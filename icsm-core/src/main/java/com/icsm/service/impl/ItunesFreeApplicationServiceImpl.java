package com.icsm.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icsm.service.ItunesFreeApplicationService;
import com.icsmobile.faadplatform.dao.ItunesApplication;
import com.icsmobile.faadplatform.dao.campaign.IItunesFreeApplicationDao;

@Component("itunesFreeApplicationService")
public class ItunesFreeApplicationServiceImpl implements ItunesFreeApplicationService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IItunesFreeApplicationDao itunesFreeApplicationDao;
	
	/*
	//@Autowired
	private ICampaignDao campaignDao;	
	
	//@Autowired
	private IItunesApplicationSimpleDao itunesApplicationSimpleDao;
	*/

	public ItunesFreeApplicationServiceImpl(){
	}
	
	public List<ItunesApplication> getAppsByCompanyName(String name, int days){
		return itunesFreeApplicationDao.getAppsByCompanyName(name, days);
	}

	public List<ItunesApplication> findAllNewApps(String date, int fallInNumber, int hours) {
		return itunesFreeApplicationDao.findAllNewApps(date, fallInNumber, hours);
	}


	public List<ItunesApplication> findDroppedApps(String date, int dropOutNumber, int hours) {
		return itunesFreeApplicationDao.findDroppedApps(date, dropOutNumber, hours);				
	}

	@Override
	public List<ItunesApplication> findAllApplicationsForNames(List<String> names, int days) {
		return itunesFreeApplicationDao.findAllApplicationsForNames(names, days);
	}


	@Override
	public List<ItunesApplication> pullLastestRecordsByAppIds(List<String> ids, int hours) {
		return itunesFreeApplicationDao.pullLastestRecordsByAppIds(ids, hours);
	}


	@Override
	public List<ItunesApplication> findHotApps(String date, int moved, int hours) {
		return itunesFreeApplicationDao.findHotApps(date, moved, hours);
	}


	@Override
	public List<ItunesApplication> findNotHotApps(String date, int moved, int hours) {
		return itunesFreeApplicationDao.findNotHotApps(date, moved, hours);
	}

	public List<ItunesApplication> findTop300OfTheHour(int hours){
		return itunesFreeApplicationDao.findTop300OfTheHour(hours);
	}

}
