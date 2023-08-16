package com.meConcurrent.pool;

import cn.hutool.json.JSONUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池引用局部变量测试,结论:不会出现并发问题,因为不涉及到修改,如果修改编译直接报错
 */
public class ThreadPoolVarTest {
    /**
     * 测试线程池内引用通用变量
     */
    public void testgeneralVar(){
        ExecutorService t= Executors.newFixedThreadPool(20);

        /**第一种情况编译报错
         *  Long num=0L;
         *         t.submit(()->{
         *            num= RandomUtil.randomLong(); //编译报错
         *         });
         */

        String str="a,b,c,d,e,f,g,h,i,j,k,l,m,n";
        for (int i=0;i<10000;i++){
            int finalI = i;
            t.submit(()->{
                System.out.println("i值:"+ finalI);
                String [] arr= str.split(",");
                System.out.println(Thread.currentThread().getName()+"-"+ JSONUtil.toJsonStr(arr));
            });
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("主线程:str"+str);

    }

    public static void main(String[] args) {
        ThreadPoolVarTest tpt=new ThreadPoolVarTest();
        tpt.testgeneralVar();
    }
}
