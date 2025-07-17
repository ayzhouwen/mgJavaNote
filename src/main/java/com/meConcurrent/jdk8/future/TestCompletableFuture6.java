package com.meConcurrent.jdk8.future;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.util.MyDateUtil;
import com.util.MyThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture 超时测试
 */
@Slf4j
public class TestCompletableFuture6 {
    /**
     *
     */
    private static ThreadPoolExecutor externalDataSourcePool=new ThreadPoolExecutor(32,128,0, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(1000),new MyThreadFactory("externalDataSourcePool"));
    public static void testTime1() {
        long stime=System.currentTimeMillis();
        try {
            List<String> list=new ArrayList<>();
            list.add("A");
            list.add("B");
            list.add("C");
            list.add("D");
            list.add("E");
            List<CompletableFuture<String>> futureList=new ArrayList<>();
            list.forEach(e->{
                CompletableFuture<String> future=CompletableFuture.supplyAsync(()->{
                    try {
                            if (e.equals("A")){
                                //模拟超时
                                Thread.sleep(2000);
                            }

                        if (e.equals("E")){

                            //模拟超时
                            Thread.sleep(0);
                            throw new RuntimeException("故意异常");
                        }

                    } catch (Exception ex) {
                        log.error("获取数据换装远程接口异常", ex);
                    }
                    return null;
                },externalDataSourcePool);
                futureList.add(future);
            });
            CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(
                    futureList.toArray(new CompletableFuture[0]));
            try {
                // 尝试在5秒内完成所有任务
                combinedFuture.get(30, TimeUnit.SECONDS);
            } catch (Exception e) {
                //注意此时e.getMessage()返回null
                log.error("获取数据换装远获取数据异常："+e.getMessage());
            }
        } finally {
            System.out.println(MyDateUtil.execTime("所有任务异步等待时间：",stime));
            externalDataSourcePool.shutdown();
        }


    }
    public static void main(String[] args) {

        testTime1();

    }
}
