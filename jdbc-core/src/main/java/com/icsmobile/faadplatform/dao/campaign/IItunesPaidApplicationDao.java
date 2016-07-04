package com.icsmobile.faadplatform.dao.campaign;

import java.util.List;
import java.util.Set;

import com.icsmobile.faadplatform.dao.ItunesApplication;

public interface IItunesPaidApplicationDao {
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
	 * This only applies to paid apps
	 * 
	 * If an app was newly released within the last 5 days and it is in the top 200, assume it was featured by itunes and
	 * send out a notification.
	 * 
	 * Use this during scrapping to see if any of these apps have fallen out of the top 200 in which case a sales person
	 * needs to be notified.
	 */
	public Set<String> findNewClientsByItunesFeaturedApp();
	
	/*
	 * Update this flag if an email has been sent out 
	 */
	public void batchUpdateAlreadyTargetedApp(List<String> itunesAppIds);


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
