package com.gc;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Random;

public class TestSoftReference {
	public static void main(String[] args) {
				class Mydata{
					public int id;
					public long data;	
			};
			
		    HashMap<Long,SoftReference<Mydata> > hMap=new HashMap<>(); //只能是hashmap相关或者直接数组的,list,treemap,不好使
		  SoftReference<Mydata> [] arr=new SoftReference[100000]; //数组也会释放内存
		 
		    Random ra =new Random();
			  for(int jj=0;jj<200;jj++){
				   Thread tx=new  Thread( new Runnable() {
					@Override
					public void run() {
						int j=0;
						  while(true){
							  j++;
							  	Mydata mk=	  new Mydata();
							  	mk.data=System.currentTimeMillis();
								 SoftReference<Mydata> smk=new SoftReference<Mydata>(mk);
								 

							 	//hMap.put(System.currentTimeMillis(), smk);
								 arr[ra.nextInt(10000)]=smk;



							  try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						  }
					}
				});
				   
				   tx.start();
			   }
	}
}
