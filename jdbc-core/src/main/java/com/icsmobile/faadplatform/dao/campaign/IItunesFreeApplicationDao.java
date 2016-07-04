package com.icsmobile.faadplatform.dao.campaign;

import java.util.List;

import com.icsmobile.faadplatform.dao.ItunesApplication;
import com.icsmobile.faadplatform.dao.ItunesApplicationRowMapper;

public interface IItunesFreeApplicationDao {
	

	
	// 
	/*
	 * get latest top 300
	 * 
	 * This is no different from what you see in itunes
	 * 
	 * (non-Javadoc)
	 * @see com.icsm.dao.ItunesApplicationDao#findAllAppsOneHourAgo()
	 */
	public List<ItunesApplication> findAllAppsOneHourAgo();
	
	public List<ItunesApplication> findTop300OfTheHour(int hours);
	
	/*
	 * Will be used to send a notification out to sales them when an app has risen more then 20 positions in the last 4 hours
	 */
	public List<ItunesApplication> findTotalMovementGreaterThan20(List<String> itunesAppId);


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
	
	public List<ItunesApplication> getAppById(String appId, int startHours, int lookBackHours);

}
