package com.icsm.service;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@WebService
@Path("/paid/")
public interface IItunesPaidRssReportService {
	
	@GET
	@Path("/report")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportPaidAppByCompanyName(@QueryParam("name") String name, @QueryParam("days") Integer days);

	@GET
	@Path("/campaignapps")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportPaidForCampaigns();
	
	@GET
	@Path("/campaigns/bydate")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportPaidForCampaignsByDate(@QueryParam("startDay") String startDay, @QueryParam("decrement") int decrement);
	
	/*
	 * New is a 12 hour check
	 * 
	 */ 
	@GET
	@Path("/new")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportPaidNewApps(@QueryParam("date") String date, @QueryParam("fallInNumber") int limit, @QueryParam("hours") int hours);

	@GET
	@Path("/hot")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportPaidHotApps(@QueryParam("date") String date, @QueryParam("moved") int moved, @QueryParam("hours") int hours);
	
	@GET
	@Path("/nothot")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportPaidNotHot(@QueryParam("date") String date, @QueryParam("moved") int dropped, @QueryParam("hours") int hours);

	@GET
	@Path("/old")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportPaidOldApps(@QueryParam("date") String date, @QueryParam("dropOutNumber") int limit, @QueryParam("hours") int hours);

	@GET
	@Path("/top300")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportPaidPriorTop300Apps(@QueryParam("hours") int hours);
	
	@GET
	@Path("/wasfeatured")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportForPaidPastFeaturedApps(@QueryParam("hours") Integer hours);
	
	@GET
	@Path("/byId")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String getPaidAppByItunesAppId(@QueryParam("appId") String appId, @QueryParam("hours") int startHours, @QueryParam("lookBackHours") int lookBackHours);

	@GET
	@Path("/itunesFeatured")
	@Produces({"text/plain"}) //, "application/json", "application/xml"})
	public String reportNewlyReleasedAppsDoingWell();
}
