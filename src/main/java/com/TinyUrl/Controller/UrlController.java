package com.TinyUrl.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HttpServletBean;

import com.TinyUrl.Models.Range;
import com.TinyUrl.Models.UrlMapping;
import com.TinyUrl.Services.UrlGeneratorService;
import com.TinyUrl.Services.UrlMappingService;
import com.datastax.driver.core.utils.UUIDs;


@RestController
public class UrlController {
	
	@Autowired
	private UrlGeneratorService urlGeneratorService;
	@Autowired
	private UrlMappingService urlMappingService;
	
	@RequestMapping(value="/url/generate", method=RequestMethod.POST)
	@ResponseBody
	public void generateURL(@RequestBody final Map<String,String> map,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException
	{
		URL url;
		HttpURLConnection huc;
		String sOldUrl = map.get("src");
		try {
			 
			url = new URL(sOldUrl);
			huc = (HttpURLConnection) url.openConnection();
			huc.setRequestMethod("HEAD");
			int responseCode = huc.getResponseCode();
			if(responseCode == 404) response.sendError(404);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendError(404);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendError(404);
		}
		UrlMapping urlMap = urlMappingService.findByOldUrl(sOldUrl);
		HttpSession session = request.getSession();
		SecurityContext sc = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication auth = sc.getAuthentication();
	
		JSONObject data = new JSONObject();
    	data.put("statusCode", 200);
    	
		
		if( urlMap != null)  data.put("output", urlMap.getNewUrl()); 
		
		urlMap = new UrlMapping(urlGeneratorService.getNewURL(sOldUrl), sOldUrl, (String)auth.getPrincipal());
		data.put("output", "localhost:8080/urls/"+urlMappingService.save(urlMap).getNewUrl());
		String sJson = data.toString();
    	PrintWriter writer = response.getWriter();
    	writer.write(sJson);
    	writer.flush();
    	writer.close();
	}
	
	@RequestMapping(value="/urls/{tinyUrl}", method=RequestMethod.GET)
	public void getOriginalUrl(@PathVariable(value="tinyUrl") String sTinyUrl, HttpServletResponse response) throws IOException
	{
		try {
			UrlMapping urlMap = urlMappingService.getByNewUrl(sTinyUrl);
			if(urlMap == null || urlMap.getOldUrl() == null || urlMap.getOldUrl().isEmpty())
			{	
				response.sendError(404);
			}
			else	
				response.sendRedirect(urlMap.getOldUrl());
		} catch (IOException e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}
}