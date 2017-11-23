package com.designPattern.proxy.demo2;
//<<java程序性能优化第二章代理>
//利用代理实现懒加载,提高项目启动时间,这里使用的是静态代理
public interface IDBQuery {
    String requset();
}
