package com.icsmobile.faadplatform.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ItunesApplicationRowMapper implements RowMapper<ItunesApplication>
{
    public ItunesApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
            ItunesApplication ia = new ItunesApplication();
            ia.setId(rs.getLong("id"));
            ia.setName(rs.getString("name"));
            ia.setPullDate(rs.getString("pull_date"));
            ia.setRanking(rs.getInt("ranking"));
            ia.setMoved(rs.getInt("moved"));
            ia.setNewtop(rs.getInt("new_top"));
            ia.setDropped(rs.getInt("dropped"));
            ia.setLastBatch(rs.getInt("last_batch"));
            ia.setHasTag(rs.getInt("has_tag"));
            ia.setBundleId(rs.getString("bundle_id"));
            ia.setItunesAppId(rs.getString("itunes_app_id"));
            ia.setCategory(rs.getString("category"));
            ia.setCompanyName(rs.getString("company_name"));
            ia.setCompanyItunesLink(rs.getString("company_itunes_link"));
            ia.setPriceType(rs.getString("price_type"));
            ia.setAmount(rs.getString("amount"));
            ia.setCurrency(rs.getString("currency"));
            ia.setLink(rs.getString("link"));
            ia.setImage(rs.getString("image"));
            ia.setReleaseDate(rs.getString("release_date"));
            ia.setItunesFeaturedApp(rs.getInt("itunes_featured_app"));
            ia.setAlreadyTargeted(rs.getInt("already_targeted"));
            ia.setModifiedOn(rs.getDate("modified_on"));
            ia.setCreatedOn(rs.getDate("created_on"));
            return ia;
    }

}
