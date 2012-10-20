package com;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class MongoOne {

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException {
		Mongo mg = new Mongo("10.20.53.21",27017);

		//查询所有的Database
        for (String name : mg.getDatabaseNames()) {
            System.out.println("dbName: " + name);
        }
        
        DB dbTest = mg.getDB("test");
        //查询所有的聚集集合
        for (String name : dbTest.getCollectionNames()) {
            System.out.println("test - collectionName: " + name);
        }
        
        DB dbJipeng = mg.getDB("jipeng");
        //查询所有的聚集集合
        for (String name : dbJipeng.getCollectionNames()) {
            System.out.println("jipeng - collectionName: " + name);
        }
        DBCollection users = dbJipeng.getCollection("userinfo");
        //查询所有的数据
        DBCursor cur = users.find();
        while (cur.hasNext()) {
            System.out.println(cur.next());
        }
        System.out.println(cur.count());
        System.out.println(cur.getCursorId());
        System.out.println(JSON.serialize(cur));
	}

}
