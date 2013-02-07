package com.jectin.controller;

import java.io.IOException;
import java.util.*;

import com.jectin.service.MailSendHandler;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jectin.service.PasswordNotMatchException;
import com.jectin.service.UserDetailsStore;
import com.jectin.service.UserNotExistsException;

@Controller
@RequestMapping(value="/users")
public class UsersController {
	
	Map<String, String> guidEmailMap = new HashMap<String, String>();
	private UserDetailsStore userDetailsStore = UserDetailsStore.getUserDetailsStore();
 	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLoginPage() {
		return "loginpage";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String showSignupPage() {
		return "signup";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST) 
	public @ResponseBody String signup(@RequestParam(value = "UserName", required = true) String username, 
			@RequestParam(value = "PassWord", required = true) String password, 
			@RequestParam(value = "Name", required = true) String name) throws IOException {

        Map<String, String> respMap = new HashMap<String, String>();
        ObjectMapper mapper = new ObjectMapper();

        username = username.toLowerCase();
		String response = "";
		System.out.println(username + " " + password + " " + name);
		
		try {
			userDetailsStore.getFriendlyName(username, password);
		} catch (PasswordNotMatchException e) {
			response = "FAILUSER";

		} catch (UserNotExistsException e) {
			
			// This is success for Signup,
			
			String activCode = userDetailsStore.processNewSignup(username, password, name);
            MailSendHandler.getMailSendHandler().sendHelloMailTo(name, username, activCode);
			response = "SUCCESS";
		}

        respMap.put("respCode", response);
		return mapper.writeValueAsString(respMap);
	}

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public String activateShow() {
        return "activate";
    }

    @RequestMapping(value="/activate", method=RequestMethod.POST)
    public @ResponseBody String activate(@RequestParam(value = "ActivationCode", required = true) String activcode) {
        if(userDetailsStore.activate(activcode)) {
            return "Successfully activated";
        }
        else {
            return "Not activated, try signing up again";
        }
    }
	
	@RequestMapping(value="/login", method=RequestMethod.POST) 
	public @ResponseBody String login(@RequestParam(value = "UserName", required = true) String username, 
			@RequestParam(value = "PassWord", required = true) String password){
		
		String friendlyName = "";
        String sessionGuid = "";
        String responseJsonString = "";

        username = username.toLowerCase();
		
		try {
			friendlyName = userDetailsStore.getFriendlyName(username, password);
            sessionGuid = createNewSessionGuid();
            userDetailsStore.setSessionFor(username, sessionGuid);
		} catch (PasswordNotMatchException e) {
			friendlyName = "FAILPWD";
		} catch (UserNotExistsException e) {
			friendlyName = "FAILUSER";
		}

        ObjectMapper mapper = new ObjectMapper();;
        Map<String, String> responseMap = new HashMap<String, String>();

        responseMap.put("fname", friendlyName);
        responseMap.put("sessionguid", sessionGuid);

        try {
            responseJsonString = mapper.writeValueAsString(responseMap);
            System.out.println("Response String for login: " + responseJsonString);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return responseJsonString;
	}

    private String createNewSessionGuid() {

        return UUID.randomUUID().toString();
    }

}
