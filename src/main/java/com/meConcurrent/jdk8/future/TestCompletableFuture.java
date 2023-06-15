package com.meConcurrent.jdk8.future;

import com.util.MyDateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
@Slf4j
public class TestCompletableFuture {


    //注意 如果并发任务中有任意一处抛出异常,那么调用者很容易挂掉,必须catch住异常,如果调用线程是核心线会瞬间导致其崩溃,catch住,catch住,catch住

    //执行完任务的时间基本上等于最长任务的执行时间

    //注意生产环境尽量为为CompletableFuture指定不同的线程池,否则会造成只使用一个线程池问题,很容易造成业务堆积

    //注意 如果futuresList遍历时执行当前的futures发生异常,代码肯定会异常,代码就此中断,所以join后下执行函数一定要catch 异常,不要干扰其他任务数据的读取
    public  void thenCombine(){
        long stime=System.currentTimeMillis();
        try {

            CompletableFuture<String> result=CompletableFuture.supplyAsync(()->{
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                log.info("第一个CF");
                return "hello1";
            }).thenCombine(CompletableFuture.supplyAsync(() -> {
//            if (true){
//                throw new RuntimeException("故意异常");
//            }
                log.info("第二个CF");
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return "hello2";
            }), (t, u) -> {
                log.info("组合:"+t+","+u);
                return t + " " + u;
            }).thenCombine(CompletableFuture.supplyAsync(() -> {
//            if (true){
//                throw new RuntimeException("故意异常");
//            }
                log.info("第三个CF");
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return "hello3";
            }), (t1, u2) -> {

                log.info("组合2:"+t1+","+u2);
                return t1 + " " + u2;


            });

            log.info("最终值:"+result.get());
        } catch (Exception e) {
            e.printStackTrace();
        }


        log.info(MyDateUtil.execTime("thenCombine 耗时",stime));
    }


    //注意代码中的线程池只是方便测试,生产环境线程池必须为静态变量,不能是局部变量,除非自己手动卸载线程池
    public void testAllOf() {
        ThreadPoolExecutor poolExecutor= (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        long stime = System.currentTimeMillis();
        List<CompletableFuture<Map<String,Object>>> futuresList=new ArrayList<>();
        for (int i = 0; i <300 ; i++) {
            int finalI = i;
            CompletableFuture<Map<String,Object>> result=CompletableFuture.supplyAsync(()->{
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Map<String,Object> data=new HashMap<>();
                data.put("name","张"+ finalI);
                data.put("age", finalI);
                return data;
            }, poolExecutor);
            futuresList.add(result);
        }
        this.listAllOf(futuresList,poolExecutor);
        log.info(MyDateUtil.execTime("testAllOf 耗时", stime)+"数据长度:"+futuresList.size());
    }

    //多任务异步全部执行完毕方式1:
    public <T> void listAllOf(List<CompletableFuture<T>> futuresList,ThreadPoolExecutor poolExecutor) {
//        CompletableFuture<Void> allFuturesResult =
//                CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));
//                 allFuturesResult.join();

        futuresList.forEach(e -> {
            e.join();
        });
        if (poolExecutor!=null){
            poolExecutor.shutdownNow();
        }
    }


    //多任务异步全部执行完毕方式2 ,直接遍历list,然后执行join方法
    public <T> void listAllOf2(List<CompletableFuture<T>> futuresList) {
        List list = futuresList.stream().map(e -> {
            Object o = e.join();
            //  log.info(o);
            return o;
        }).collect(Collectors.toList());
//        log.info(list);

    }
    public static void main(String[] args) {
        TestCompletableFuture tcf = new TestCompletableFuture();
//        for (int i = 0; i <1 ; i++) {
//            tcf.thenCombine();
//        }



        new Thread(()->{
            tcf.testAllOf();
        }).start();


        new Thread(()->{
            tcf.testAllOf();
        }).start();


        new Thread(()->{
            tcf.testAllOf();
        }).start();

        new Thread(()->{
            tcf.testAllOf();
        }).start();

        try {
            Thread.sleep(1000*60*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
