package com.meConcurrent.future;

import com.util.MyDateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class TestCompletableFuture {


    //注意 如果并发任务中有任意一处抛出异常,那么调用者很容易挂掉,必须catch住异常,如果调用线程是核心线会瞬间导致其崩溃,catch住,catch住,catch住

    //执行完任务的时间基本上等于最长任务的执行时间
    public  void thenCombine(){
        long stime=System.currentTimeMillis();
        try {

            CompletableFuture<String> result=CompletableFuture.supplyAsync(()->{
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("第一个CF");
                return "hello1";
            }).thenCombine(CompletableFuture.supplyAsync(() -> {
//            if (true){
//                throw new RuntimeException("故意异常");
//            }
                System.out.println("第二个CF");
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return "hello2";
            }), (t, u) -> {
                System.out.println("组合:"+t+","+u);
                return t + " " + u;
            }).thenCombine(CompletableFuture.supplyAsync(() -> {
//            if (true){
//                throw new RuntimeException("故意异常");
//            }
                System.out.println("第三个CF");
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return "hello3";
            }), (t1, u2) -> {

                System.out.println("组合2:"+t1+","+u2);
                return t1 + " " + u2;


            });

            System.out.println("最终值:"+result.get());
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(MyDateUtil.execTime("thenCombine 耗时",stime));
    }


    public void testAllOf() {
        long stime = System.currentTimeMillis();
        List<CompletableFuture<Map<String,Object>>> futuresList=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            int finalI = i;
            CompletableFuture<Map<String,Object>> result=CompletableFuture.supplyAsync(()->{
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Map<String,Object> data=new HashMap<>();
                data.put("name","张"+ finalI);
                data.put("age", finalI);
                return data;
            });
            futuresList.add(result);
        }
        this.listAllOf(futuresList);
        System.out.println(MyDateUtil.execTime("testAllOf 耗时", stime));
    }

    //多任务异步全部执行完毕方式1:
    public <T> void listAllOf(List<CompletableFuture<T>> futuresList) {
//        CompletableFuture<Void> allFuturesResult =
//                CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));
//                 allFuturesResult.join();
        futuresList.forEach(e -> {
            System.out.println(e.join());
        });

        futuresList.forEach(e -> {
            System.out.println(e.join());
        });

//        futuresList.forEach(e->{
//            System.out.println(e.join());});

    }


    //多任务异步全部执行完毕方式2 ,直接遍历list,然后执行join方法
    public <T> void listAllOf2(List<CompletableFuture<T>> futuresList) {
        List list = futuresList.stream().map(e -> {
            Object o = e.join();
            //  System.out.println(o);
            return o;
        }).collect(Collectors.toList());
        System.out.println(list);

    }
    public static void main(String[] args) {
        TestCompletableFuture tcf = new TestCompletableFuture();
//        for (int i = 0; i <1 ; i++) {
//            tcf.thenCombine();
//        }

        tcf.testAllOf();
    }
}
