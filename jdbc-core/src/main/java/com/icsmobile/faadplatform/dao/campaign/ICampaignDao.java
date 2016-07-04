/*
 * @(#)com.icsmobile.faadplatform.dao.campaign
 * 
 * Summary : Exposes Data Access Layer interface for campaign section 
 *
 *
 * JDK version : 6.0
 * Version History : 1.0    Mar 2, 2011
 */
package com.icsmobile.faadplatform.dao.campaign;

import java.util.List;
import java.util.Map;

import com.icsmobile.faadplatform.dao.BaseBO;
import com.icsmobile.faadplatform.dao.CampaignBO;
import com.icsmobile.faadplatform.dao.CampaignDTO;

/**
 * Exposes Data Access Layer interface for campaigns
 * 
 * @author Athir Gillani
 */
public interface ICampaignDao {
	
	public List<String> fetchCampaigns();

	public List<CampaignDTO> getCampaignDetail(final List<String> names);
}
