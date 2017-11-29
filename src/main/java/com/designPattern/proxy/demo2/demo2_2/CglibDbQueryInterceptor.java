package com.designPattern.proxy.demo2.demo2_2;

import com.designPattern.proxy.demo2.DBQuery;
import com.designPattern.proxy.demo2.IDBQuery;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
//<<java程序性能优化第二章使用Cglib动态代理>
public class CglibDbQueryInterceptor implements MethodInterceptor {
    IDBQuery real=null;
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (real==null){
            real=new DBQuery();
        }
        return real.request();
    }
}
