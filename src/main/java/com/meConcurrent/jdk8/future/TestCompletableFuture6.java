package com.meConcurrent.jdk8.future;

import com.util.MyDateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture 超时测试
 */
@Slf4j
public class TestCompletableFuture6 {
    /**
     * 以后尽量用supplyAsync，如果用runAsync ，在get时会报空指针异常
     */
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
                CompletableFuture<String> future=CompletableFuture.supplyAsync((()->{
                    try {
                            if (e.equals("A")){
                                //模拟超时
                                Thread.sleep(3000);
                            }

                        if (e.equals("E")){
                            //模拟超时
                            Thread.sleep(4000);
                        }

                    } catch (Exception ex) {
                        log.error("获取数据换装远程接口异常", ex);
                    }
                    return e;
                }));
                futureList.add(future);
            });
            CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(
                    futureList.toArray(new CompletableFuture[0]));
            try {
                // 尝试在5秒内完成所有任务
                combinedFuture.get(5, TimeUnit.SECONDS);
            } catch (Exception e) {
                log.error("获取数据换装远获取数据异常", e);
            }
        } finally {
            System.out.println(MyDateUtil.execTime("所有任务异步等待时间：",stime));
        }


    }
    public static void main(String[] args) {

        testTime1();

    }
}
