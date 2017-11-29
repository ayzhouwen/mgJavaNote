package com.designPattern.proxy.demo2.demo2_3;

import com.designPattern.proxy.demo2.DBQuery;
import com.designPattern.proxy.demo2.IDBQuery;
import javassist.util.proxy.MethodHandler;

import java.lang.reflect.Method;
//<<java程序性能优化第二章使用javassist动态代理>
public class JavasistDynDbQueryHandler implements MethodHandler {
    IDBQuery real=null;
    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        if (real==null){
            real=new DBQuery();
        }
        return  real.request();
    }
}
