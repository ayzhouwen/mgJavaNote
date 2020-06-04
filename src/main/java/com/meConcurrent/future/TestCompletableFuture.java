package com.meConcurrent.future;

import com.util.MyDateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestCompletableFuture {


    //注意 如果并发任务中有任意一处抛出异常,那么调用者很容易挂掉,必须catch住异常,如果调用线程是核心线会瞬间导致其崩溃,catch住,catch住,catch住

    //执行完任务的时间基本上等于最长任务的执行时间
    public  void thenCombine(){
        long stime=System.currentTimeMillis();
        try {
            CompletableFuture

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




    public  void allOf(){
        List<CompletableFuture<Map<String,Object>>> futuresList=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            int finalI = i;
            CompletableFuture<Map<String,Object>> result=CompletableFuture.supplyAsync(()->{
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("第一个CF");
                Map<String,Object> data=new HashMap<>();
                data.put("name","张"+ finalI);
                data.put("age", finalI);
                return data;
            });

            futuresList.add(result);
        }

//        CompletableFuture<Integer> one = CompletableFuture.supplyAsync(() -> 1);
//        CompletableFuture<Integer> two = CompletableFuture.supplyAsync(() -> 2);
//        CompletableFuture<Integer> three = CompletableFuture.supplyAsync(() -> 3);
//        CompletableFuture<Integer> four = CompletableFuture.supplyAsync(() -> 4);
//        CompletableFuture<Integer> five = CompletableFuture.supplyAsync(() -> 5);
//        CompletableFuture<Integer> six = CompletableFuture.supplyAsync(() -> 6);
//
//        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(one, two, three, four, five, six);
//        voidCompletableFuture.thenApply(v->{
//            return Stream.of(one,two,three,four, five, six)
//                    .map(CompletableFuture::join)
//                    .collect(Collectors.toList());
//        }).thenAccept(System.out::println);


        String[] arr;
        arr=new  String[3];
        CompletableFuture<Void> allFuturesResult =
                CompletableFuture.allOf(arr[0]);
         allFuturesResult.thenApply(v ->
                futuresList.stream().
                        map(future -> future.join()).
                        collect(Collectors.toList())
        );
    }

    public static void main(String[] args) {
        TestCompletableFuture tcf=new TestCompletableFuture();
        for (int i = 0; i <1 ; i++) {
            tcf.thenCombine();
        }

    }
}
