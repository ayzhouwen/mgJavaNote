package com.designPattern.proxy.demo2;

public class DBQueryProxy implements  IDBQuery {
    private DBQuery real=null;

    @Override
    public String request() {
        //在真正需要的时候,才会创建真是对象,创建过程可能很慢
        if (real==null){
            real=new DBQuery();
        }

        //在多线程环境下,这里返回一个虚假类,类似Future模式
        return real.request();
    }
}
