package com.icsm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.icsm.model.CampaignReport;
import com.icsm.model.FeaturedAppReport;
import com.icsmobile.faadplatform.dao.ItunesApplication;

public abstract class BaseService {
	
	private final Log log = LogFactory.getLog(getClass());
	
	protected final static ObjectMapper objectMapper = new ObjectMapper();


	protected ArrayNode pupulateReportNode(List<FeaturedAppReport> apps) {
		SimpleDateFormat itunesDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat featuredDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

		DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();

		if (apps != null) {
			ArrayNode node = objectMapper.createArrayNode();

			for (FeaturedAppReport report : apps) {
				ObjectNode item = objectMapper.createObjectNode();
				item.put("name", report.getItunesApp().getName());
				item.put("appid", report.getItunesApp().getItunesAppId());
				try {
					DateTime timestamp = parser2.parseDateTime(report.getItunesApp().getPullDate());
					Date newtimestamp = format.parse(timestamp.toDate().toString());
					item.put("timestamp", itunesDateFormat.format(newtimestamp));
				} catch (ParseException e) {
					log.error("unable to parse timestamp");
					item.put("timestamp", report.getItunesApp().getPullDate());
				}
				item.put("moved", report.getItunesApp().getMoved());
				item.put("rank", report.getItunesApp().getRanking());
				item.put("newtop", report.getItunesApp().getNewtop() == 1 ? true : false);
				item.put("dropped", report.getItunesApp().getDropped() == 1 ? true : false);
				item.put("image", report.getItunesApp().getImage());
				item.put("link", report.getItunesApp().getLink());
				item.put("hastag", report.getItunesApp().getHasTag());
				try {
					DateTime releasedate = parser2.parseDateTime(report.getItunesApp().getReleaseDate());
					Date newreleasedate = format.parse(releasedate.toDate().toString());
					item.put("releasedate", itunesDateFormat.format(newreleasedate));
				} catch (ParseException e) {
					log.error("unable to parse release date");
					item.put("releasedate", report.getItunesApp().getReleaseDate());
				}
				item.put("category", report.getItunesApp().getCategory());

				item.put("companyname", report.getItunesApp().getCompanyName());
				
				item.put("start_date", featuredDateFormat.format(report.getFeaturedApp().getStartDate())); 
				item.put("end_date", featuredDateFormat.format(report.getFeaturedApp().getEndDate()));

				node.add(item);
			}

			return node;
		}
		return objectMapper.createArrayNode();
	}
	
	protected ArrayNode populateArrayNode(List<CampaignReport> apps, int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		SimpleDateFormat itunesDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

		DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();

		if (apps != null) {
			ArrayNode node = objectMapper.createArrayNode();

			for (CampaignReport report : apps) {
				ObjectNode item = objectMapper.createObjectNode();
				item.put("name", report.getStats().getName());
				item.put("appid", report.getStats().getItunesAppId());
				try {
					DateTime timestamp = parser2.parseDateTime(report.getStats().getPullDate());
					Date newtimestamp = format.parse(timestamp.toDate().toString());
					item.put("timestamp", itunesDateFormat.format(newtimestamp));
				} catch (ParseException e) {
					log.error("unable to parse timestamp");
					item.put("timestamp", report.getStats().getPullDate());
				}
				item.put("moved", report.getStats().getMoved());
				item.put("rank", report.getStats().getRanking());
				item.put("newtop", report.getStats().getNewtop() == 1 ? true : false);
				item.put("dropped", report.getStats().getDropped() == 1 ? true : false);
				item.put("image", report.getStats().getImage());
				item.put("link", report.getStats().getLink());
				item.put("hastag", report.getStats().getHasTag());
				try {
					DateTime releasedate = parser2.parseDateTime(report.getStats().getReleaseDate());
					Date newreleasedate = format.parse(releasedate.toDate().toString());
					item.put("releasedate", itunesDateFormat.format(newreleasedate));
				} catch (ParseException e) {
					log.error("unable to parse release date");
					item.put("releasedate", report.getStats().getReleaseDate());
				}
				item.put("category", report.getStats().getCategory());

				item.put("companyname", report.getStats().getCompanyName());

				item.put("description", report.getCampaign().getDescription());
				item.put("startdate", sdf.format(report.getCampaign().getStartDate()));
				item.put("enddate", sdf.format(report.getCampaign().getEndDate()));

				node.add(item);
			}

			return node;
		}
		return objectMapper.createArrayNode();
	}
	
	protected ArrayNode pupulateNode(List<ItunesApplication> apps) {
		SimpleDateFormat itunesDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

		DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();

		if (apps != null) {
			ArrayNode node = objectMapper.createArrayNode();

			for (ItunesApplication report : apps) {
				ObjectNode item = objectMapper.createObjectNode();
				item.put("name", report.getName());
				item.put("appid", report.getItunesAppId());
				try {
					DateTime timestamp = parser2.parseDateTime(report.getPullDate());
					Date newtimestamp = format.parse(timestamp.toDate().toString());
					item.put("timestamp", itunesDateFormat.format(newtimestamp));
				} catch (ParseException e) {
					log.error("unable to parse timestamp");
					item.put("timestamp", report.getPullDate());
				}
				item.put("moved", report.getMoved());
				item.put("rank", report.getRanking());
				item.put("newtop", report.getNewtop() == 1 ? true : false);
				item.put("dropped", report.getDropped() == 1 ? true : false);
				item.put("image", report.getImage());
				item.put("link", report.getLink());
				item.put("hastag", report.getHasTag());
				try {
					DateTime releasedate = parser2.parseDateTime(report.getReleaseDate());
					Date newreleasedate = format.parse(releasedate.toDate().toString());
					item.put("releasedate", itunesDateFormat.format(newreleasedate));
				} catch (ParseException e) {
					log.error("unable to parse release date");
					item.put("releasedate", report.getReleaseDate());
				}
				item.put("category", report.getCategory());

				item.put("companyname", report.getCompanyName());
				
				item.put("itunesfeaturedapp", report.getItunesFeaturedApp());
				
				item.put("alreadytargeted", report.getAlreadyTargeted());

				node.add(item);
			}

			return node;
		}
		return objectMapper.createArrayNode();
	}

}
