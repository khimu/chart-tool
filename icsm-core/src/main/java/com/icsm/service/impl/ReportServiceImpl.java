package com.icsm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icsm.model.CampaignReport;
import com.icsm.model.FeaturedAppReport;
import com.icsm.service.IItunesPaidApplicationService;
import com.icsm.service.ItunesFreeApplicationService;
import com.icsm.service.ReportService;
import com.icsmobile.faadplatform.dao.CampaignDTO;
import com.icsmobile.faadplatform.dao.FeaturedApp;
import com.icsmobile.faadplatform.dao.ItunesApplication;
import com.icsmobile.faadplatform.dao.campaign.ICalendarRewardsAppDao;
import com.icsmobile.faadplatform.dao.campaign.ICampaignDao;

@Component("reportService")
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ICampaignDao campaignDao;
	
	@Autowired
	private ItunesFreeApplicationService itunesFreeApplicationService;
	
	@Autowired
	private ICalendarRewardsAppDao calendarRewardsAppDao;
	
	@Autowired
	private IItunesPaidApplicationService itunesPaidApplicationService;
	

	public List<CampaignReport> findAllCampaignApplications(int days){		
		Map<String, CampaignReport> reports = new HashMap<String, CampaignReport>();
		
		// get list of all applications where a campaign was ran for
		List<String> names = campaignDao.fetchCampaigns();
		
		List<String> foundMatchNames = new ArrayList<String>();
		
		if(names != null && names.isEmpty() == false){
			List<ItunesApplication> ias = itunesFreeApplicationService.findAllApplicationsForNames(names, days);
			if(ias != null && ias.isEmpty() == false){
				for(ItunesApplication ia : ias){
					CampaignReport report = new CampaignReport();
					report.setStats(ia);
					reports.put(ia.getName().toLowerCase(), report);
					foundMatchNames.add(ia.getName().toLowerCase());
				}

				List<CampaignDTO> dtos = campaignDao.getCampaignDetail(foundMatchNames);
				for(CampaignDTO dto : dtos){
					CampaignReport report = reports.get(dto.getApplicationName().toLowerCase());
					if(report != null){
						report.setCampaign(dto);
					}
				}

			}
		}
		return new ArrayList<CampaignReport>(reports.values());
	}
	
	public List<FeaturedAppReport> findAllRewardsApp(int hours){
		List<FeaturedAppReport> reports = new ArrayList<FeaturedAppReport>();
		List<String> itunesAppIds = new ArrayList<String>();
		Map<String, FeaturedApp> result = new HashMap<String, FeaturedApp>();
		List<FeaturedApp> featuredApps = calendarRewardsAppDao.getApplicationIdForCalendarRewards();
		for(FeaturedApp app : featuredApps){
			itunesAppIds.add(app.getItunesAppId());
			result.put(app.getItunesAppId(), app);
		}
		List<ItunesApplication> itunesApps = itunesFreeApplicationService.pullLastestRecordsByAppIds(itunesAppIds, hours);
		
		for(ItunesApplication ia : itunesApps){
			FeaturedAppReport report = new FeaturedAppReport();
			FeaturedApp app = result.get(ia.getItunesAppId());
			report.setFeaturedApp(app);
			report.setItunesApp(ia);
			reports.add(report);
		}
		return reports;
	}
	
	public List<FeaturedAppReport> findAllPaidRewardsApp(int hours){
		List<FeaturedAppReport> reports = new ArrayList<FeaturedAppReport>();
		List<String> itunesAppIds = new ArrayList<String>();
		Map<String, FeaturedApp> result = new HashMap<String, FeaturedApp>();
		List<FeaturedApp> featuredApps = calendarRewardsAppDao.getApplicationIdForCalendarRewards();
		for(FeaturedApp app : featuredApps){
			itunesAppIds.add(app.getItunesAppId());
			result.put(app.getItunesAppId(), app);
		}
		List<com.icsmobile.faadplatform.dao.ItunesApplication> itunesApps = itunesPaidApplicationService.pullLastestRecordsByAppIds(itunesAppIds, hours);
		
		for(com.icsmobile.faadplatform.dao.ItunesApplication ia : itunesApps){
			FeaturedAppReport report = new FeaturedAppReport();
			FeaturedApp app = result.get(ia.getItunesAppId());
			report.setFeaturedApp(app);
			report.setItunesApp(ia);
			reports.add(report);
		}
		return reports;
	}


}
