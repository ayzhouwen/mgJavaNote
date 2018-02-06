package com.meConcurrent;

//读写锁测试
//来源:http://www.cnblogs.com/liuling/p/2013-8-21-03.html

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @作者 zw
 * @描述:
 * @创建于 2018/2/1  13:37
 * @修改者:
 * @参数: * @param null
 */
public class TestReadWrjteLockTest {
    public static void main(String[] args) {
       // test1();
        test2();
    }

    public static void test1() {
        final Queue3 q3 = new Queue3();
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        q3.get();
                    }
                }
            }).start();
        }


        for (int i=0;i<3;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        q3.put(new Random().nextInt(10000));
                    }
                }
            }).start();
        }


    }

    public static void test2() {
        final CacheDemo cacheDemo=new   CacheDemo();
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(new Random().nextInt(10000));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(cacheDemo.get( Long.toString(new Random().nextInt(3))));
                    }
                }
            }).start();
        }





    }



}



class Queue3 {
    private Object data = null; //共享数据,只能有一个线程能写该数据,但可以多个线程同时读该数据
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public void get() {
        try {
            rwl.readLock().lock();  //上读锁,其他线程只能读不能写
            System.out.println(Thread.currentThread().getName() + "准备读取数据!");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "完成数据读取:" + data);
        } finally {
            rwl.readLock().unlock();
        }
    }

    public void put(Object data) {
        rwl.writeLock().lock();  //上写锁,不允许其他线程读,也不允许写
        try {
            System.out.println(Thread.currentThread().getName() + "  准备写数据");

            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            this.data = data;

            System.out.println(Thread.currentThread().getName() + " 完成写数据" + data);
        } finally {
            rwl.writeLock().unlock(); //释放写锁
        }
    }
}

//缓存器
class  CacheDemo{
    private Map<String,Object> map=new HashMap<String,Object>() ;//缓存器
    private ReadWriteLock rwl=new ReentrantReadWriteLock();

    public  Object get(String id){
        Object value=null;
        //System.out.println(Thread.currentThread().getName() + "获取读锁前");
        rwl.readLock().lock(); //首先开启读锁.从缓存中去取
        //System.out.println(Thread.currentThread().getName() + "获取读锁后");
        try {
            value=map.get(id);
            //如果缓存中没有释放读锁,上写锁
            if (value==null){
                rwl.readLock().unlock();
                rwl.writeLock().lock();

                try {
                    if (value==null){
                        value="aaa"; //此时可以去数据库查找,这里简单的模拟一下
                        map.put(id,value);
                    }
                } finally {
                    rwl.writeLock().unlock(); //释放写锁
                }

                rwl.readLock().lock(); //然后再上读锁

            }
        } finally {
                rwl.readLock().unlock(); //最后释放写锁
        }

        return  value;

    }
}
