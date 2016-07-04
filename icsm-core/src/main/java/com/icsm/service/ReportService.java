package com.icsm.service;

import java.util.List;

import com.icsm.model.CampaignReport;
import com.icsm.model.FeaturedAppReport;

public interface ReportService {

	public List<CampaignReport> findAllCampaignApplications(int days);
	
	public List<FeaturedAppReport> findAllRewardsApp(int hours);
	
	public List<FeaturedAppReport> findAllPaidRewardsApp(int hours);
}
