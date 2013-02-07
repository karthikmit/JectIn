package com.jectin.service;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jectin.db.DBProxy;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

public class UrlMapper {

	private static final String FULL_URL = "fullurl";
	private static final String MONGO_PRIMARY_KEY_ID = "_id";
	private static final String MONGO_COLLECTION_NAME = "IndexedUrlMap";
	private static UrlMapper urlMapperInstance = null;
	private Map<String, String> urlmap = new HashMap<String, String>();
	private DBProxy dbProxy;
	private Cache<String, String> cache = null;
	private BasicKeyGenerator basicKeyGenerator = new BasicKeyGenerator(new Date().getTime());

	private UrlMapper() throws UnknownHostException, MongoException {
		
		//TODO Make it configurable
		dbProxy = DBProxy.getDBProxy();
		cache = CacheBuilder.newBuilder().maximumSize(4).build();
	}

	public static UrlMapper getUrlMapper() {
		if(urlMapperInstance  == null) {
			try {
				urlMapperInstance = new UrlMapper();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MongoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return urlMapperInstance;
	}
	
	public String getMappedUrl(String inCryptUrl) {
		System.out.println(inCryptUrl);
		
		DBObject outObj = dbProxy.queryOne(new BasicDBObject(MONGO_PRIMARY_KEY_ID, inCryptUrl.trim()), MONGO_COLLECTION_NAME);
		System.out.println(outObj);
		String fullurl = (String) outObj.get(FULL_URL);
		
		return fullurl;
	}

	public String mapUrl(String fullurl) {
		fullurl = fullurl.trim();
		
		if(cache.getIfPresent(fullurl) != null) {
			return cache.getIfPresent(fullurl);
		}
	
		String newIdStr = basicKeyGenerator.getNextKey(6);
		urlmap.put(newIdStr, fullurl);
		
		// Put it to Mongo also.
		DBObject dbObj = new BasicDBObject();
		dbObj.put(MONGO_PRIMARY_KEY_ID, newIdStr);
		dbObj.put(FULL_URL, fullurl);
		
		cache.put(fullurl, newIdStr);
		dbProxy.persist(dbObj, MONGO_COLLECTION_NAME);
		
		return newIdStr;
	}
}
