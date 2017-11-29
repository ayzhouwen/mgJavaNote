package com.designPattern.proxy.demo2;

public class DBQuery implements  IDBQuery
{
    public DBQuery(){
        try {
            System.out.println("模拟创建开始");
            Thread.sleep(3000);  //可能包含数据库连接等耗时操作
            System.out.println("模拟创建结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String request() {
        System.out.println("最终实现类执行");
        return  "requset string";
    }
}
