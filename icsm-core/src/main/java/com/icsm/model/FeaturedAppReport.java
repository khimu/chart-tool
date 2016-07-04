package com.icsm.model;

import com.icsmobile.faadplatform.dao.FeaturedApp;
import com.icsmobile.faadplatform.dao.ItunesApplication;

public class FeaturedAppReport {

	private FeaturedApp featuredApp;
	private ItunesApplication itunesApp;

	public FeaturedApp getFeaturedApp() {
		return featuredApp;
	}

	public void setFeaturedApp(FeaturedApp featuredApp) {
		this.featuredApp = featuredApp;
	}

	public ItunesApplication getItunesApp() {
		return itunesApp;
	}

	public void setItunesApp(ItunesApplication itunesApp) {
		this.itunesApp = itunesApp;
	}

	
}
