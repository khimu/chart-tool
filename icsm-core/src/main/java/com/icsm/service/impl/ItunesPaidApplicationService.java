package com.icsm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icsm.service.IItunesPaidApplicationService;
import com.icsmobile.faadplatform.dao.ItunesApplication;
import com.icsmobile.faadplatform.dao.campaign.IItunesPaidApplicationDao;

@Component("itunesPaidApplicationService")
public class ItunesPaidApplicationService implements IItunesPaidApplicationService {

	@Autowired
	private IItunesPaidApplicationDao dao;

	@Override
	public List<ItunesApplication> findAllAppsOneHourAgo() {
		return dao.findAllAppsOneHourAgo();
	}

	@Override
	public List<ItunesApplication> findTop300OfTheHour(int hours) {
		return dao.findTop300OfTheHour(hours);
	}

	@Override
	public List<ItunesApplication> pullLastestRecordsByAppIds(List<String> ids, int hours) {
		return dao.pullLastestRecordsByAppIds(ids, hours);
	}

	@Override
	public List<ItunesApplication> findAllApplicationsForNames(List<String> names, int hours) {
		return dao.findAllApplicationsForNames(names, hours);
	}

	@Override
	public List<ItunesApplication> getAppsByCompanyName(String name, int hours) {
		return dao.getAppsByCompanyName(name, hours);
	}

	@Override
	public List<ItunesApplication> findAllNewApps(String date, int fallInNumber, int hours) {
		return null;
	}

	@Override
	public List<ItunesApplication> findHotApps(String date, int moved, int hours) {
		return dao.findHotApps(date, moved, hours);
	}

	@Override
	public List<ItunesApplication> findNotHotApps(String date, int moved, int hours) {
		return dao.findNotHotApps(date, moved, hours);
	}

	@Override
	public List<ItunesApplication> findDroppedApps(String date, int dropOutNumber, int hours) {
		return dao.findDroppedApps(date, dropOutNumber, hours);
	}

}
