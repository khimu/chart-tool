package com.icsm.service;

import java.util.List;

import com.icsmobile.faadplatform.dao.ItunesApplication;

public interface ItunesFreeApplicationService {


	public List<ItunesApplication> findTop300OfTheHour(int hours);

	/*
	 * Select a list of records for the given list of app ids 
	 * 
	 * allows going back in time to retrieve the position of the app at the given point in time
	 * 
	 * back up by hours to have finer control over the granularity of the data
	 * 
	 */
	public List<ItunesApplication> pullLastestRecordsByAppIds(List<String> ids, int hours);

	public List<ItunesApplication> findAllApplicationsForNames(List<String> names, int hours);

	public List<ItunesApplication> getAppsByCompanyName(String name, int hours);

	public List<ItunesApplication> findAllNewApps(String date, int fallInNumber, int hours);

	public List<ItunesApplication> findHotApps(String date, int moved, int hours);
	
	public List<ItunesApplication> findNotHotApps(String date, int moved, int hours);

	/*
	 * Find all old apps which has dropped out of the top 200
	 */
	public List<ItunesApplication> findDroppedApps(String date, int dropOutNumber, int hours);
	
}
