package com.icsmobile.faadplatform.dao.campaign;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.icsmobile.faadplatform.dao.ItunesApplication;

public interface IItunesApplicationCommonDao {

	public List<ItunesApplication> getPaidLastBatch();

	public List<ItunesApplication> getFreeLastBatch();
	
	public void batchUpdateLastBatch(final List<ItunesApplication> data) throws Exception;
	
	public void batchSave(final List<ItunesApplication> data) throws Exception;
	
	public List<ItunesApplication> getAppById(String appId, int startHours, int lookBackHours);
}
