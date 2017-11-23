package com.designPattern.proxy.demo2;

import com.designPattern.proxy.demo2.demo2_1.JdkDbQeuryProxyHandler;

import java.lang.reflect.Proxy;

public class TestDBQuery {
    public static void main(String[] args) {
//        IDBQuery query=new DBQueryProxy(); //使用静态代理
//        query.requset();
        IDBQuery jdkProxy= (IDBQuery) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new Class[]{IDBQuery.class},new JdkDbQeuryProxyHandler());
        jdkProxy.requset();
    }
}
