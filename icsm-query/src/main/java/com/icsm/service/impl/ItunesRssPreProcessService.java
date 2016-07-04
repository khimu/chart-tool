package com.icsm.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icsm.service.IItunesRssPreProcessService;
import com.icsm.service.ItunesApplicationCommonService;
import com.icsm.service.ItunesFreeApplicationService;
import com.icsmobile.faadplatform.dao.ItunesApplication;
import com.icsmobile.faadplatform.mongo.manager.IMongoManager;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/*
 * We will be pulling data from itunes every 15 minutes which will be 4 records per hour
 * 
 * This service will run every hour and process data for the previous hour by pulling the latest 4 records from mongodb
 * 
 * The order of the list from mongodb will be in descending order latest record to earliest record
 * 
 * Every time this service is called we will generate 300 * 4 new sets of records
 * 
 */
@Component("itunesRssPreProcessService")
public class ItunesRssPreProcessService implements IItunesRssPreProcessService {

	@Autowired
	private ItunesApplicationCommonService itunesApplicationSimpleManager;
	
	@Autowired
	private ItunesFreeApplicationService itunesApplicationManager;
	
	private final Log log = LogFactory.getLog(getClass());
	
	private final static ObjectMapper objectMapper = new ObjectMapper(); 
	
	@Autowired
	private IMongoManager freeMongoManager;
	
	@Autowired
	private IMongoManager paidMongoManager;
	
	public enum TopSize {
		TEN(10), TWENTY_FIVE(25), FIFTY(50), ONE_HUNDRED(100), THREE_HUNDRED(300);
		
		private Integer size;
		
		private TopSize(int size){
			this.size = size;
		}
		
		public int getSize(){
			return this.size;
		}

	}
	
	private final static String PAID = "toppaidapplications";
	private final static String FREE = "topfreeapplications";
	
	public List<String> readData(String type, int start, int limit){
		DBObject sortCriteria = new BasicDBObject("timestamp", -1); 
		if(PAID.equals(type)){
			return paidMongoManager.readSublist(sortCriteria, start, limit);
		}else if(FREE.equals(type)){
			return freeMongoManager.readSublist(sortCriteria, start, limit);
		}else{
			log.error("Report type not supported " + type);
		}	
		return new ArrayList<String>();
	}
	
	public String preProcessOldPaid(int limit){
		
		List<ItunesApplication> dbList = itunesApplicationSimpleManager.getPaidLastBatch();
		
		for(int i = 0; i < limit/4; i ++){
			startProcess(PAID, i * 4, (i + 1) * 4, dbList);
		}		
		
		return "Process Started";
	}
		
	public String preProcessOldFree(int limit){
		
		List<ItunesApplication> dbList = itunesApplicationSimpleManager.getFreeLastBatch();
		
		//List<JsonNode> data = new ArrayList<JsonNode>();
		for(int i = 0; i < limit/4; i ++){
			startProcess(FREE, i * 4, (i + 1) * 4, dbList);
		}
		
		return "Process Started";
	}	

	public String preProcessPaid(){
		List<ItunesApplication> dbList = itunesApplicationSimpleManager.getPaidLastBatch();
		
		//List<JsonNode> data = new ArrayList<JsonNode>();
		return startProcess(PAID, 0, 4, dbList);
	}
		
	public String preProcessFree(){
		List<ItunesApplication> dbList = itunesApplicationSimpleManager.getFreeLastBatch();
		
		//List<JsonNode> data = new ArrayList<JsonNode>();
		return startProcess(FREE, 0, 4, dbList);
	}
	
	private String startProcess(String type, int start, int limit, List<ItunesApplication> dbList){
		List<String> jsons = this.readData(type, start , limit);
		//List<JsonNode> data = new ArrayList<JsonNode>();
		Map<Integer, Map<String, ItunesApplication>> itunesAppMap = new HashMap<Integer, Map<String, ItunesApplication>>();

		// pull the last record set from db
		
		// 
		for(Integer recordOrder = 0; recordOrder < jsons.size(); recordOrder ++){
			try {
				JsonNode node = objectMapper.readTree(jsons.get(recordOrder));
				itunesAppMap.put(recordOrder, new HashMap<String,  ItunesApplication>());
				
				// run batch update on record to flag 
				
				createData(itunesAppMap.get(recordOrder), node);
				
				//data.add(node);
			} catch (JsonProcessingException e) {
				log.error("Unable to process record due to " + e.getMessage());
			} catch (IOException e) {
				log.error("Unable to process record due to " + e.getMessage());

			}
		}
		
		flagNewTopApps(itunesAppMap, dbList);
		flagDroppedApps(itunesAppMap, dbList);
		populateMovedField(itunesAppMap, dbList);
		
		if(itunesAppMap.get(0) != null){
			flagLastBatch(itunesAppMap.get(0).values());
		}
		
		if(dbList != null){
			unflagLastBatch(dbList);
		}
		
		persistData(itunesAppMap);
		
		return "Process Executed";

	}
	
	private void persistData(Map<Integer, Map<String, ItunesApplication>> itunesAppMap){
		try {
			if(itunesAppMap.get(0) != null){
				itunesApplicationSimpleManager.batchSave(new ArrayList<ItunesApplication>(itunesAppMap.get(0).values()));
			}
			if(itunesAppMap.get(1) != null){
				itunesApplicationSimpleManager.batchSave(new ArrayList<ItunesApplication>(itunesAppMap.get(1).values()));
			}
			if(itunesAppMap.get(2) != null){
				itunesApplicationSimpleManager.batchSave(new ArrayList<ItunesApplication>(itunesAppMap.get(2).values()));
			}
			if(itunesAppMap.get(3) != null){
				itunesApplicationSimpleManager.batchSave(new ArrayList<ItunesApplication>(itunesAppMap.get(3).values()));
			}
		} catch (Exception e) {
			log.error("Unable to persist batch due to " + e.getMessage());
		}
	}
	

	private void flagLastBatch(Collection<ItunesApplication> current){
		for(ItunesApplication ia : current){
			ia.setLastBatch(1);
		}
	}
	
	private void unflagLastBatch(Collection<ItunesApplication> current){
		for(ItunesApplication ia : current){
			ia.setLastBatch(0);
		}
		try {
			itunesApplicationSimpleManager.batchUpdate(new ArrayList<ItunesApplication>(current));
		} catch (Exception e) {
			log.error("Unable to update last_batch flag due to " + e.getMessage());
		}
	}
	
	
	private void setNewTopField(Collection<ItunesApplication> current, Set<String> previous){
		for(ItunesApplication ia : current){
			if(previous.contains(ia.getName()) == false){
				ia.setNewtop(1);
				ia.setDropped(0);
			}			
		}		
	}
	
	private void setDroppedField(Collection<ItunesApplication> previous, Set<String> current){
		for(ItunesApplication ia : previous){
			if(current.contains(ia.getName()) == false){
		    	ia.setDropped(1);
			}
		 }	
	}
	
	/*
	 * dbRank is old record
	 * newRecordRank is new record
	 * 
	 * oldRecord - newRecord yields + value if app is jumping the chart
	 * oldRecord - newRecord yields - value if app is falling off the chart
	 * 
	 * 300 - 100 = 200 jump 200 from previous position
	 * 100 - 200 = 100 fell 100 from previous position
	 */
	private void calculateRankingMovement(Collection<ItunesApplication> previous, Map<String, ItunesApplication> current){		
		for(ItunesApplication ia : previous){
			if(ia.getDropped() == 0){
				ItunesApplication newRecord = current.get(ia.getName());
				Integer dbRank = ia.getRanking();
				Integer newRecordRank = newRecord.getRanking();
				newRecord.setMoved(dbRank - newRecordRank);
				//System.out.println( parser2.parseDateTime(current.get(ia.getName()).getTimestamp()).isAfter(parser2.parseDateTime(ia.getTimestamp()).getMillis()));
			}
		}
	}
	
	private Set<String> createHashSet(List<ItunesApplication> data){
		
		Set<String> set = new HashSet<String>();
		
		if(data == null){
			return set;
		}
	
		for(ItunesApplication ia : data){
			ia.setLastBatch(0);
			ia.setMoved(0);
			set.add(ia.getName());
		}
		
		return set;
	}
	
	
	private Set<String> createHashSet(Map<String, ItunesApplication> data){
		
		Set<String> set = new HashSet<String>();
		
		if(data == null){
			return set;
		}
	
		for(ItunesApplication ia : data.values()){
			ia.setLastBatch(0);
			ia.setMoved(0);
			set.add(ia.getName());
		}
		
		return set;
	}
	
	private void flagNewTopApps(Map<Integer, Map<String, ItunesApplication>> itunesAppMap, List<ItunesApplication> dbList){
		// go forward
		Set<String> secondSet = createHashSet(itunesAppMap.get(1));
		Set<String> thirdSet = createHashSet(itunesAppMap.get(2));
		Set<String> fourthSet = createHashSet(itunesAppMap.get(3));
		Set<String> dbSet = createHashSet(dbList);
		
		// if app from latest list is not in previous list, it is a new top app
		if(itunesAppMap.get(0) != null){
			setNewTopField(itunesAppMap.get(0).values(), secondSet);
		}
		if(itunesAppMap.get(1) != null){			
			setNewTopField(itunesAppMap.get(1).values(), thirdSet);
		}
		if(itunesAppMap.get(2) != null){			
			setNewTopField(itunesAppMap.get(2).values(), fourthSet);
		}

		if(itunesAppMap.get(3) != null){
			// if nothing exist in the db, all records in fourthSet will be flagged as new
			setNewTopField(itunesAppMap.get(3).values(), dbSet);
		}
	}
	
	private void flagDroppedApps(Map<Integer, Map<String, ItunesApplication>> itunesAppMap, List<ItunesApplication> dbList){
		// go backwards
		Set<String> firstSet = createHashSet(itunesAppMap.get(0));
		Set<String> secondSet = createHashSet(itunesAppMap.get(1));
		Set<String> thirdSet = createHashSet(itunesAppMap.get(2));
		Set<String> fourthSet = createHashSet(itunesAppMap.get(3));
		
		
		// if dbList is null, dbList record does not need to be updated
		if(dbList != null){
			setDroppedField(dbList, fourthSet);
		}

		if(itunesAppMap.get(3) != null){
			setDroppedField(itunesAppMap.get(3).values(), thirdSet);
		}
		if(itunesAppMap.get(2) != null){
			setDroppedField(itunesAppMap.get(2).values(), secondSet);
		}
		if(itunesAppMap.get(1) != null){
			setDroppedField(itunesAppMap.get(1).values(), firstSet);
		}
		
		// NOTE:  the first list's dropped field will be set next time preProcess is called
	}
	

	private void populateMovedField(Map<Integer, Map<String, ItunesApplication>> itunesAppMap, List<ItunesApplication> dbList){
		if(itunesAppMap.get(3) != null & dbList != null){
			// from db to fourth set, count if movement was in positive or negative direction		
			calculateRankingMovement(dbList, itunesAppMap.get(3));
		}
		if(itunesAppMap.get(3) != null && itunesAppMap.get(2) != null){
			calculateRankingMovement(itunesAppMap.get(3).values(), itunesAppMap.get(2));
		}
		if(itunesAppMap.get(2) != null && itunesAppMap.get(1) != null){
			calculateRankingMovement(itunesAppMap.get(2).values(), itunesAppMap.get(1));
		}
		if(itunesAppMap.get(1) != null && itunesAppMap.get(0) != null){
			calculateRankingMovement(itunesAppMap.get(1).values(), itunesAppMap.get(0));
		}
	}
	
	
	private void createData(Map<String, ItunesApplication> itunesApp, JsonNode node){
		JsonNode array = node.findPath("entries");
		JsonNode timestamp = node.findPath("timestamp");
			
		Iterator<JsonNode> itr = array.iterator();
		
		while(itr.hasNext()){
			ItunesApplication itunesApplicationObj = new ItunesApplication();
			
			JsonNode next = itr.next();
			String name = next.getFieldNames().next();
			
			itunesApplicationObj.setName(name);
			itunesApplicationObj.setPullDate(timestamp.getTextValue());
			
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
				JsonNode hasTag = c.getPath("has_tag");
				JsonNode itunesFeaturedApp = c.getPath("itunes_featured_app");
				
				itunesApplicationObj.setHasTag(hasTag.getIntValue());
				itunesApplicationObj.setAmount(amount.getTextValue());
				itunesApplicationObj.setCurrency(currency.getTextValue());
				itunesApplicationObj.setBundleId(bundleId.getTextValue());
				itunesApplicationObj.setItunesAppId(itunesAppId.getTextValue());
				itunesApplicationObj.setLink(link.getTextValue());
				itunesApplicationObj.setImage(image.getTextValue());
				itunesApplicationObj.setCompanyName(companyName.getTextValue());
				itunesApplicationObj.setCompanyItunesLink(itunesCompanyLink.getTextValue());
				itunesApplicationObj.setPriceType(priceType.getTextValue());
				
				itunesApplicationObj.setRanking(rank.getIntValue());
				itunesApplicationObj.setCategory(category.getTextValue());
				itunesApplicationObj.setReleaseDate(releaseDate.getTextValue());
				itunesApplicationObj.setItunesFeaturedApp(itunesFeaturedApp.getIntValue());
			}
			itunesApp.put(name, itunesApplicationObj);
		}
	}


}
