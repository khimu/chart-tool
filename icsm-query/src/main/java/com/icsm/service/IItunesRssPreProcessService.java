package com.icsm.service;


public interface IItunesRssPreProcessService {


	public String preProcessFree();

	public String preProcessPaid();

	public String preProcessOldPaid(int limit);

	public String preProcessOldFree(int limit);

}
