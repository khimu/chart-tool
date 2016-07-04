package com.icsm.service;

import java.util.List;

import com.icsmobile.faadplatform.dao.ItunesApplication;


public interface ItunesApplicationCommonService {

	public void batchUpdate(List<ItunesApplication> data) throws Exception;
	
	public List<ItunesApplication> getPaidLastBatch();
	
	public List<ItunesApplication> getFreeLastBatch();
	
	public void batchSave(List<ItunesApplication> data) throws Exception;
}
