package com.icsm.service;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@WebService
@Path("/")
public interface IItunesRssReportService {
	
	@GET
	@Path("/itunes/report")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportAppByCompanyName(@QueryParam("name") String name, @QueryParam("days") Integer days);

	@GET
	@Path("/itunes/campaignapps")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportForCampaigns();
	
	@GET
	@Path("/itunes/campaigns/bydate")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportForCampaignsByDate(@QueryParam("startDay") String startDay, @QueryParam("decrement") int decrement);
	
	/*
	 * New is a 12 hour check
	 * 
	 */ 
	@GET
	@Path("/itunes/getNew")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportNewApps(@QueryParam("date") String date, @QueryParam("fallInNumber") int limit, @QueryParam("hours") int hours);

	@GET
	@Path("/itunes/getHotApps")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportHotApps(@QueryParam("date") String date, @QueryParam("moved") int moved, @QueryParam("hours") int hours);
	
	@GET
	@Path("/itunes/getNotHotApps")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportNotHot(@QueryParam("date") String date, @QueryParam("moved") int dropped, @QueryParam("hours") int hours);

	@GET
	@Path("/itunes/getOldApps")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportOldApps(@QueryParam("date") String date, @QueryParam("dropOutNumber") int limit, @QueryParam("hours") int hours);

	@GET
	@Path("/itunes/top300ByHour")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportPriorTop300Apps(@QueryParam("hours") int hours);

	@GET
	@Path("/itunes/rewardsCampaignApps")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportForRewardsCampaignApps(@QueryParam("hours") int hours);

	@GET
	@Path("/itunes/appIds")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String getAppIds();

	@GET
	@Path("/itunes/byId")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String getFreeAppByItunesAppId(@QueryParam("appId") String appId, @QueryParam("hours") int startHours, @QueryParam("lookBackHours") int lookBackHours);

	@GET
	@Path("/itunes/commonById")
	@Produces({"text/plain"})
	public String getAppByItunesAppId(@QueryParam("appId") String appId, @QueryParam("hours") int startHours, @QueryParam("lookBackHours") int lookBackHours);
	
}
