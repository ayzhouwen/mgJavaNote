package com.designPattern.proxy;
//订单接口
public interface  OrderApi {
	//获取产品名称
	public String getProductName();
	//获取用户
	public String getUserName();
	//设置产品名称
	public  void setProName( String uname ,String name);
	
}
