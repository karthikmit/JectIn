package com.jectin.db;

import com.mongodb.DBObject;

public interface IMongoPersistable {

	public DBObject getDBObject();

}