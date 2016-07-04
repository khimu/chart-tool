import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class SimpleTest extends TestCase {

	private final static String FEED_URL = "http://itunes.apple.com/us/rss/%TYPE%/limit=%LIMIT%/xml";
	
	private final static DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	
	ObjectMapper m = new ObjectMapper(); 

	String type = "topfreeapplications";
	String limit = "10";

	public void testRssFeed(){
		
		List<String> result = new ArrayList<String>();
		try {
	        DataHandler handler = new DataHandler(new URLDataSource(getURL(type, limit)));

			ObjectNode root = m.createObjectNode();

			
			ArrayNode entries = m.createArrayNode();
			root.put("entries", entries);

	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(handler.getDataSource().getInputStream());

	        doc.getDocumentElement().normalize();
       	 
	        NodeList tt = doc.getDocumentElement().getElementsByTagName("entry");

	        for(int i = 0 ; i < tt.getLength(); i ++){
	        	ObjectNode rootEntry = m.createObjectNode();
	        	
	        	ObjectNode entry = m.createObjectNode();
	        	
	        	rootEntry.put("entry", entry);
	        	entries.add(rootEntry);
	        	
	            Node cNode = tt.item(i);
	            Element cElement = (Element) cNode;
	            
	            NodeList id = cElement.getElementsByTagName("id");
	            
	            ObjectNode idValue = m.createObjectNode();
	            idValue.put("textValue", id.item(0).getTextContent());
	            

	            entry.put("id", idValue);
	            
	            
	            NamedNodeMap map = id.item(0).getAttributes();
	            for(int j = 0; j < map.getLength(); j ++){
	            	ObjectNode idAttributes = m.createObjectNode();
	            	idValue.put(map.item(j).getNodeName(), map.item(j).getNodeValue());
	            }
	            
	            NodeList title = cElement.getElementsByTagName("title");
	            
	            entry.put("title", title.item(0).getTextContent());
	            
	            NodeList summary = cElement.getElementsByTagName("summary");
	            
	            entry.put("summary", summary.item(0).getTextContent());
	            
	            NodeList name = cElement.getElementsByTagName("im:name");
	            
	            entry.put("im:name", name.item(0).getTextContent());
	            
	            
	            NodeList artist = cElement.getElementsByTagName("im:artist");
	            
	            ObjectNode artistNode = m.createObjectNode();
	            artistNode.put("textValue", artist.item(0).getTextContent());

	            entry.put("im:artist", artistNode);

	            NamedNodeMap artistMap = artist.item(0).getAttributes();
	            for(int j = 0; j < artistMap.getLength(); j ++){
	            	artistNode.put(artistMap.item(j).getNodeName(), artistMap.item(j).getNodeValue());
	            }

	            NodeList price = cElement.getElementsByTagName("im:price");   
	            ObjectNode priceNode = m.createObjectNode();
	            priceNode.put("textValue", price.item(0).getTextContent());

	            entry.put("im:price", priceNode);

	            NamedNodeMap priceMap = price.item(0).getAttributes();
	            for(int j = 0; j < priceMap.getLength(); j ++){
	            	artistNode.put(priceMap.item(j).getNodeName(), priceMap.item(j).getNodeValue());
	            }
	            
	            NodeList releaseDate = cElement.getElementsByTagName("im:releaseDate");
	            
	            ObjectNode releaseNode = m.createObjectNode();
	            releaseNode.put("textValue", releaseDate.item(0).getTextContent());

	            entry.put("im:releaseDate", releaseNode);

	            NamedNodeMap releaseMap = releaseDate.item(0).getAttributes();
	            for(int j = 0; j < releaseMap.getLength(); j ++){
	            	releaseNode.put(releaseMap.item(j).getNodeName(), releaseMap.item(j).getNodeValue());
	            }
	            
	            NodeList rights = cElement.getElementsByTagName("rights");
	            entry.put("rights", rights.item(0).getTextContent());
	            
	            NodeList links = cElement.getElementsByTagName("link");
	            
	            for(int j = 0; j < links.getLength(); j ++){
		            Node link = links.item(j);
		            Element linkE = (Element) link;
	
		            ObjectNode linkNode = m.createObjectNode();
		            linkNode.put("textValue", linkE.getTextContent());

		            entry.put("link"+j, linkNode);

		            NamedNodeMap linkMap = linkE.getAttributes();
		            for(int k = 0; k < linkMap.getLength(); k ++){
		            	linkNode.put(linkMap.item(k).getNodeName(), linkMap.item(k).getNodeValue());
		            }
		            
		            NodeList durations = linkE.getElementsByTagName("im:duration");
		            if(durations != null && durations.getLength() > 0){
		            	linkNode.put(durations.item(0).getNodeName(), durations.item(0).getNodeValue());
		            }
	            }
	            
	            NodeList images = cElement.getElementsByTagName("im:image");
	            
	            for(int j = 0; j < images.getLength(); j ++){
		            Node image = images.item(j);
		            Element imageE = (Element) image;
		            
		            
		            ObjectNode imageNode = m.createObjectNode();
		            imageNode.put("textValue", imageE.getTextContent());

		            entry.put("im:image"+j, imageNode);

		            NamedNodeMap imageMap = imageE.getAttributes();
		            for(int k = 0; k < imageMap.getLength(); k ++){
		            	imageNode.put(imageMap.item(k).getNodeName(), imageMap.item(k).getNodeValue());
		            }

	            }
	            
	            NodeList contentType = cElement.getElementsByTagName("im:contentType");
	            
	            ObjectNode contentTypeNode = m.createObjectNode();
	            contentTypeNode.put("textValue", contentType.item(0).getTextContent());
	            
	            entry.put("im:contentType", contentTypeNode);
	            
	            NamedNodeMap contentTypeMap = contentType.item(0).getAttributes();
	            for(int j = 0; j < contentTypeMap.getLength(); j ++){
	            	contentTypeNode.put(contentTypeMap.item(j).getNodeName(), contentTypeMap.item(j).getNodeValue());
	            }

	            
	            NodeList category = cElement.getElementsByTagName("category");
	            
	            ObjectNode categoryNode = m.createObjectNode();
	            categoryNode.put("textValue", category.item(0).getTextContent());
	            
	            entry.put("category", categoryNode);
	            
	            NamedNodeMap categoryMap = category.item(0).getAttributes();
	            for(int j = 0; j < categoryMap.getLength(); j ++){	            	
	            	categoryNode.put(categoryMap.item(j).getNodeName(), categoryMap.item(j).getNodeValue());
	            }
	            
	            NodeList content = cElement.getElementsByTagName("content");
	            
	            ObjectNode contentNode = m.createObjectNode();
	            contentNode.put("textValue", content.item(0).getTextContent());
	            
	            entry.put("content", contentNode);
	            
	            NamedNodeMap contentMap = content.item(0).getAttributes();
	            for(int j = 0; j < contentMap.getLength(); j ++){
	            	contentNode.put(contentMap.item(j).getNodeName(), contentMap.item(j).getNodeValue());
	            }

	            System.out.println(root.toString());
	            
		        FileOutputStream fos = new FileOutputStream(new File("./topfreeapplications.json"));
		       
		        
		        fos.write(root.toString().getBytes());
	        }	        
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.exit(0);
	}
	
	private void updateEntry(ObjectNode entry, String tagName, Element entryElement){
        NodeList tag = entryElement.getElementsByTagName(tagName);
        
        ObjectNode node = m.createObjectNode();
        node.put("textValue", tag.item(0).getTextContent());
        
        entry.put(tagName, node);
        
        NamedNodeMap map = tag.item(0).getAttributes();
        for(int j = 0; j < map.getLength(); j ++){
        	node.put(map.item(j).getNodeName(), map.item(j).getNodeValue());
        }
	}
	
	
	
	private URL getURL(String type, String limit) throws MalformedURLException{
		return new URL(FEED_URL.replace("%TYPE%", type).replace("%LIMIT%", limit));
	}


}
