package com.meConcurrent.thread;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 守护线程测试总结
 * 1.thread.setDaemon(true); 是将线程设置为守护线程,默认是用户线程
 * 2.用户线程和守护线程功能上没什么区别,但是当jvm中没有用户线程,只剩下守护线程时,那么jvm将停止运行,进程退出
 * 3.当守护线程创建出来的线程默认是守护线程,可以更改为用户线程
 * 4.当用户线程创建出来的线程默认是用户线程,可以改为守护线程
 */
@Slf4j
public class DaemonTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(()->{
            Thread thread2=new Thread(()->{
                while (true){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("当前线程B时间:{},是否守护线程:{}", DateUtil.now(),Thread.currentThread().isDaemon());
                }

            });
//            thread2.setDaemon(false);
            thread2.start();

            while (true){
                try {
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("当前线程A时间:{},是否守护线程:{}", DateUtil.now(),Thread.currentThread().isDaemon());

            }
        });
        //设置为守护线程
//        thread.setDaemon(true);
        thread.start();
        Thread.sleep(5000);
    }
}
