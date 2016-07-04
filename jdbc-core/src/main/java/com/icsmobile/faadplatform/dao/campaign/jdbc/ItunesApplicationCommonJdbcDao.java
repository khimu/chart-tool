package com.icsmobile.faadplatform.dao.campaign.jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.icsmobile.faadplatform.dao.ItunesApplication;
import com.icsmobile.faadplatform.dao.ItunesApplicationRowMapper;
import com.icsmobile.faadplatform.dao.campaign.IItunesApplicationCommonDao;

@Repository
public class ItunesApplicationCommonJdbcDao extends SimpleJdbcDaoSupport implements IItunesApplicationCommonDao {

	private final Log log = LogFactory.getLog(getClass());

	private static final String QUERY_BATCH_UPDATE_LAST_BATCH = "update itunes_application set last_batch = 0, modified_on = NOW() where id = ?";
	
	private static final String QUERY_BATCH_SAVE = "insert into itunes_application"
			+ " (name, pull_date, ranking, moved, new_top, dropped, last_batch, bundle_id, itunes_app_id, category, company_name, company_itunes_link, price_type, amount, currency, link, image, release_date, has_tag, itunes_featured_app, already_targeted, created_on, modified_on) "
			+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	

	@Autowired
    public ItunesApplicationCommonJdbcDao(DataSource itunesAppDataSource){
    	this.setDataSource(itunesAppDataSource);
    }
	
	
	public List<ItunesApplication> getPaidLastBatch() {
		return getJdbcTemplate().query("select * from itunes_application where last_batch = 1 and price_type != 'Free'", new ItunesApplicationRowMapper());
	}

	public List<ItunesApplication> getFreeLastBatch() {
		return getJdbcTemplate().query("select * from itunes_application where last_batch = 1 and price_type LIKE 'Free'", new ItunesApplicationRowMapper());
	}

	public void batchUpdateLastBatch(final List<ItunesApplication> data) throws Exception {
		
		getJdbcTemplate().batchUpdate(QUERY_BATCH_UPDATE_LAST_BATCH, new BatchPreparedStatementSetter() {
			 
			@Override
			public void setValues(PreparedStatement pstmt, int i) throws SQLException {
				pstmt.setLong(1, data.get(i).getId());
			}
		 
			@Override
			public int getBatchSize() {
				return data.size();
			}
		  });
	
	}
	
	
	public void batchSave(final List<ItunesApplication> data) throws Exception {
		
		getJdbcTemplate().batchUpdate(QUERY_BATCH_SAVE, new BatchPreparedStatementSetter() {
			 
			@Override
			public void setValues(PreparedStatement pstmt, int i) throws SQLException {
				pstmt.setString(1, data.get(i).getName());
				pstmt.setString(2, data.get(i).getPullDate());
				pstmt.setInt(3, data.get(i).getRanking());
				pstmt.setInt(4, data.get(i).getMoved());

				pstmt.setInt(5, data.get(i).getNewtop());
				pstmt.setInt(6, data.get(i).getDropped());
				pstmt.setInt(7, data.get(i).getLastBatch());

				pstmt.setString(8, data.get(i).getBundleId());
				pstmt.setString(9, data.get(i).getItunesAppId());
				pstmt.setString(10, data.get(i).getCategory());
				pstmt.setString(11, data.get(i).getCompanyName());
				pstmt.setString(12, data.get(i).getCompanyItunesLink());
				pstmt.setString(13, data.get(i).getPriceType());
				pstmt.setString(14, data.get(i).getAmount());
				pstmt.setString(15, data.get(i).getCurrency());
				pstmt.setString(16, data.get(i).getLink());
				pstmt.setString(17, data.get(i).getImage());
				pstmt.setString(18, data.get(i).getReleaseDate());
				pstmt.setInt(19, data.get(i).getHasTag());
				pstmt.setInt(20, data.get(i).getItunesFeaturedApp());
				pstmt.setInt(21, data.get(i).getAlreadyTargeted());
				pstmt.setDate(22, new Date(System.currentTimeMillis()));
				pstmt.setDate(23, new Date(System.currentTimeMillis()));
			}
		 
			@Override
			public int getBatchSize() {
				return data.size();
			}
		  });
	
	}

	// combine report for both paid and free into one report for a given app id
	public List<ItunesApplication> getAppById(String appId, int startHours, int lookBackHours) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

		java.util.Date today = new java.util.Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.HOUR, 0 - startHours);
		String now = sdf.format(cal.getTime());
		cal.add(Calendar.HOUR, 0 - (startHours + lookBackHours));
		String start = sdf.format(cal.getTime());

		
		String sql = "select * from itunes_application where pull_date <= ? and pull_date >= ? and itunes_app_id = ? order by pull_date desc";
		
		return getJdbcTemplate().query(sql, new ItunesApplicationRowMapper(), now, start, appId);
	}


}
