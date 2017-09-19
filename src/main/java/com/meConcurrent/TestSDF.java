package com.meConcurrent;
//SimpleDateFormat  日期非线程安全引起的并发问题

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestSDF {
		final static SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		public String format(Date d){
			return sdf.format(d);
		}
		
		public static void main(String[] args) {
				ExecutorService es=Executors.newFixedThreadPool(50);
				for(int i=0;i<50;i++){
					PrintTimeNew  pt=new PrintTimeNew();
					pt.threadName=i;
					es.execute(pt);
				}
				 es.shutdown();
		}
}

class PrintTimeNew extends Thread{//时间转字符线程
			public int  threadName;
		    TestSDF ts =new TestSDF();
		    SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    @Override
		    public void run(){
		    	super.run();
		    	Date d=null;
		    	String strD=null;
		    	if (threadName%2==0) {
					strD="2012-02-02 12:12:12";
				}else {
					 strD="2012-03-30 12:12:12";  
				}
		    	try {
					d=sdf.parse(strD);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	for(int i=0;i<100;i++){
		    		String sd=ts.format(d);
		    		try {
						Thread.sleep(1);
					} catch (Exception e) {
						// TODO: handle exception
					}
		    		
		    		 if(!sd.equals("2012-02-02 12:12:12") &&(threadName%2==0)){  
		                 System.out.println(threadName+":  原:"+strD+"  新:"+sd);  
		             } 
		    	}
		    }
}
