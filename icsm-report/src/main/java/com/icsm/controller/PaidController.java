package com.icsm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PaidController {
	
	@RequestMapping(value="/paiddetails", method=RequestMethod.GET)
	public ModelAndView details(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false, value = "appId") String id, @RequestParam(required = false, value = "hours") Integer hours, @RequestParam(required = false, value = "lookBackHours") Integer lookBackHours){
		Map<String, String> map = new HashMap<String, String>();
		map.put("appId", id);
		map.put("hours", hours + "");
		map.put("lookBackHours", lookBackHours + "");

		return new ModelAndView("paid/details", map);
	}
	
	
	@RequestMapping(value="/campaign_report_paid", method=RequestMethod.GET)
	public ModelAndView report(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false, value = "hours") Integer hours){
		Map<String, String> map = new HashMap<String, String>();
		map.put("hours", hours + "");

		return new ModelAndView("paid/campaign_report_paid", map);
	}
}
