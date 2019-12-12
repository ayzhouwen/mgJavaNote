package com.syntax;


import cn.hutool.core.thread.ThreadUtil;

//目前测试的结果跟文章的结果不一致,文章说捕获不到UncaughtException异常,但是实验发现是可以捕获到的:
//文章地址:https://mp.weixin.qq.com/s/VDxHUPy6T2PhM_viCvRb8w
public class UncaughtExceptionTest {

    static {

        ThreadUtil.execAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行异常前");

                try {
                    if(2>1){
                        throw new UnsupportedOperationException();
                    }
                } catch (Exception  e) {
                    System.out.println("异常了");
                    e.printStackTrace();
                }

                System.out.println("执行异常后");

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("异步线程:执行完");
                System.out.println( "异步线程:"+Thread.currentThread().getState());
            }
        });

    }
    public static void main(String[] args) {
        System.out.println("主线程:执行完");
        System.out.println( "主线程:"+Thread.currentThread().getState());

    }
}
