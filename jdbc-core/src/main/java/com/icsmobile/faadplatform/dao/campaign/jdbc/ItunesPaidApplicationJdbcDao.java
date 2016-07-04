package com.icsmobile.faadplatform.dao.campaign.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.icsmobile.faadplatform.dao.BaseDao;
import com.icsmobile.faadplatform.dao.ItunesApplication;
import com.icsmobile.faadplatform.dao.ItunesApplicationRowMapper;
import com.icsmobile.faadplatform.dao.campaign.IItunesPaidApplicationDao;

@Repository
public class ItunesPaidApplicationJdbcDao extends BaseDao implements IItunesPaidApplicationDao {
	
	private static final String QUERY_BATCH_UPDATE_ALREADY_TARGETED = "update itunes_application set already_targeted = 1, modified_on = NOW() where itunes_app_id = ? and already_targeted = 0";

	private final static String QUERY = "select distinct * from itunes_application ia1 join (select id from itunes_application where pull_date <= ? and pull_date >= ? and price_type != 'Free') as ia2 on ia1.id = ia2.id ";

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	public ItunesPaidApplicationJdbcDao(DataSource itunesAppDataSource) {
		this.setDataSource(itunesAppDataSource);
	}

	public ItunesPaidApplicationJdbcDao() {
	}
	
	public void batchUpdateAlreadyTargetedApp(final List<String> itunesAppIds) {
		getJdbcTemplate().batchUpdate(QUERY_BATCH_UPDATE_ALREADY_TARGETED, new BatchPreparedStatementSetter() {
			 
			@Override
			public void setValues(PreparedStatement pstmt, int i) throws SQLException {
				pstmt.setString(1, itunesAppIds.get(i));
			}
		 
			@Override
			public int getBatchSize() {
				return itunesAppIds.size();
			}
		  });
	}
	
	
	public List<ItunesApplication> findAllAppsOneHourAgo() {
		return findTop300OfTheHour(1);
				
	}	
	
	public Set<String> findNewClientsByItunesFeaturedApp(){
		String sql = "select distinct itunes_app_id from itunes_application where itunes_featured_app = 1 and already_targeted = 0 group by itunes_app_id";
		return new TreeSet(getJdbcTemplate().queryForList(sql, String.class));
	}

	public List<ItunesApplication> findTop300OfTheHour(int hours) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.HOUR, 0 - hours);
		
		String end = sdf.format(cal.getTime());
		cal.add(Calendar.HOUR, 0 - (hours + 1));
		String start = sdf.format(cal.getTime());

		String sql = "select distinct * from itunes_application ia1 join (select id from itunes_application where pull_date <= ? and pull_date >= ? and price_type != 'Free' order by id limit 300) as ia2 on ia1.id = ia2.id order by ia1.ranking asc limit 300";

		
		List<ItunesApplication> list = getJdbcTemplate().query(sql, new ItunesApplicationRowMapper(), end, start);
		
		if(list.size() == 0){
			cal.add(Calendar.HOUR, 0 - (hours + 2));
			start = sdf.format(cal.getTime());
			list = getJdbcTemplate().query(sql, new ItunesApplicationRowMapper(), end, start);
		}
		return list;
	}	
		
	/*
	 * Select a list of records for the given list of app ids 
	 * 
	 * allows going back in time to retrieve the position of the app at the given point in time
	 * 
	 * back up by hours to have finer control over the granularity of the data
	 * 
	 */
	public List<ItunesApplication> pullLastestRecordsByAppIds(List<String> ids, int hours){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.HOUR, 0 - hours);

		String end = sdf.format(cal.getTime());
		cal.add(Calendar.HOUR, 0 - (hours + 8));
		String start = sdf.format(cal.getTime());
				
        StringBuilder q = new StringBuilder();
        for(String name :ids){
                q.append("?, ");
        }
		
        String sql = QUERY + " where ia1.itunes_app_id in (" + q.replace(q.lastIndexOf(","), q.length(), "") + ") group by ia1.itunes_app_id order by ia1.ranking asc";
        List<Object> params = new ArrayList<Object>();
        params.add(end);
        params.add(start);
        for(int i = 0 ; i < ids.size(); i ++){
                params.add(ids.get(i));
        }   		
		return getJdbcTemplate().query(sql, params.toArray(), new ItunesApplicationRowMapper());
	}

	/*
	 * Names from campaign table will not match that from itunes
	 * 
	 * (non-Javadoc)
	 * @see com.icsm.dao.ItunesApplicationDao#findAllApplicationsForNames(java.util.List, int)
	 */
	@Deprecated
	public List<ItunesApplication> findAllApplicationsForNames(List<String> names, int hours) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.HOUR, 0 - hours);
		
		String end = sdf.format(cal.getTime());
		cal.add(Calendar.HOUR, 0 - (hours + 8));
		String start = sdf.format(cal.getTime());
		/*
		 * gives me all records matching given names that is no later then the date provided
		 * group by will eliminate duplicates
		 * 
		 */
		String sql =  QUERY + " where lower(ia1.name) in (?) group by ia1.itunes_app_id order by ia1.ranking asc";

		
		return getJdbcTemplate().query(sql, new ItunesApplicationRowMapper(), end, start, names);
	}

	public List<ItunesApplication> getAppsByCompanyName(String name, int hours) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.HOUR, 0 - hours);
		String end = sdf.format(cal.getTime());
		cal.add(Calendar.HOUR, 0 - (hours + 8));
		String start = sdf.format(cal.getTime());

		String sql = QUERY + " where lower(ia1.company_name) like ? group by ia1.itunes_app_id order by ia1.ranking asc";
		return getJdbcTemplate().query(sql, new ItunesApplicationRowMapper(), end, start, name.toLowerCase());
	}

	/**
	 * 
	 * Find all new apps for the given hour
	 * 
	 * ie if you are finding all new apps 48 hours ago, provide 48 hours in the parameter.  This will tell you what app was new 48 hours ago
	 * 
	 * fallInNumber is the ranking.  Only pull data from 0 to fallInNumber
	 * 
	 */
	public List<ItunesApplication> findAllNewApps(String date, int fallInNumber, int hours) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

		SimpleDateFormat inputSDF = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		if (date == null) {
			cal.setTime(today);
		} else {
			try {
				cal.setTime(inputSDF.parse(date));
			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}
		cal.add(Calendar.HOUR, 0 - hours);
		
		String end = sdf.format(cal.getTime());
		cal.add(Calendar.HOUR, 0 - (hours + 8));
		String start = sdf.format(cal.getTime());

		String sql = QUERY + " where new_top = 1 and ia1.ranking <= ? group by ia1.itunes_app_id order by ia1.ranking asc";

		return getJdbcTemplate().query(sql, new ItunesApplicationRowMapper(), end, start, fallInNumber);
	}

	public List<ItunesApplication> findHotApps(String date, int moved, int hours) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		SimpleDateFormat inputSDF = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		if (date == null) {
			cal.setTime(today);
		} else {
			try {
				cal.setTime(inputSDF.parse(date));
			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}

		cal.add(Calendar.HOUR, 0 - hours);
		
		String end = sdf.format(cal.getTime());
		cal.add(Calendar.HOUR, 0 - (hours + 8));
		String start = sdf.format(cal.getTime());

		
		String sql = QUERY + " where moved >= ? group by ia1.itunes_app_id order by ia1.ranking";
		
		return getJdbcTemplate().query(sql, new ItunesApplicationRowMapper(), end, start, moved);
	}
	
	public List<ItunesApplication> findNotHotApps(String date, int moved, int hours) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		SimpleDateFormat inputSDF = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		if (date == null) {
			cal.setTime(today);
		} else {
			try {
				cal.setTime(inputSDF.parse(date));
			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}

		cal.add(Calendar.HOUR, 0 - hours);

		String end = sdf.format(cal.getTime());
		cal.add(Calendar.HOUR, 0 - (hours + 8));
		String start = sdf.format(cal.getTime());

		
		String sql = QUERY + " where moved <= ? group by ia1.itunes_app_id order by ia1.ranking";
		
		return getJdbcTemplate().query(sql, new ItunesApplicationRowMapper(), end, start, - moved);
	}

	/*
	 * Find all old apps which has dropped out of the top 200
	 * 
	 * Data is pulled according to the hour provided.  If you want to look at what apps dropped in the current hour,
	 * simply provide the 0 hours as the parameter.
	 * 
	 * If you want to see what apps dropped 8 hours ago, simply provide 8 for the hour.
	 * 
	 * dropOutNumber is the ranking value; anything greater than this value will be counted
	 */
	public List<ItunesApplication> findDroppedApps(String date, int dropOutNumber, int hours) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		SimpleDateFormat inputSDF = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		if (date == null) {
			cal.setTime(today);
		} else {
			try {
				cal.setTime(inputSDF.parse(date));
			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}

		cal.add(Calendar.HOUR, 0 - hours);
		
		

		String end = sdf.format(cal.getTime());
		cal.add(Calendar.HOUR, 0 - (hours + 8));
		String start = sdf.format(cal.getTime());

		
		String sql = QUERY + " where ia1.dropped = 1 and ia1.ranking >= ? group by ia1.itunes_app_id order by ia1.ranking asc";
		
		return getJdbcTemplate().query(sql, new ItunesApplicationRowMapper(), end, start, dropOutNumber);
	}

	public List<ItunesApplication> getAppById(String appId, int startHours, int lookBackHours) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.HOUR, 0 - startHours);
		String now = sdf.format(cal.getTime());
		cal.add(Calendar.HOUR, 0 - (startHours + lookBackHours));
		String start = sdf.format(cal.getTime());

		
		String sql = "select * from itunes_application where pull_date <= ? and pull_date >= ? and itunes_app_id = ? and price_type != 'Free' order by pull_date desc";
		
		return getJdbcTemplate().query(sql, new ItunesApplicationRowMapper(), now, start, appId);
	}

}
