package com.jvm.sj._04;

import java.lang.ref.WeakReference;

/**
 * <<实战java虚拟机>>4.3.4 弱引用-发现即回收,
 */
public class WeakRef {
    public static class User{
        public int id;
        public String name;
        public User(int id,String name){
            this.id=id;
            this.name=name;
        }

        @Override
        public String toString() {
            return "[id="+id+",name="+name+"]";
        }
    }

    public static void main(String[] args) {
        User u=new User(1,"geym");
        WeakReference<User> userWeakReference=new WeakReference<>(u);
        u=null;
        System.out.println(userWeakReference.get());
        System.gc();
        //不管当前内存空间足够与否,都会回收他的内存
        System.out.println("After GC:");
        System.out.println(userWeakReference.get()); //不用配置jvm内存参数直接输出null,

    }
}
