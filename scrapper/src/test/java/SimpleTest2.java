import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;


public class SimpleTest2 extends TestCase {

	String type = "topfreeapplications";
	String limit = "10";

	protected final Log log = LogFactory.getLog(getClass());
	
	private final static String FEED_URL = "http://itunes.apple.com/us/rss/%TYPE%/limit=%LIMIT%/xml";
	
	private final static DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	
	private final static ObjectMapper objectMapper = new ObjectMapper(); 
	

	private void writeData(String type, String limit){
		try {
			Mongo m = new Mongo( "localhost" , 27017 );

			DB db = m.getDB( type );

			DBCollection collection = db.getCollection(type);
			
			DBObject dbObject = (DBObject)JSON.parse(getRssFeedJson(type, limit));
			 
			collection.insert(dbObject);			
		}catch(Exception e){
			log.error("Unable to connect to Mongodb " + e.getMessage());
		}
	}
	
	private void readData(String type){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			
			Mongo m = new Mongo( "localhost" , 27017 );

			DB db = m.getDB( type );

			DBCollection collection = db.getCollection(type);
 
			DBCursor cursorDocJSON = collection.find();
			while (cursorDocJSON.hasNext()) {
				JsonNode node = objectMapper.readTree(cursorDocJSON.next().toString());
				
				//System.out.println(node.toString());
				
				JsonNode array = node.findPath("entries");
				
				Iterator<JsonNode> itr = array.iterator();
				
				while(itr.hasNext()){
					JsonNode next = itr.next();
					String name = next.getFieldNames().next();
					Iterator<JsonNode> content = next.getElements();
					
					while(content.hasNext()){
						JsonNode c = content.next();
						JsonNode rank = c.getPath("rank");
						JsonNode category = c.getPath("category");
						JsonNode releaseDate = c.getPath("release_date");
						JsonNode priceType = c.getPath("price_type");
						JsonNode amount = c.getPath("amount");
						JsonNode currency = c.getPath("currency");
						JsonNode bundleId = c.getPath("bundle_id");
						JsonNode itunesAppId = c.getPath("itunes_app_id");
						JsonNode link = c.getPath("link");
						JsonNode image = c.getPath("image");
						JsonNode companyName = c.getPath("company_name");
						JsonNode itunesCompanyLink = c.getPath("itunes_company_link");
						
						System.out.println("START");
						System.out.println(amount.getTextValue());
						System.out.println(currency.getTextValue());
						System.out.println(bundleId.getTextValue());
						System.out.println(itunesAppId.getTextValue());
						System.out.println(link.getTextValue());
						System.out.println(image.getTextValue());
						System.out.println(companyName.getTextValue());
						System.out.println(itunesCompanyLink.getTextValue());
						
						System.out.println(rank.getIntValue());
						System.out.println(category.getTextValue());
						System.out.println(releaseDate.getTextValue());
						System.out.println("END");
						
						Iterator<String> names = c.getFieldNames();
						while(names.hasNext()){
							System.out.println(names.next());
						}
						System.out.println("Exit");
					}
					System.out.println("Exit");
				}
				System.out.println("Exit");
			}
 
			//collection.remove(new BasicDBObject());
			
		}catch(Exception e){
			log.error("Unable to connect to Mongodb " + e.getMessage());
		}
	}
	
	private void list(String type){
		try {
			Mongo m = new Mongo( "localhost" , 27017 );
	
			DB db = m.getDB( type );
			
			Set<String> collections = db.getCollectionNames();
			 
			for (String collectionName : collections) {
				System.out.println(collectionName);
			}
		}catch(Exception e){
			log.error("Unable to connect to Mongodb " + e.getMessage());
		}
	}
	
	private void search(String type){
		try {
			Mongo m = new Mongo( "localhost" , 27017 );
	
			DB db = m.getDB( type );

			DBCollection collection = db.getCollection(type);
			 
			BasicDBObject query = new BasicDBObject();
			query.put("timestamp", new BasicDBObject("$gt", "2012-07-10T18:12:35-0700"));
			DBCursor cursor = collection.find(query);
			while(cursor.hasNext()) {
				System.out.println(cursor.next());
			}
		}catch(Exception e){
			log.error("Unable to connect to Mongodb " + e.getMessage());
		}
	}
	
	private String getRssFeedJson(String type, String limit){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		ObjectNode root = objectMapper.createObjectNode();

		try {
	        DataHandler handler = new DataHandler(new URLDataSource(getURL(type, limit)));
			
			ArrayNode entries = objectMapper.createArrayNode();
			root.put("timestamp", sdf.format(new Date()));
			root.put("entries", entries);

	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(handler.getDataSource().getInputStream());

	        doc.getDocumentElement().normalize();
       	 
	        NodeList tt = doc.getDocumentElement().getElementsByTagName("entry");

	        for(int i = 0 ; i < tt.getLength(); i ++){
	        	try {
		        	ObjectNode rootEntry = objectMapper.createObjectNode();
		        	ObjectNode entry = objectMapper.createObjectNode();
		        	
		            Node cNode = tt.item(i);
		            Element cElement = (Element) cNode;
		        	
		            NodeList name = cElement.getElementsByTagName("im:name");
	
		        	rootEntry.put(name.item(0).getTextContent(), entry);
		        	entries.add(rootEntry);
		        	
		            entry.put("rank", i);
	
		            parseId(cElement, entry);
		            
		            NodeList category = cElement.getElementsByTagName("category");
		            entry.put("category", ((Element)category.item(0)).getAttributeNode("label").getNodeValue());
		            
		            parseArtist(cElement, entry);
		            parsePrice(cElement, entry);
		            parseLink(cElement, entry);
		            parseImage(cElement, entry);
		            
		            NodeList releaseDate = cElement.getElementsByTagName("im:releaseDate");
		            entry.put("release_date", releaseDate.item(0).getTextContent());
	        	}catch(Exception e){
	        		System.out.println("unable to process entry due to " + e.getMessage());
	        	}

		        //FileOutputStream fos = new FileOutputStream(new File("./topfreeapplications.json"));
		        //fos.write(root.toString().getBytes());
	        }	        
		}catch(Exception e){
			System.out.println("unable to parse document from itunes due to " + e.getMessage());			
		}

		return root.toString();
	}


	public void testRss(){
		writeData(type, limit);
		readData(type);
		//list(type);
		//search(type);
	}
	
	private void parseId(Element rootElement, ObjectNode entry){
        NodeList id = rootElement.getElementsByTagName("id");
                
        NamedNodeMap map = id.item(0).getAttributes();
        
        for(int j = 0; j < map.getLength(); j ++){
        	if(map.item(j).getNodeName().equals("im:bundleId")){
        		entry.put("bundle_id", map.item(j).getNodeValue());
        	}else if(map.item(j).getNodeName().equals("im:id")){
        		entry.put("itunes_app_id", map.item(j).getNodeValue());
        	}
        }
	}
	
	private void parseImage(Element rootElement, ObjectNode entry){
		NodeList images = rootElement.getElementsByTagName("im:image");
        
        for(int j = 0; j < images.getLength(); j ++){
            Node image = images.item(j);
            Element imageE = (Element) image;
            
            if(imageE.getAttributeNode("height").getNodeValue().equals("100")){
            	entry.put("image", imageE.getTextContent());
            }
        }
	}
	
	private void parseLink(Element rootElement, ObjectNode entry){
		NodeList links = rootElement.getElementsByTagName("link");
        
        for(int j = 0; j < links.getLength(); j ++){
            Node link = links.item(j);
            Element linkE = (Element) link;
            
            if(linkE.getAttributeNode("type").getNodeValue().equals("text/html")){
            	entry.put("link", linkE.getAttributeNode("href").getNodeValue());
            }
            
        }
	}

	private void parsePrice(Element rootElement, ObjectNode entry){
		NodeList price = rootElement.getElementsByTagName("im:price");   

        entry.put("price_type", price.item(0).getTextContent());
 
        NamedNodeMap priceMap = price.item(0).getAttributes();
        for(int j = 0; j < priceMap.getLength(); j ++){
        	if(priceMap.item(j).getNodeName().equals("amount")){
        		entry.put("amount",  priceMap.item(j).getNodeValue());
        	}else if(priceMap.item(j).getNodeName().equals("currency")){
        		entry.put("currency",  priceMap.item(j).getNodeValue());
        	}        
        }    
	}
	
	private void parseArtist(Element rootElement, ObjectNode entry){
		NodeList artist = rootElement.getElementsByTagName("im:artist");
 
        entry.put("company_name", artist.item(0).getTextContent());
        
        NamedNodeMap artistMap = artist.item(0).getAttributes();
        for(int j = 0; j < artistMap.getLength(); j ++){
        	if(artistMap.item(j).getNodeName().equals("href")){
        		entry.put("itunes_company_link", artistMap.item(j).getNodeValue());
        	}
        }
	}
	
	private URL getURL(String type, String limit) throws MalformedURLException{
		return new URL(FEED_URL.replace("%TYPE%", type).replace("%LIMIT%", limit));
	}

}
