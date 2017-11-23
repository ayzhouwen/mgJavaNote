package com.designPattern.proxy.demo2.demo2_1;

import com.designPattern.proxy.demo2.DBQuery;
import com.designPattern.proxy.demo2.IDBQuery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
//<<java程序性能优化第二章代理>
//利用代理实现懒加载,提高项目启动时间,这里使用的是动态态代理
public class JdkDbQeuryProxyHandler implements InvocationHandler {
    IDBQuery real=null; //主题接口
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (real==null){
            real=new DBQuery();  //如果时第一次调用,则生成真实对象
        }
        return real.requset();
    }
}

