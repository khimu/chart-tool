package com.icsmobile.faadplatform.dao;

import java.io.Serializable;
import java.util.Date;

public class ItunesApplication implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	// name of the app
	private String name;

	// the time in which the record was pulled from itunes
	private String pullDate;

	// negative implies fallen off chart
	private Integer ranking;

	// TODO: pick a time slot
	// Indicates how much the app has moved since the last 15 minute or one
	// hour.
	private Integer moved;

	// flag when a new app has moved to the top
	private Integer newtop = 0;

	// flags when an app is no longer in the top
	private Integer dropped = 0;
	
	private Integer lastBatch = 0;
	
	private Integer hasTag = 0;

	// Metadata from itunes on the application
	private String bundleId;
	private String itunesAppId;
	private String category;
	private String companyName;
	private String companyItunesLink;
	private String priceType;
	private String amount;
	private String currency;
	private String link;
	private String image;
	private String releaseDate;
	
	private Integer itunesFeaturedApp = 0;
	private Integer alreadyTargeted = 0;
	private Date modifiedOn;
	private Date createdOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getLastBatch(){
		return lastBatch;
	}

	public void setLastBatch(Integer lastBatch) {
		this.lastBatch = lastBatch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPullDate() {
		return pullDate;
	}

	public void setPullDate(String pullDate) {
		this.pullDate = pullDate;
	}

	public Integer getHasTag() {
		return hasTag;
	}

	public void setHasTag(Integer hasTag) {
		this.hasTag = hasTag;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public Integer getMoved() {
		return moved;
	}

	public void setMoved(Integer moved) {
		this.moved = moved;
	}

	public Integer getDropped(){
		return dropped;
	}

	public void setDropped(Integer dropped) {
		this.dropped = dropped;
	}

	public Integer getNewtop(){
		return newtop;
	}

	public void setNewtop(Integer newtop) {
		this.newtop = newtop;
	}

	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public String getItunesAppId() {
		return itunesAppId;
	}

	public void setItunesAppId(String itunesAppId) {
		this.itunesAppId = itunesAppId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyItunesLink() {
		return companyItunesLink;
	}

	public void setCompanyItunesLink(String companyItunesLink) {
		this.companyItunesLink = companyItunesLink;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Integer getItunesFeaturedApp() {
		return itunesFeaturedApp;
	}

	public void setItunesFeaturedApp(Integer itunesFeaturedApp) {
		this.itunesFeaturedApp = itunesFeaturedApp;
	}

	public Integer getAlreadyTargeted() {
		return alreadyTargeted;
	}

	public void setAlreadyTargeted(Integer alreadyTargeted) {
		this.alreadyTargeted = alreadyTargeted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alreadyTargeted == null) ? 0 : alreadyTargeted.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((bundleId == null) ? 0 : bundleId.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((companyItunesLink == null) ? 0 : companyItunesLink.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((dropped == null) ? 0 : dropped.hashCode());
		result = prime * result + ((hasTag == null) ? 0 : hasTag.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((itunesAppId == null) ? 0 : itunesAppId.hashCode());
		result = prime * result + ((itunesFeaturedApp == null) ? 0 : itunesFeaturedApp.hashCode());
		result = prime * result + ((lastBatch == null) ? 0 : lastBatch.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((modifiedOn == null) ? 0 : modifiedOn.hashCode());
		result = prime * result + ((moved == null) ? 0 : moved.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((newtop == null) ? 0 : newtop.hashCode());
		result = prime * result + ((priceType == null) ? 0 : priceType.hashCode());
		result = prime * result + ((pullDate == null) ? 0 : pullDate.hashCode());
		result = prime * result + ((ranking == null) ? 0 : ranking.hashCode());
		result = prime * result + ((releaseDate == null) ? 0 : releaseDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItunesApplication other = (ItunesApplication) obj;
		if (alreadyTargeted == null) {
			if (other.alreadyTargeted != null)
				return false;
		} else if (!alreadyTargeted.equals(other.alreadyTargeted))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (bundleId == null) {
			if (other.bundleId != null)
				return false;
		} else if (!bundleId.equals(other.bundleId))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (companyItunesLink == null) {
			if (other.companyItunesLink != null)
				return false;
		} else if (!companyItunesLink.equals(other.companyItunesLink))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (dropped == null) {
			if (other.dropped != null)
				return false;
		} else if (!dropped.equals(other.dropped))
			return false;
		if (hasTag == null) {
			if (other.hasTag != null)
				return false;
		} else if (!hasTag.equals(other.hasTag))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (itunesAppId == null) {
			if (other.itunesAppId != null)
				return false;
		} else if (!itunesAppId.equals(other.itunesAppId))
			return false;
		if (itunesFeaturedApp == null) {
			if (other.itunesFeaturedApp != null)
				return false;
		} else if (!itunesFeaturedApp.equals(other.itunesFeaturedApp))
			return false;
		if (lastBatch == null) {
			if (other.lastBatch != null)
				return false;
		} else if (!lastBatch.equals(other.lastBatch))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (modifiedOn == null) {
			if (other.modifiedOn != null)
				return false;
		} else if (!modifiedOn.equals(other.modifiedOn))
			return false;
		if (moved == null) {
			if (other.moved != null)
				return false;
		} else if (!moved.equals(other.moved))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (newtop == null) {
			if (other.newtop != null)
				return false;
		} else if (!newtop.equals(other.newtop))
			return false;
		if (priceType == null) {
			if (other.priceType != null)
				return false;
		} else if (!priceType.equals(other.priceType))
			return false;
		if (pullDate == null) {
			if (other.pullDate != null)
				return false;
		} else if (!pullDate.equals(other.pullDate))
			return false;
		if (ranking == null) {
			if (other.ranking != null)
				return false;
		} else if (!ranking.equals(other.ranking))
			return false;
		if (releaseDate == null) {
			if (other.releaseDate != null)
				return false;
		} else if (!releaseDate.equals(other.releaseDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItunesApplication [id=" + id + ", name=" + name + ", pullDate=" + pullDate + ", ranking=" + ranking + ", moved=" + moved + ", newtop=" + newtop + ", dropped=" + dropped + ", lastBatch=" + lastBatch + ", hasTag=" + hasTag + ", bundleId=" + bundleId + ", itunesAppId=" + itunesAppId + ", category=" + category + ", companyName=" + companyName + ", companyItunesLink=" + companyItunesLink + ", priceType=" + priceType + ", amount=" + amount + ", currency=" + currency + ", link=" + link + ", image=" + image + ", releaseDate=" + releaseDate + ", itunesFeaturedApp=" + itunesFeaturedApp + ", alreadyTargeted=" + alreadyTargeted + ", modifiedOn=" + modifiedOn + ", createdOn=" + createdOn + "]";
	}

}
