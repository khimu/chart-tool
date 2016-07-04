package com.icsm.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.icsm.service.IItunesFeedService;
import com.icsmobile.faadplatform.dao.ItunesApplication;
import com.icsmobile.faadplatform.dao.campaign.IItunesFreeApplicationDao;
import com.icsmobile.faadplatform.dao.campaign.IItunesPaidApplicationDao;
import com.icsmobile.faadplatform.mongo.manager.IMongoManager;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Service("itunesRssFeedService")
public class ItunesRssFeedService implements IItunesFeedService {
	
	private static final int FALL_INTO_TOP = 200;

	protected final Log log = LogFactory.getLog(getClass());
	
	private final static int DROP_OUT_OF_VALUE = 150;
	
	private final static String TO = "sales@icsmobile.com";
	private final static String FROM = "dev@icsmobile.com";

	private final static String FEED_URL = "http://itunes.apple.com/us/rss/%TYPE%/limit=300/xml";
	
	private final static String PAID = "toppaidapplications";
	private final static String FREE = "topfreeapplications";
	
	@Autowired
	private JavaMailSender mailSender;

	private final static DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

	private final static ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private String webUrl;
	
	@Autowired
	private IItunesPaidApplicationDao iItunesPaidApplicationDao;
	
	@Autowired
	private IItunesFreeApplicationDao iItunesFreeApplicationDao;

	@Autowired
	private IMongoManager freeMongoManager;
	
	@Autowired
	private IMongoManager paidMongoManager;

	public ItunesRssFeedService() {
	}

	/*
	 * Used for testing
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: \t localhost 27017");
			// System.out.println("Usage: \t toppaidapplications 300 localhost 27017");
			// System.out.println("Usage: \t topgrossingapplications 300 localhost 27017");
		}
		ItunesRssFeedService service = new ItunesRssFeedService();
		service.processFeed(args[0], args[1]);
		System.exit(0);
	}

	public void processFeed(String type, String limit) {
		writeData(type, limit);
	}

	/**
	 * Web service used to pull metrics from itunes for analysis
	 */
	public String fetchFreeRssFeed(String type) {
		writeData(type, "300");
		return "Process Executed";
	}

	private void writeData(String type, String limit) {
		DBObject dbObject = (DBObject) JSON.parse(getRssFeedJson(type, limit));
		if(PAID.equals(type)){
			paidMongoManager.insert(dbObject);
		}else if(FREE.equals(type)){
			freeMongoManager.insert(dbObject);
		}else{
			log.error("Report type not supported " + type);
		}
	}

	/**
	 * Get metrics from itunes and process content into json string
	 * 
	 * @param type
	 * @param limit
	 * @return
	 */
	private String getRssFeedJson(String type, String limit) {

		Set<String> watchlist = getNewlyReleasedItunesFeaturedAppsIfPaid(type);
		List<String> targettedApps = new ArrayList<String>();
		
		List<String> all300ItunesAppIds = new ArrayList<String>();
		
		StringBuilder fellOutOfTopX = new StringBuilder();
		StringBuilder fellIntoTopX = new StringBuilder();
		
		fellIntoTopX.append("Name \t\t RANK \t\t RELEASE DATE \t\t LINK");
		fellIntoTopX.append("\n");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		ObjectNode root = objectMapper.createObjectNode();
		
		String appId = "";

		try {
			DataHandler handler = new DataHandler(new URLDataSource(getURL(type, limit)));

			ArrayNode entries = objectMapper.createArrayNode();
			root.put("timestamp", sdf.format(new Date()));
			root.put("entries", entries);

			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(handler.getDataSource().getInputStream());

			doc.getDocumentElement().normalize();

			NodeList tt = doc.getDocumentElement().getElementsByTagName("entry");

			for (int i = 0; i < tt.getLength(); i++) {
				try {
					ObjectNode rootEntry = objectMapper.createObjectNode();
					ObjectNode entry = objectMapper.createObjectNode();

					Node cNode = tt.item(i);
					Element cElement = (Element) cNode;

					NodeList name = cElement.getElementsByTagName("im:name");

					rootEntry.put(name.item(0).getTextContent().replaceAll("[^a-zA-Z]", ""), entry);
					entries.add(rootEntry);

					entry.put("rank", i + 1);

					appId = parseId(cElement, entry);
					
					// collect the app ID to run a query at the end for all apps with more than 20 movements in the last 4 hours - only for FREE apps
					all300ItunesAppIds.add(appId);

					NodeList category = cElement.getElementsByTagName("category");
					entry.put("category", ((Element) category.item(0)).getAttributeNode("label").getNodeValue());

					parseArtist(cElement, entry);
					parsePrice(cElement, entry);
					parseLink(cElement, entry);
					parseImage(cElement, entry);

					NodeList releaseDate = cElement.getElementsByTagName("im:releaseDate");
					entry.put("release_date", releaseDate.item(0).getTextContent());

					// only for PAID - into = 200
					boolean isFeaturedApp = findNewlyReleasedAppsInTop(i + 1, entry, releaseDate.item(0).getTextContent(), appId, name.item(0).getTextContent(), type, fellIntoTopX, watchlist);
					
					// only for PAID - out = 150
					if(isFeaturedApp == false){
						checkIfFeaturedAppFellOutOfTop(appId, name.item(0).getTextContent(), type, i + 1, watchlist, targettedApps, fellOutOfTopX);
					}

					NodeList summary = cElement.getElementsByTagName("summary");
					entry.put("has_tag", summary.item(0).getTextContent().toLowerCase().contains("freeappaday") ? 1 : 0);
				} catch (Exception e) {
					log.error("unable to process entry due to " + e.getMessage());
				}
			}
		} catch (Exception e) {
			log.error("unable to parse document from itunes due to " + e.getMessage());
		}
		
		iItunesPaidApplicationDao.batchUpdateAlreadyTargetedApp(targettedApps);
		
		if(FREE.equals(type)){
			List<ItunesApplication> topMovers = iItunesFreeApplicationDao.findTotalMovementGreaterThan20(all300ItunesAppIds);
			if(topMovers.size() > 0){
				emailSalesWithTopMovers(topMovers);
			}
		}else{
			// only for PAID
			sendEmail("Newly released app fell out of top 150", fellOutOfTopX.toString());
			
			// send email only if item is new to our watch list
			sendEmail("Newly released app moved into top 200", fellIntoTopX.toString());
		}

		return root.toString();
	}
	
	// check current ranking against db ranking
	private void checkIfFeaturedAppFellOutOfTop(String appId, String name, String type, int rank, Set<String> watchlist, List<String> targettedApps, StringBuilder fellOutOfTopX){
		// indicates falling out of the top into the 200 - 300 range
		
		if(PAID.equals(type) && rank > DROP_OUT_OF_VALUE && watchlist.contains(appId)){
			targettedApps.add(appId);
			
			fellOutOfTopX.append("\n\n" + name + " - itunes app Id: " + appId + " Ranking: " + rank);
		}
	}
	
	private void sendEmail(String subject, String body){
		if(StringUtils.trimToNull(body) == null){
			return;
		}
		//GMailSender send = new GMailSender();
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setText(body);
			message.setSubject(subject);
			message.setFrom(FROM);
			message.setTo(TO);
			mailSender.send(message);
			//mailSender.sendMail(subject, body, FROM, TO);
		} catch (Exception e) {
			log.error(body);
		}
	}
	
	private void emailSalesWithTopMovers(List<ItunesApplication> topMovers){
		StringBuilder b = new StringBuilder();
		
		for(ItunesApplication ia : topMovers){
			b.append(ia.getName() + "\t\t");
			b.append(webUrl + "/app/freedetails?appId=");
			b.append(ia.getItunesAppId());
			b.append("&hours=4&lookBackHours=8");
			b.append("\n");
			b.append("\n");
		}
		//GMailSender send = new GMailSender();
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setText(b.toString());
			message.setSubject("Free Apps Top Movers of the hour");
			message.setFrom(FROM);
			message.setTo(TO);
			mailSender.send(message);			
			//send.sendMail("Free Apps Top Movers of the hour", b.toString(), FROM, TO);
		} catch (Exception e) {
			log.error("Top Movers of the hour: " + b.toString());
		}
	}

	/*
	 * Apps that were released within the last 5 days and are in the top 200
	 * will be flagged and
	 * 
	 * only handling free and paid currently - do not pass in topgrossing without adding logic around it
	 */
	private boolean findNewlyReleasedAppsInTop(int rank, ObjectNode entry, String releaseDate, String appId, String name, String type, StringBuilder fellIntoTopX, Set<String> watchlist) {
		if (rank >= FALL_INTO_TOP || FREE.equals(type)) {
			entry.put("itunes_featured_app", 0);
			return false;
		}
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();
		DateTime releasedate = parser2.parseDateTime(releaseDate);

		Calendar rdCal = Calendar.getInstance();
		rdCal.setTimeInMillis(releasedate.getMillis());
		rdCal.add(Calendar.DAY_OF_YEAR, 5);
		
		// was this app released within the last 5 days
		if (rdCal.getTimeInMillis() >= System.currentTimeMillis() && rank < FALL_INTO_TOP) {
			// this is an assumption we're making based on the app being new and
			// in the top 200
			entry.put("itunes_featured_app", 1);

			if(watchlist.contains(appId) == false){
				fellIntoTopX.append(name + "\t\t" + rank + "\t\t" + new Date(releasedate.getMillis()) + "\t\t");
				fellIntoTopX.append(webUrl + "/app/paiddetails?appId=");
				fellIntoTopX.append(appId);
				fellIntoTopX.append("&hours=4&lookBackHours=8");
				fellIntoTopX.append("\n\n");
				//fellIntoTopX.append("\n" + name + "("+new Date(releasedate.getMillis())+") - itunes app id: " + appId + " ranking: " + rank);
			}
			return true;
		} else {
			entry.put("itunes_featured_app", 0);
			return false;
		}
	}
	
	private Set<String> getNewlyReleasedItunesFeaturedAppsIfPaid(String type){
		if(PAID.equals(type)){
			return iItunesPaidApplicationDao.findNewClientsByItunesFeaturedApp();
		}
		return new TreeSet<String>();
	}

	private String parseId(Element rootElement, ObjectNode entry) {
		NodeList id = rootElement.getElementsByTagName("id");

		NamedNodeMap map = id.item(0).getAttributes();

		String appId = "";

		for (int j = 0; j < map.getLength(); j++) {
			if (map.item(j).getNodeName().equals("im:bundleId")) {
				entry.put("bundle_id", map.item(j).getNodeValue());
			} else if (map.item(j).getNodeName().equals("im:id")) {
				appId = map.item(j).getNodeValue();
				entry.put("itunes_app_id", appId);
			}
		}
		return appId;
	}

	private void parseImage(Element rootElement, ObjectNode entry) {
		NodeList images = rootElement.getElementsByTagName("im:image");

		for (int j = 0; j < images.getLength(); j++) {
			Node image = images.item(j);
			Element imageE = (Element) image;

			if (imageE.getAttributeNode("height").getNodeValue().equals("100")) {
				entry.put("image", imageE.getTextContent());
			}
		}
	}

	private void parseLink(Element rootElement, ObjectNode entry) {
		NodeList links = rootElement.getElementsByTagName("link");

		for (int j = 0; j < links.getLength(); j++) {
			Node link = links.item(j);
			Element linkE = (Element) link;

			if (linkE.getAttributeNode("type").getNodeValue().equals("text/html")) {
				entry.put("link", linkE.getAttributeNode("href").getNodeValue());
			}

		}
	}

	private void parsePrice(Element rootElement, ObjectNode entry) {
		NodeList price = rootElement.getElementsByTagName("im:price");

		entry.put("price_type", price.item(0).getTextContent());

		NamedNodeMap priceMap = price.item(0).getAttributes();
		for (int j = 0; j < priceMap.getLength(); j++) {
			if (priceMap.item(j).getNodeName().equals("amount")) {
				entry.put("amount", priceMap.item(j).getNodeValue());
			} else if (priceMap.item(j).getNodeName().equals("currency")) {
				entry.put("currency", priceMap.item(j).getNodeValue());
			}
		}
	}

	private void parseArtist(Element rootElement, ObjectNode entry) {
		NodeList artist = rootElement.getElementsByTagName("im:artist");

		entry.put("company_name", artist.item(0).getTextContent());

		NamedNodeMap artistMap = artist.item(0).getAttributes();
		for (int j = 0; j < artistMap.getLength(); j++) {
			if (artistMap.item(j).getNodeName().equals("href")) {
				entry.put("itunes_company_link", artistMap.item(j).getNodeValue());
			}
		}
	}

	private URL getURL(String type, String limit) throws MalformedURLException {
		return new URL(FEED_URL.replace("%TYPE%", type).replace("%LIMIT%", limit));
	}

}
