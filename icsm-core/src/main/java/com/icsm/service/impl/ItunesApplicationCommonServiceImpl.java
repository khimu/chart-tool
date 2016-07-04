package com.icsm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icsm.service.ItunesApplicationCommonService;
import com.icsmobile.faadplatform.dao.ItunesApplication;
import com.icsmobile.faadplatform.dao.campaign.IItunesApplicationCommonDao;

@Component("itunesApplicationCommonService")
public class ItunesApplicationCommonServiceImpl implements ItunesApplicationCommonService {

	@Autowired
	private IItunesApplicationCommonDao itunesApplicationCommonDao;
	
	public List<ItunesApplication> getPaidLastBatch(){
		return itunesApplicationCommonDao.getPaidLastBatch();
	}
	
	public List<ItunesApplication> getFreeLastBatch(){
		return itunesApplicationCommonDao.getFreeLastBatch();
	}
	
	public void batchUpdate(List<ItunesApplication> data) throws Exception {
		itunesApplicationCommonDao.batchUpdateLastBatch(data);
	}
	
	public void batchSave(List<ItunesApplication> data) throws Exception {
		itunesApplicationCommonDao.batchSave(data);
	}
}
