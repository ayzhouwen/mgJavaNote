package com.meConcurrent.pool;

import cn.hutool.core.date.DateUtil;
import com.util.MyMonitorUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程池扩容与缩容测试，与释放线程核心线程（如果释放完黑那些线程数量后，此时没有用户线程，那么jvm会退出），
 *
 */
@Slf4j
public class ThreadPoolChangeSizeTest {
    /**
     * 模拟的线程池数量由10到100,即使将setCorePoolSize 设置为1000,但是必须重新提交任务时
     * 才会,创建新的线程,所以一定要使用有界队列和最大线程数一定要大于核心线程数
     */
    public static void expand(){
        //
        ThreadPoolExecutor pools=new ThreadPoolExecutor(10,100,
                0L, TimeUnit.SECONDS,new LinkedBlockingDeque<>(200));
        for (int i = 0; i <1000 ; i++) {
            try {
                pools.execute(()->{
                  while (true){
                   log.info(DateUtil.now());
                      try {
                          Thread.sleep(1000L);
                      } catch (Exception e) {
                          throw new RuntimeException(e);
                      }
                  }
                });
            } catch (Exception e) {
                log.error("处理任务异常:",e);
            }
        }
       MyMonitorUtil.getThreadPoolMonitor("测试",pools);
        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //增大核心数,线程池提交新任务时,会创建
        pools.setCorePoolSize(1000);

        pools.allowCoreThreadTimeOut(true);

        MyMonitorUtil.getThreadPoolMonitor("扩容后",pools);

    }

    /**
     * 释放核心线程,注意释放完核心线程，如果没有其他用户线程，那么jvm会退出
     */
    public static void freeCoreThread(){
        AtomicLong atomicLong=new AtomicLong();
        ThreadPoolExecutor pools=new ThreadPoolExecutor(10,10,
                5L, TimeUnit.SECONDS,new LinkedBlockingQueue<>(10));
       //自定义拒绝策略
        pools.setRejectedExecutionHandler((runnable,executor)->{
            log.error("队列满了:{}",atomicLong.incrementAndGet());
        });
        //任务执行完成释放核心线程
        pools.allowCoreThreadTimeOut(true);
        for (int i = 0; i <30 ; i++) {
            int finalI = i;
            pools.execute(()->{
                log.info("第{}个线程执行", finalI);
                try {
                    Thread.sleep(1000*3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                log.info("@@@@第{}个线程执行完毕@@@@", finalI);
            });
        }
        //第二轮测试
        log.info("任务分发完成");
        try {
            //防止任务提前执行
            Thread.sleep(1000*30);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 30; i <60 ; i++) {
            int finalI = i;
            pools.execute(()->{
                log.info("第{}个线程执行", finalI);
                try {
                    Thread.sleep(1000*3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                log.info("@@@@第{}个线程执行完毕@@@@", finalI);
            });
        }

        log.info("任务分发完成2");


    }

    public static void main(String[] args) {
//        expand();
        freeCoreThread();
    }
}
