package com.jectin.controller;

import java.io.IOException;
import java.util.Map;

import com.jectin.db.DBProxy;
import com.jectin.service.UserDetailsStore;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/safestore")
public class SafeStoreController {

	@RequestMapping("/view")
	public String safeStoreView() {
		return "safestore";
	}
	
	@RequestMapping(value="/store", method=RequestMethod.POST)
	public @ResponseBody String safeStoreValues(@RequestParam(value = "jsonMap", required = true) String inputMap,
                                                @RequestParam(value = "sessionId", required = true) String sessionGuid)
				throws JsonParseException, JsonMappingException, IOException {
		System.out.println(inputMap);

        if(sessionGuid == null) {
            //TODO Return login screen to the user.

            return "NOTLOGGEDIN";
        }

        String emailId = getMailId(sessionGuid);
        System.out.println(emailId);

        if(emailId == null) {
            //TODO Return login screen to the user.

            return "LOGGEDOUT";
        }

        DBObject safeObject = parseSafeJsonInfo(getMailId(sessionGuid), inputMap);
        DBProxy.getDBProxy().persist(safeObject, "safestore");
        return "SUCCESS";
	}

    private String getMailId(String sessionGuid) {
        return UserDetailsStore.getUserDetailsStore().getEmailIdFor(sessionGuid);
    }

    private DBObject parseSafeJsonInfo(String emailId, String inputMap) throws IOException {
        DBObject dbObject = new BasicDBObject().append("_id", emailId);
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> keyValMap = mapper.readValue(inputMap, Map.class);

        for (String key : keyValMap.keySet()) {
            dbObject.put(key, keyValMap.get(key));
        }

        return dbObject;
    }
}
