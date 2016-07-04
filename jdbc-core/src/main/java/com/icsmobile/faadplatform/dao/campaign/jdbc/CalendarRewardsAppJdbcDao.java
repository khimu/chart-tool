package com.icsmobile.faadplatform.dao.campaign.jdbc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.icsmobile.faadplatform.dao.BaseDao;
import com.icsmobile.faadplatform.dao.FeaturedApp;
import com.icsmobile.faadplatform.dao.FeaturedAppRowMapper;
import com.icsmobile.faadplatform.dao.campaign.ICalendarRewardsAppDao;

@Repository
public class CalendarRewardsAppJdbcDao extends BaseDao implements ICalendarRewardsAppDao {
	
	private final static String ITUNES_APPID_SQL = "select ua.app_store_id, cal.start_date, cal.end_date from faad_user.applications ua join faad_reward.calendar cal where ua.application_id=cal.application_id and cal.start_date >= ? and cal.end_date <= ? and cal.application_id not in (select c.application_id from faad_reward.calendar c where c.status = 3 or c.status = 1)";
	
	@Autowired
	public CalendarRewardsAppJdbcDao(DataSource calendarRewardsAppDataSource) {
		this.setDataSource(calendarRewardsAppDataSource);
	}

    public List<FeaturedApp> getApplicationIdForCalendarRewards() {
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    		Date today = new Date();

    		Calendar cal = Calendar.getInstance();
    		cal.setTime(today);
    		cal.add(Calendar.DAY_OF_YEAR, 0 - 30);
            
            // execute query
            return getJdbcTemplate().query(ITUNES_APPID_SQL, new FeaturedAppRowMapper(), sdf.format(cal.getTime()), sdf.format(today));
    }

}
