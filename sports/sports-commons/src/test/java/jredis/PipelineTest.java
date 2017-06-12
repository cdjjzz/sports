package jredis;


import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commons.PropertiesHelper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

public class PipelineTest {
	final static String  IP=PropertiesHelper.getAsString("ip");
	final static int PORT=PropertiesHelper.getAsInteger("port");
	private  Jedis jedis;
	
	@Before
	public  void outStartTime(){
		System.out.println(System.currentTimeMillis());
	}
	@After
	public  void outEndTime(){
		System.out.println(System.currentTimeMillis());
	}
	//使用管道符
	@Test
	public  void usePipeline(){
		jedis = new Jedis(IP,PORT);
		Pipeline pipeline = jedis.pipelined();  
		for (int i = 0; i < 1000; i++) {
			pipeline.incr("selfincr");
		}
		pipeline.sync();
		jedis.disconnect();
	}
	//不使用
	@Test
	public  void noPipeline(){
		jedis = new Jedis(IP,PORT);
		for (int i = 0; i < 1000; i++) {
			jedis.incr("selfincr");
		}
		jedis.disconnect();
	}
	@Test
	public  void transactionTest(){
		jedis = new Jedis(IP,PORT);
		Transaction transaction=jedis.multi();
		transaction.set("key", "aa");  
		long l=System.currentTimeMillis();
		while(System.currentTimeMillis()-l<5000);
	    transaction.set("key1", "bb");  
	    List<Object> oList=transaction.exec();  
	    System.out.println(oList);  
	          
        jedis.watch("key");//使用redis 乐观锁  
        transaction = jedis.multi();  
        transaction.set("key2", "cc");  
        transaction.set("key3", "dd");  
        oList = transaction.exec();  
	    System.out.println(oList);  
	}
	
	
	
	
}
