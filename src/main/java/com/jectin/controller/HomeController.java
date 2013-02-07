package com.jectin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jectin.form.UrlForm;
import com.jectin.service.UrlMapper;

@Controller
@RequestMapping(value="/")
public class HomeController {

	@RequestMapping(value="")
	public ModelAndView home(HttpServletResponse response) throws IOException{
		ModelMap map = new ModelMap();
		
		UrlForm form = new UrlForm();
		map.put("urlform", form);
		return new ModelAndView("home", map);
	}
	
	@RequestMapping("favicon.ico")
    public String favIconForward(){
        return "forward:resources/inject.png";
    }
	
	@RequestMapping(value="/{crypturl}")
	public String decryptUrl(@PathVariable(value="crypturl") String crypturl, ModelMap map) {
		if(crypturl == null || crypturl.length() == 0)
			return "home";
		
		System.out.println("Decrypt starts");
		
		String redirectUrl = getMappedUrl(crypturl);
		
		if(redirectUrl == null) {
			return "error";
		}
		else{
			return "redirect:" + redirectUrl;
		}
	}
	
	private String getMappedUrl(String crypturl) {
		return UrlMapper.getUrlMapper().getMappedUrl(crypturl);
	}
	
}
