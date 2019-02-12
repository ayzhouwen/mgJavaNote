package com.syntax.other;

import java.util.concurrent.TimeUnit;

//测试优雅关机
public class ShutdownHookTest {
    public static void main(String[] args) throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread(()->{

            System.out.println("优雅关机:ShutdownHook开始");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("优雅关机:ShutdownHook结束");

        },"优雅关机"));

        TimeUnit.SECONDS.sleep(7);

        System.exit(0);
    }
}
