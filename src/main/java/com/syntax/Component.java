package com.syntax;
//http://blog.csdn.net/imxiangzi/article/details/7949296
//测试this的指向,

import java.io.IOException;

/*内部类的this有三种指向：
1、本类的成员，即this.getDataHandle
2、父类的成员，即super.getDataHandle
3、所属类的成员，即MainSystem.this.getDataHandle*/
public class Component {
	 public void getData() throws IOException, InterruptedException

	  {

	    System.out.println("Transferring data...");
	    getDataHandle();

	  };
	  
	  public void getDataHandle()

	  {

		  System.out.println("Component Data Refreshed.");

	  }

}
