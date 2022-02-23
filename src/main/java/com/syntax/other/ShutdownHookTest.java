package com.syntax.other;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.concurrent.TimeUnit;


/**
 * 测试优雅关机
 * win上如果是在任务栏里直接结束进程,test1和test2都不会走回调,在IDE里点击红色的按钮,会进入test1和test2都会进入回调
 */
public class ShutdownHookTest {
    //ShutdownHook方式
    public static void test1(){
        Runtime.getRuntime().addShutdownHook(new Thread(()->{

            System.out.println("优雅关机:ShutdownHook开始");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("优雅关机:ShutdownHook结束");

        },"优雅关机"));

        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //signal方式
    public static void test2(){
        Signal sig=new Signal(System.getProperties().getProperty("os.name").toLowerCase().startsWith("win")?"INT":"TERM");
        Signal.handle(sig, new SignalHandler() {
            @Override
            public void handle(Signal signal) {
                System.out.println("优雅关机:Signal开始");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("优雅关机:Signal结束");
                //必须加入此代码,否则程序无法退出
                System.exit(0);
            }
        });

        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ShutdownHookTest.test1();
    }
}
