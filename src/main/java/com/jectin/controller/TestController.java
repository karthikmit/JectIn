package com.jectin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/echo")
public class TestController {

	@RequestMapping(value="/", method=RequestMethod.POST)
	public @ResponseBody String sayHelloPost(@RequestParam(value = "Name", required = true) String name) {
		return "Hello " + name;
	}
	
	@RequestMapping(value="/{name}")
	public @ResponseBody String sayHello(@PathVariable(value="name") String name) {
		return "Hello " + name;
	}
	

}
