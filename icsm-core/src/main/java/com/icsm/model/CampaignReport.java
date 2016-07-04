package com.icsm.model;

import java.io.Serializable;

import com.icsmobile.faadplatform.dao.ItunesApplication;


import com.icsmobile.faadplatform.dao.CampaignDTO;


public class CampaignReport implements Serializable {
	private CampaignDTO campaign;
	private ItunesApplication stats;
	public CampaignDTO getCampaign() {
		return campaign;
	}
	public void setCampaign(CampaignDTO campaign) {
		this.campaign = campaign;
	}
	public ItunesApplication getStats() {
		return stats;
	}
	public void setStats(ItunesApplication stats) {
		this.stats = stats;
	}
	
	
}
