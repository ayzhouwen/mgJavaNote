package com.jvm.sj._04;

import java.lang.ref.SoftReference;

/**
 * <<实战java虚拟机>>4.3.3 软引用当内存不足时可以被回收,
 */
public class SoftRef {
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

    public static void main(String[] args) throws InterruptedException {
            User u=new User(1,"geym");
        SoftReference<User> userSoftReference=new SoftReference<User>(u);
        u=null;
        System.out.println(userSoftReference.get());
        System.gc();
        System.out.println("After GC");
        System.out.println(userSoftReference.get());

        byte [] b=new byte[1024*931*7];//注意书上分配的字节是1024*925*7,这个是不会造成java回收内存的,适当的放大一些
        System.gc();
        Thread.sleep(5000);
        System.out.println(userSoftReference.get());
    }

}
