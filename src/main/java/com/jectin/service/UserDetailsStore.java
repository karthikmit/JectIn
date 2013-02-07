package com.jectin.service;

import com.jectin.db.DBProxy;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserDetailsStore {

	private DBProxy dbProxy = null;
	private static final String PASSWORD = "password";
	private static final String MONGO_PRIMARY_KEY_ID = "_id";
	private static final String MONGO_COLLECTION_NAME = "IndexedUsersLoginDetails";
	private static final String FRIENDLY_NAME = "name";

    private Map<String, String> sessionIdEmailIdMap = new HashMap<String, String>();
    private HashMap<String,DBObject> activCodeMap = new HashMap<String, DBObject>();


    private UserDetailsStore() {
		dbProxy  = DBProxy.getDBProxy();	
	}

    private static UserDetailsStore userDetailsStore = null;

    public static UserDetailsStore getUserDetailsStore() {
        if(userDetailsStore == null) {
            userDetailsStore = new UserDetailsStore();
        }
        return  userDetailsStore;
    }

    public void setSessionFor(String emailId, String sessionId) {
        sessionIdEmailIdMap.put(sessionId, emailId);
    }

    public String getEmailIdFor(String sessionId) {
        return sessionIdEmailIdMap.get(sessionId);
    }

    // This would be called during login.
	public String getFriendlyName(String username, String password) 
			throws PasswordNotMatchException, UserNotExistsException {
		
		System.out.println(username + " " + password);
		
		String mappedUserName = MapEMailIDForMongo(username.trim());
		
		DBObject outObj = dbProxy.queryOne(new BasicDBObject(MONGO_PRIMARY_KEY_ID, mappedUserName), MONGO_COLLECTION_NAME);
		
		if(outObj == null) {
			throw new UserNotExistsException("User, " + username + " isn't present in DB");
		}
		
		String dbUserName = ((String)outObj.get(MONGO_PRIMARY_KEY_ID)).trim();
		if(!dbUserName.equals(mappedUserName)) {
			System.out.println(dbUserName + " " + mappedUserName);
			throw new UserNotExistsException("User, " + username + " isn't present in DB");
		}
		
		System.out.println(outObj);
		
		String passwordDB = (String) outObj.get(PASSWORD);

		if(!passwordDB.equals(password)) {
			
			throw new PasswordNotMatchException("Given password isn't matching with DB");
		}
		
		return (String) outObj.get(FRIENDLY_NAME);
	}
	
	public String processNewSignup(String username, String password, String friendlyName) {


        System.out.println(username + " " + password + " " + friendlyName);

        DBObject dbObj = new BasicDBObject();
        dbObj.put(MONGO_PRIMARY_KEY_ID, MapEMailIDForMongo(username.trim()));
        dbObj.put(PASSWORD , password.trim());
        dbObj.put(FRIENDLY_NAME, friendlyName.trim());

        String activCode = UUID.randomUUID().toString();

        activCodeMap.put(activCode, dbObj);
        return activCode;
    }

    public boolean activate(String activCode) {

        if(activCodeMap.containsKey(activCode)) {
            DBObject dbObject = activCodeMap.get(activCode);
            persistInDB(dbObject);
            return true;
        }
        return false;
    }

    private void persistInDB(DBObject dbObj) {
        dbProxy.persist(dbObj, MONGO_COLLECTION_NAME);
    }

    private String MapEMailIDForMongo(String username) {
		
		return username;
	}
}
