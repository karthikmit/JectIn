package com.jectin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jectin.service.UrlMapper;

@Controller
@RequestMapping(value="/in")
public class ShortenController {

	@RequestMapping(value="", method=RequestMethod.POST)
	public @ResponseBody String cryptUrlAnother(@RequestParam(value = "FullUrl", required = true)String fullUrl, HttpServletRequest req) {
		String cryptUrl = UrlMapper.getUrlMapper().mapUrl(fullUrl);
		String servername = req.getServerName();
		System.out.println(servername);
		int localPort = req.getLocalPort();
		System.out.println(localPort);
		
		String urlserverpart = "";
		if(servername.equalsIgnoreCase("localhost")) {
			urlserverpart = servername + ":" + localPort;
		}
		else {
			urlserverpart = "ject.in";
		}
		System.out.println(urlserverpart);
		String encodedUrl = urlserverpart + "/" + cryptUrl;
		return encodedUrl;
	}
	
	@RequestMapping("/view")
	public String showShortenPage() {
		return "shortenurl";
	}
}
