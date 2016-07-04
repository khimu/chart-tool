package com.icsm.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.icsm.service.impl.ItunesRssFeedService;

@Controller
public class MainController {
	
	Logger log = Logger.getLogger(MainController.class);
	
	protected static final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private ItunesRssFeedService itunesRssFeedService;
	
	@RequestMapping(value = "rssfeeds/{type}", method = RequestMethod.GET)
	public void rssfeeds(@PathVariable("type") String type, HttpServletResponse resp) {
		output(itunesRssFeedService.fetchFreeRssFeed(type), resp);
	}
	
	public void output(String message, HttpServletResponse resp){
		OutputStream out = null;
		try {
			out = resp.getOutputStream();
			resp.setContentType("application/json");
			
			ObjectNode node = objectMapper.createObjectNode();
			node.put("result", message);
			
			//JsonNode node = objectMapper.readTree(json);
			
			out.write(node.toString().getBytes());

			resp.setContentLength(node.toString().length());
			resp.setHeader("Cache-Control","no-cache");
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}
	}
}
