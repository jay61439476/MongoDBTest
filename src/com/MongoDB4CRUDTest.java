package com;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MongoDB4CRUDTest {
    private Mongo mg = null;
    private DB db;
    private DBCollection users;
    
    @Before
    public void init() {
        try {
//            mg = new Mongo();
            mg = new Mongo("10.20.53.21",27017);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
        //获取temp DB；如果默认没有创建，mongodb会自动创建
        db = mg.getDB("jipeng");
        //获取users DBCollection；如果默认没有创建，mongodb会自动创建
        users = db.getCollection("userinfo");
    }
    
    @After
    public void destory() {
        if (mg != null)
            mg.close();
        mg = null;
        db = null;
        users = null;
        System.gc();
    }
    
    private void queryAll() {
        print("查询users的所有数据：");
        //db游标
        DBCursor cur = users.find();
        while (cur.hasNext()) {
            print(cur.next());
        }
        
        print("Total Count: " + users.count() + '\n');
    }
    
    public void print(Object o) {
        System.out.println(o);
    }
    
	 
	public void test1() {
		queryAll();
		
		DBObject userinfo = new BasicDBObject();
		userinfo.put("name", "zwj");
		userinfo.put("language","java");
		userinfo.put("tools","MyEclipse-2");
		userinfo.put("age", 25);
//		print(users.save(userinfo).getN());
//		queryAll();
		
		List<DBObject> list = new ArrayList<DBObject>();
		list.add(userinfo);
		DBObject userinfoXL = new BasicDBObject("name","xinlin");
		userinfoXL.put("language", "C");
		userinfoXL.put("game", "DOTA");
		list.add(userinfoXL);
		print(users.insert(list).getN());
		
		
		queryAll();
	}
	
	@Test
	public void remove() {
	    queryAll();
	    print("删除id = 508167cf66d92610a341e246：" 
	    		+ users.remove(
	    				new BasicDBObject("_id", new ObjectId("508167cf66d92610a341e246"))
	    			).getN());
	    queryAll();
	    print("remove tools = MyEclipse: " 
	    		+ users.remove(
	    				new BasicDBObject("tools", "MyEclipse")
	    				).getN()
				);
	    queryAll();
	    
	    // print("remove age >= 24: " 
	    //+ users.remove(new BasicDBObject("age", new BasicDBObject("$gte", 24))).getN());
	    //"$gt"： 大于	    "$gte"：大于等于	    "$lt"： 小于	    "$lte"：小于等于	    "$in"： 包含 
	}
	
	
	
	
	
	
	
	
	
	
	
	
    
}
