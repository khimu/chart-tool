/*
 * @(#)com.icsmobile.faadplatform.bo.campaign
 * 
 * Copyright 2011-2012 ICS Mobile, Inc. All rights reserved 
 *
 *
 * JDK version : 6.0
 * Version History : 1.0    Feb 28, 2011
 */
package com.icsmobile.faadplatform.dao;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;


/**
 * It represents the campaign business object 
 *
 * @author Athir Gillani
 */
public class CampaignBO extends BaseBO {

	private static final String CLASS_NAME = "CampaignBO";
	
	private static final long serialVersionUID = 6136342678336575024L;

	private int campaignId;
	
	private Integer applicationId;
	
	private Integer customerId;
	
	private String name;
	
	private String description;
	
	private String type;
	
	private Date startDate;
	
	private Date endDate;
	
	private Integer installCap;
	
	private Integer dailyInstallCap;
	
	private Integer price;
	
	private Integer discount;
	
	private Double estimatedBudget;
	
	private Integer weightage;
	
	private Boolean isActive;
	
	private Boolean isApproved;
	
	private Boolean isDeleted;
	
	private Integer creditReward;

	private Integer completions;
    
	private boolean selfPromotion;
	
	/**
	 * Default Constructor
	 */
	public CampaignBO() {
		//
	}
	
	/**
	 * @param campaignId
	 */
	public CampaignBO(int campaignId) {
		this.campaignId = campaignId;
	}

	/**
	 * Instantiates a new campaign bo.
	 *
	 * @param campaignId the campaign id
	 * @param customerId the customer id
	 * @param name the name
	 * @param description the description
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param installCap the install cap
	 * @param dailyInstallCap the daily install cap
	 * @param price the price
	 * @param discount the discount
	 * @param estimatedBudget the estimated budget
	 * @param weightage the weightage
	 * @param isActive the is active
	 * @param isApproved the is approved
	 * @param isDeleted the is deleted
	 * @param creditReward the credit reward
	 */
	public CampaignBO(int campaignId, Integer customerId, String name, String description, Date startDate, Date endDate,
			Integer installCap, Integer dailyInstallCap, Integer price, Integer discount, Double estimatedBudget, Integer weightage,
			Boolean isActive, Boolean isApproved, Boolean isDeleted, Integer creditReward) {
		this.campaignId = campaignId;
		this.customerId = customerId;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.installCap = installCap;
		this.dailyInstallCap = dailyInstallCap;
		this.price = price;
		this.discount = discount;
		this.estimatedBudget = estimatedBudget;
		this.weightage = weightage;
		this.isActive = isActive;
		this.isApproved = isApproved;
		this.isDeleted = isDeleted;
		this.creditReward = creditReward;
	}

	/**
	 * Instantiates a new campaign bo.
	 *
	 * @param campaignId the campaign id
	 * @param customerId the customer id
	 * @param name the name
	 * @param description the description
	 * @param type the type
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param installCap the install cap
	 * @param dailyInstallCap the daily install cap
	 * @param price the price
	 * @param discount the discount
	 * @param estimatedBudget the estimated budget
	 * @param weightage the weightage
	 * @param isActive the is active
	 * @param isApproved the is approved
	 * @param isDeleted the is deleted
	 * @param creditReward the credit reward
	 */
	public CampaignBO(int campaignId, Integer customerId, String name, String description,String type, Date startDate, Date endDate,
			Integer installCap, Integer dailyInstallCap, Integer price, Integer discount, Double estimatedBudget, Integer weightage,
			Boolean isActive, Boolean isApproved, Boolean isDeleted, Integer creditReward) {
		this.campaignId = campaignId;
		this.customerId = customerId;
		this.name = name;
		this.description = description;
		this.type = type;
		this.startDate = startDate;
		this.endDate = endDate;
		this.installCap = installCap;
		this.dailyInstallCap = dailyInstallCap;
		this.price = price;
		this.discount = discount;
		this.estimatedBudget = estimatedBudget;
		this.weightage = weightage;
		this.isActive = isActive;
		this.isApproved = isApproved;
		this.isDeleted = isDeleted;
		this.creditReward = creditReward;
	}

	/**
	 * @return campaignId
	 */
	public int getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignId
	 */
	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}

	/**
	 * @return applicationId
	 */
	public Integer getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId
	 */
	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return installCap
	 */
	public Integer getInstallCap() {
		return installCap;
	}

	/**
	 * @param installCap
	 */
	public void setInstallCap(Integer installCap) {
		this.installCap = installCap;
	}

	/**
	 * @return price
	 */
	public Integer getPrice() {
		return price;
	}

	/**
	 * @param price
	 */
	public void setPrice(Integer price) {
		this.price = price;
	}

	/**
	 * @return discount
	 */
	public Integer getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 */
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	/**
	 * @return estimatedBudget
	 */
	public Double getEstimatedBudget() {
		return estimatedBudget;
	}

	/**
	 * @param estimatedBudget
	 */
	public void setEstimatedBudget(Double estimatedBudget) {
		this.estimatedBudget = estimatedBudget;
	}

	/**
	 * @return weightage
	 */
	public Integer getWeightage() {
		return weightage;
	}

	/**
	 * @param weightage
	 */
	public void setWeightage(Integer weightage) {
		this.weightage = weightage;
	}

	/**
	 * @return isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return isApproved
	 */
	public Boolean getIsApproved() {
		return isApproved;
	}

	/**
	 * @param isApproved
	 */
	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	/**
	 * @return isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * Sets the customer id.
	 *
	 * @param customerId the new customer id
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * Gets the credit reward.
	 *
	 * @return the credit reward
	 */
	public Integer getCreditReward() {
		return creditReward;
	}

	/**
	 * Sets the credit reward.
	 *
	 * @param creditReward the new credit reward
	 */
	public void setCreditReward( Integer creditReward ) {
		this.creditReward = creditReward;
	}

	/**
	 * @return type
	 */
	public String getType( ) {
		return type;
	}

	/**
	 * @param type
	 */
	public void setType( String type ) {
		this.type = type;
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CampaignBO){
			CampaignBO campaignBO = (CampaignBO) obj;
			if ( this.campaignId == campaignBO.getCampaignId() ) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return String.valueOf(this.campaignId).hashCode();
	};

	/**
	 * @return completions
	 */
	public Integer getCompletions( ) {
		return completions;
	}

	/**
	 * @param completions
	 */
	public void setCompletions( Integer completions ) {
		this.completions = completions;
	}

	/**
	 * @param dailyInstallCap
	 */
	public void setDailyInstallCap( Integer dailyInstallCap ) {
		this.dailyInstallCap = dailyInstallCap;
	}

	/**
	 * @return dailyInstallCap
	 */
	public Integer getDailyInstallCap( ) {
		return dailyInstallCap;
	}

	/**
	 * @param selfPromotion the selfPromotion to set
	 */
	public void setSelfPromotion(boolean selfPromotion) {
		this.selfPromotion = selfPromotion;
	}

	/**
	 * @return the selfPromotion
	 */
	public boolean isSelfPromotion() {
		return selfPromotion;
	}
}
