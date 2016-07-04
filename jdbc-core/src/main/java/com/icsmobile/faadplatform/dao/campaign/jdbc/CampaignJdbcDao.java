/*
 * @(#)com.icsmobile.faadplatform.dao.campaign.jdbc
 * 
 * Summary : Data Access Layer JDBC implementation for campaign section
 *
 *
 * JDK version : 6.0
 * Version History : 1.0    Mar 2, 2011
 */
package com.icsmobile.faadplatform.dao.campaign.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.icsmobile.faadplatform.dao.BaseDao;
import com.icsmobile.faadplatform.dao.CampaignDTO;
import com.icsmobile.faadplatform.dao.CampaignRowMapper;
import com.icsmobile.faadplatform.dao.campaign.ICampaignDao;

/**
 * Data Access Layer JDBC implementation for campaigns
 *
 * @author Athir Gillani
 */
@Repository
public class CampaignJdbcDao extends BaseDao implements ICampaignDao {
	
	/**
	 * Instantiates a new campaign jdbc dao.
	 *
	 * @param campaignDataSource the campaign data source
	 */
	@Autowired
	public CampaignJdbcDao(DataSource campaignDataSource) {
		this.setDataSource(campaignDataSource);
	}

    public List<String> fetchCampaigns() {
            List<String> campaigns = new ArrayList<String>();

            // execute query
            SqlRowSet campaignRow = getJdbcTemplate().queryForRowSet("SELECT distinct name FROM campaigns", new Object[] {});
            
            if (!campaignRow.next()) {
                    return null;
            }
            
            // create campaign object
            campaigns.add(campaignRow.getString(1).toLowerCase());  

            return campaigns;
    }

    public List<CampaignDTO> getCampaignDetail(final List<String> names){

            StringBuilder q = new StringBuilder();
            for(String name :names){
                    q.append("?, ");
            }
            

            String sql = "SELECT distinct name, description, start_date, end_date, credit_reward FROM campaigns where lower(name) in (" + q.replace(q.lastIndexOf(","), q.length(), "") + ")";

            List<Object> params = new ArrayList<Object>();
            
            for(int i = 1 ; i - 1 < names.size(); i ++){
                    params.add(names.get(i - 1));
            }                       
            
            return getJdbcTemplate().query(sql, params.toArray(), new CampaignRowMapper());

    }
	

}
