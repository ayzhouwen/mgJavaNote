package com.meConcurrent.lock;

import cn.hutool.json.JSONUtil;
import com.util.MyDateUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * 多线程下模拟根据锁块(不同ip)来计算不同ip累加值是否准确
 */
public class MySegmentedLock {
    public static ConcurrentHashMap<String, Object> lockMap = new ConcurrentHashMap<>();
    public static Map<String,Integer> numMap = new ConcurrentHashMap<>();

    public void testLock() {
        long stime=System.currentTimeMillis();
        Integer threadNum = 40;
        CountDownLatch cdh = new CountDownLatch(threadNum);
        lockMap.put("192.168.1.1",new Object());
        lockMap.put("192.168.1.2",new Object());
        numMap.put("192.168.1.1",0);
        numMap.put("192.168.1.2",0);
        for (int i = 0; i < threadNum; i++) {
            String lockName ="";
            if (i<10){
                lockName="192.168.1.1";
            }else {
                lockName="192.168.1.2";
            }
            //System.out.println(i+":"+lockName);
            MyRun myRun = new MyRun(lockName, cdh);
            Thread t = new Thread(myRun);
            t.start();
        }

        try {
            cdh.await();
            System.out.println(MyDateUtil.execTime("计算完成时间:",stime));
            System.out.println("当前num值:" + JSONUtil.toJsonStr(numMap));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        MySegmentedLock mySegmentedLock = new MySegmentedLock();
        mySegmentedLock.testLock();
    }
}

class MyRun implements Runnable {
    private CountDownLatch cdh;
    private String ip;
    public MyRun(String ip, CountDownLatch cdh ) {
        this.cdh = cdh;
        this.ip = ip;
    }

    @Override
    public  void run() {
        try {
            //模拟分区计算
            synchronized(MySegmentedLock.lockMap.get(ip)) {
                for (int i = 0; i <5000 ; i++) {
                    Integer num= MySegmentedLock.numMap.get(ip);
                    num++;
                    MySegmentedLock.numMap.put(ip,num);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cdh.countDown();
        }

    }
}


