package com.jectin.db;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class DBProxy {

	private String hostname;
	private int port;
	private DB dbInst;
	private static DBProxy dbProxy = null;

    private DBProxy(String host, int port, String dbName) throws UnknownHostException, MongoException{
		this.hostname = host;
		this.port = port;
		connect(dbName);
	}
	
	public static DBProxy getDBProxy() {
		if(dbProxy == null) {
			synchronized (DBProxy.class) {
				if(dbProxy == null) {
					try {
						
						dbProxy = new DBProxy(ApplicationProperties.HOST, ApplicationProperties.PORT, ApplicationProperties.MONGO_DB_NAME);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MongoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dbProxy.authenticate(ApplicationProperties.USER_NAME, ApplicationProperties.PASSWORD);
				}
			}
		}
		
		return dbProxy;
	}
	
	private void connect(String dbName) throws UnknownHostException, MongoException {
		
		Mongo instance = new Mongo(this.hostname, this.port);
		
		dbInst = instance.getDB(dbName);
	}
	
	public void authenticate(String username, String password) {
		
		if(!dbInst.authenticate(username, password.toCharArray())) {
			System.out.println("Auth failed");
		}
	}
	public void persist(IMongoPersistable mpObj, String collectionName) {
		DBCollection collection = dbInst.getCollection(collectionName);
		
		collection.save(mpObj.getDBObject());
	}
	
	public void persist(DBObject dbObj, String collectionName) {
		DBCollection collection = dbInst.getCollection(collectionName);
		
		collection.save(dbObj);
	}
	
	public DBObject getLastElement(String collectionName) {
		DBCollection collection = dbInst.getCollection(collectionName);
		DBObject sortBy = new BasicDBObject();
		sortBy.put("_id", -1);
		DBCursor cursor = collection.find().sort(sortBy);
		if(cursor.hasNext()) {
			return cursor.next();
		}
		
		return null;
	}
	
	public DBObject queryOne(DBObject query, String collectionName) {
		DBCollection collection = dbInst.getCollection(collectionName);
		DBCursor cursor = collection.find(query);
		DBObject object = null;

        try {
            while(cursor.hasNext()) {
                object = cursor.next();
            }
        } finally {
            cursor.close();
        }
        
        return object;
	}
}

