package com.designPattern.proxy.demo2;

import com.designPattern.proxy.demo2.demo2_2.CglibDbQueryInterceptor;
import com.designPattern.proxy.demo2.demo2_3.JavasistDynDbQueryHandler;
import javassist.*;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import net.sf.cglib.proxy.Enhancer;

public class TestDBQuery {
    public static void main(String[] args) {
//        IDBQuery query=new DBQueryProxy(); //使用静态代理
//        query.request();

        //jdk实现动态代理
//        IDBQuery jdkProxy= (IDBQuery) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new Class[]{IDBQuery.class},new JdkDbQeuryProxyHandler());
//        jdkProxy.request();

        //使用cglib实现动态代理
      TestDBQuery.createCglibProxy().request();

        //javasist工厂模式实现动态代理
//        try {
//            TestDBQuery.createJavassistDynProxy().request();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //动态拼接字符串动态代理
//        try {
//            TestDBQuery.createJavassistBytecodeDynamicProxy().request();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    //  使用cglib实现动态代理
    public  static IDBQuery createCglibProxy(){
        Enhancer enhancer=new Enhancer();
        enhancer.setCallback(new CglibDbQueryInterceptor());
        //指定切入器,定义代理类逻辑
        enhancer.setInterfaces(new Class[]{IDBQuery.class});
        //指定实现的接口
        IDBQuery cglibProxy=(IDBQuery) enhancer.create();
        return  cglibProxy;
    }

//javasist以工厂模式实现动态代理

    public  static  IDBQuery createJavassistDynProxy() throws  Exception{
        ProxyFactory proxyFactory=new ProxyFactory();
        proxyFactory.setInterfaces(new  Class[] {IDBQuery.class});
        Class proxyClass=proxyFactory.createClass();
        IDBQuery javassistProxy=(IDBQuery)proxyClass.newInstance();
        ((ProxyObject)javassistProxy).setHandler(new JavasistDynDbQueryHandler());
        return javassistProxy;
    }


    //javasist以动态java代码,并生成字节码非常灵活创建,(动态字符串转化为类,确实灵活)
   public  static IDBQuery createJavassistBytecodeDynamicProxy() throws Exception{
       ClassPool mPool=new ClassPool(true);
       //定义类名
       CtClass mCtc=mPool.makeClass(IDBQuery.class.getName()+"Javaassist-BytecodeProxy");
       //需要实现的接口
       mCtc.addInterface(mPool.get(IDBQuery.class.getName()));
       //添加构造函数
       mCtc.addConstructor(CtNewConstructor.defaultConstructor(mCtc));
       //添加类的字段信息， 使用动态java代码
       mCtc.addField(CtField.make("public "+IDBQuery.class.getName()+" real;",mCtc ) ) ;
       String dbqueryname=DBQuery.class.getName();
       //添加方法，使这里使用个动态内部逻辑
       mCtc.addMethod(CtMethod.make("public String request() {if (real==null) real=new  "+dbqueryname+" (); return real.request();}",mCtc));
       //基于以上信息，生成动类
       Class pc=mCtc.toClass();
       //生成动态类的实例
       IDBQuery bytecodeProsy=(IDBQuery)pc.newInstance();
       return bytecodeProsy;




    }

}
