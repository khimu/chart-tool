package com.icsm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icsm.service.IItunesPaidRssReportService;
import com.icsm.service.ReportService;
import com.icsmobile.faadplatform.dao.ItunesApplication;
import com.icsmobile.faadplatform.dao.campaign.ICalendarRewardsAppDao;
import com.icsmobile.faadplatform.dao.campaign.IItunesPaidApplicationDao;

@Component("itunesPaidRssReportService")
@WebService(serviceName = "itunesPaidRssReportService", endpointInterface = "com.icsm.service.IItunesPaidRssReportService")
public class ItunesPaidRssReportService extends BaseService implements IItunesPaidRssReportService {

	@Autowired
	private IItunesPaidApplicationDao itunesPaidApplicationDao;

	@Autowired
	private ReportService reportService;

	@Autowired
	private ICalendarRewardsAppDao calendarRewardsAppDao;

	private final Log log = LogFactory.getLog(getClass());

	public String reportForPaidPastFeaturedApps(Integer hours) {
		return pupulateReportNode(reportService.findAllPaidRewardsApp(hours)).toString();
	}

	public String reportPaidAppByCompanyName(String companyName, Integer days) {
		List<com.icsmobile.faadplatform.dao.ItunesApplication> apps = itunesPaidApplicationDao.getAppsByCompanyName(companyName, days);
		ArrayNode root = objectMapper.createArrayNode();

		for (ItunesApplication ia : apps) {
			ObjectNode item = objectMapper.createObjectNode();
			item.put("name", ia.getName());
			item.put("days", days);
			item.put("rank", ia.getRanking());
			root.add(item);
		}

		return root.toString();
	}

	public String reportPaidForCampaignsByDate(String startDay, int decrement) {
		ObjectNode parent = objectMapper.createObjectNode();

		ArrayNode root = objectMapper.createArrayNode();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		Date today = new Date();
		Calendar cal = Calendar.getInstance();

		if (startDay != null) {
			try {
				cal.setTime(sdf.parse(startDay));
			} catch (ParseException e) {
				log.error("unable to parse parameter startDay");
			}
		} else {
			cal.setTime(today);
			startDay = today.toString();
		}

		parent.put("startdate", startDay);
		parent.put("increment", decrement);

		cal.add(Calendar.DAY_OF_YEAR, 0 - decrement);

		ObjectNode child1 = objectMapper.createObjectNode();
		child1.put("daterange", sdf.format(cal.getTime()) + " - " + sdf.format(new Date()));
		// one week ago
		child1.put("data", populateArrayNode(reportService.findAllCampaignApplications(decrement), decrement));
		root.add(child1);

		return root.toString();
	}

	public String reportPaidForCampaigns() {
		ArrayNode root = objectMapper.createArrayNode();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		Date today = new Date();
		Calendar cal = Calendar.getInstance();

		cal.setTime(today);
		cal.add(Calendar.DAY_OF_YEAR, 0 - 7);

		ObjectNode child1 = objectMapper.createObjectNode();
		child1.put("week1", sdf.format(cal.getTime()) + " - " + sdf.format(new Date()));
		// one week ago
		child1.put("data", populateArrayNode(reportService.findAllCampaignApplications(7), 7));
		root.add(child1);

		ObjectNode child2 = objectMapper.createObjectNode();
		String weekEnd = sdf.format(cal.getTime());
		cal.add(Calendar.DAY_OF_YEAR, 0 - 14);
		// two week ago
		child2.put("week2", sdf.format(cal.getTime()) + " - " + weekEnd);
		child2.put("data", populateArrayNode(reportService.findAllCampaignApplications(14), 14));
		root.add(child2);

		ObjectNode child3 = objectMapper.createObjectNode();
		weekEnd = sdf.format(cal.getTime());
		cal.add(Calendar.DAY_OF_YEAR, 0 - 21);
		// two week ago
		child3.put("week3", sdf.format(cal.getTime()) + " - " + weekEnd);
		// three week ago
		child3.put("data", populateArrayNode(reportService.findAllCampaignApplications(21), 21));
		root.add(child3);

		ObjectNode child4 = objectMapper.createObjectNode();
		weekEnd = sdf.format(cal.getTime());
		cal.add(Calendar.DAY_OF_YEAR, 0 - 28);
		child4.put("week4", sdf.format(cal.getTime()) + " - " + weekEnd);
		// four week ago
		child4.put("data", populateArrayNode(reportService.findAllCampaignApplications(28), 28));
		root.add(child4);

		return root.toString();
	}

	/*
	 * public String campaignDetail(String appId) { List<ItunesApplication> apps
	 * = itunesApplicationManager.findAppByAppId(appId, 3); return
	 * pupulateNode(apps).toString(); }
	 */

	public String reportPaidNewApps(String date, int fallInNumber, int hours) {
		List<ItunesApplication> apps = itunesPaidApplicationDao.findAllNewApps(date, fallInNumber, hours);
		return pupulateNode(apps).toString();
	}

	public String reportPaidHotApps(String date, int moved, int hours) {
		List<ItunesApplication> apps = itunesPaidApplicationDao.findHotApps(date, moved, hours);
		return pupulateNode(apps).toString();
	}

	public String reportPaidNotHot(String date, int moved, int hours) {
		List<ItunesApplication> apps = itunesPaidApplicationDao.findNotHotApps(date, moved, hours);
		return pupulateNode(apps).toString();
	}

	public String reportPaidOldApps(String date, int dropOutNumber, int hours) {
		List<ItunesApplication> apps = itunesPaidApplicationDao.findDroppedApps(date, dropOutNumber, hours);
		return pupulateNode(apps).toString();
	}

	public String reportPaidPriorTop300Apps(int hours) {
		List<ItunesApplication> apps = itunesPaidApplicationDao.findTop300OfTheHour(hours);
		return pupulateNode(apps).toString();
	}
	
	public String getPaidAppByItunesAppId(String appId, int startHours, int lookBackHours){
		return pupulateNode(itunesPaidApplicationDao.getAppById(appId, startHours, lookBackHours)).toString();
	}
	
	public String reportNewlyReleasedAppsDoingWell(){
		Set<String> list = itunesPaidApplicationDao.findNewClientsByItunesFeaturedApp();
		ArrayNode node = objectMapper.createArrayNode();
		for(String id : list){
			node.add(id);
		}
		return node.toString();
	}

}
