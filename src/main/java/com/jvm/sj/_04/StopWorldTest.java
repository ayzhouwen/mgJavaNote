package com.jvm.sj._04;

import java.util.HashMap;
/**
 * <<实战java虚拟机>>4.3.3 软引用当内存不足时可以被回收, -Xmx512m -Xms512m -Xmn650k -XX:+UseSerialGC -XX:+PrintGCDetails
 *  可以看出来Full gc造成几百毫秒,秒级的停顿
 */
public class StopWorldTest {
    public static class MyThread extends Thread {
        HashMap map = new HashMap();

        @Override
        public void run() {

            while (true) {
                try {
                    //  System.out.println("map.size()*512/1024/1024:="+(map.size()*512/1024/1024));
                    if (map.size() * 512 / 1024 / 1024 >= 800) {
                        map.clear();
                        System.out.println("clean map0");
                    }
                    byte[] b1;
                    for (int i = 0; i < 100; i++) {
                        b1 = new byte[512];
                        map.put(System.nanoTime(), b1);
                    }
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static class PrintThread extends Thread {
        public static final long starttime = System.currentTimeMillis();

        @Override
        public void run() {
            try {
                while (true) {
                    long t = System.currentTimeMillis() - starttime;
                    System.out.println(t / 1000 + "." + t % 1000);
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyThread t = new MyThread();
        PrintThread p = new PrintThread();
        t.start();
        p.start();
    }
}
