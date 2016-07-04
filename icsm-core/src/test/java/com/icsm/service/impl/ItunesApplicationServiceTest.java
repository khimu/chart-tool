package com.icsm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.icsm.model.CampaignReport;
import com.icsm.service.BaseManagerTestCase;
import com.icsm.service.ItunesFreeApplicationService;

public class ItunesApplicationServiceTest extends BaseManagerTestCase {
	
	@Autowired
	private ItunesFreeApplicationService itunesApplicationManager;
	
	@Test
	public void testFindAllApplicationsForNames(){
		List<String> names = new ArrayList<String>();
		
		names.add("scrabblefree");
		names.add("skyburger");
		
		List<CampaignReport> found = itunesApplicationManager.findAllCampaignApplications(28);
		//ApplicationsForNames(names, 28);
		
		Assert.assertNotNull(found);
		
		Assert.assertFalse(found.isEmpty());

		for(CampaignReport ia : found){
			System.out.println(ia.getStats().getName());
			System.out.println(ia.getStats().getBundleId());
			System.out.println(ia.getStats().getNewtop());
		}
	}
}
