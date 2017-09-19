package com.designPattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//动态代理类
public class DProxy implements InvocationHandler  {
	//被代理的对象
	private OrderApi order=null;
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//如果是调用setter方法就需要检查权限
		if (method.getName().startsWith("set")) {
			//如果不是创建人,那就不能修改
			if (order.getUserName()!=null&&order.getUserName().equals(args[1])) {
				//
				return method.invoke(order, args);
			}else {
				System.out.println("对不起, "+args[1]+",  您无权修改本订单中的数据");
			}
		}else {
			//不是调用setter方法就继续运行
			return method.invoke(order, args);
		}
		return null;
	}
	
	public OrderApi getProxyInterface(Order order){
		this.order=order;
		OrderApi orderApi=(OrderApi)Proxy.newProxyInstance(order.getClass().getClassLoader(), order.getClass().getInterfaces(), this);
		return orderApi;
	}
	public static void main(String[] args) {
		Order order =new Order("设计模式","张三");
		//创建一个动态代理
		DProxy  dp=new DProxy();
		OrderApi orderApi=dp.getProxyInterface(order);
		orderApi.setProName("mysql", "李四");
		System.out.println("李四修改后订单记录没有变化:"+orderApi);
		orderApi.setProName("mysql", "张三");
		System.out.println("张三修改后,订单记录:"+orderApi);
	}
}
