package com.icsmobile.faadplatform.dao;

import java.io.Serializable;
import java.util.Date;

public class FeaturedApp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String itunesAppId;
	private Date startDate;
	private Date endDate;

	public String getItunesAppId() {
		return itunesAppId;
	}

	public void setItunesAppId(String itunesAppId) {
		this.itunesAppId = itunesAppId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
