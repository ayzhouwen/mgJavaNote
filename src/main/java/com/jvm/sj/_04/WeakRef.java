package com.jvm.sj._04;

import java.lang.ref.WeakReference;

/**
 * <<实战java虚拟机>>4.3.4 弱引用-发现即回收,
 * jvm 参数配置  -Xms190M -Xmx190M -XX:+PrintGCDetails
 *
 */
public class WeakRef {
    public static class User{
        public int id;
        public String name;
        public User(int id,String name){
            this.id=id;
            this.name=name;
        }
        public Byte [] baa;

        @Override
        public String toString() {
            return "[id="+id+",name="+name+"]";
        }
    }

    public static void main(String[] args) throws InterruptedException {
        User u=new User(1,"花花");
        u.baa=new Byte[1024*1024*30];
        WeakReference<User> userWeakReference=new WeakReference<>(u);
        // 当user为null时
//         u=null;
        System.out.println(userWeakReference.get());
        System.gc();
        //不管当前内存空间足够与否,都会回收他的内存
        Thread.sleep(1000L);
        System.out.println("After GC:");
        System.out.println(userWeakReference.get()); //不用配置jvm内存参数直接输出null,

    }
}
