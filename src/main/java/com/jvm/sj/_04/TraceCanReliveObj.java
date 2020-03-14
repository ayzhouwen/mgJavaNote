package com.jvm.sj._04;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * <<实战java虚拟机>>4.3.5 虚引用-对象回收跟踪
 */
public class TraceCanReliveObj {
    public static TraceCanReliveObj obj;
    static ReferenceQueue <TraceCanReliveObj> phantomQueue=null;
    public static class CheckRefQueue extends Thread{
        @Override
        public void run() {
            while(true){

                if (phantomQueue !=null){
                    PhantomReference<TraceCanReliveObj> objt=null;
                    try {
                        objt= (PhantomReference<TraceCanReliveObj>) phantomQueue.remove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (objt !=null){
                        System.out.println("TraceCanReliveObj is delete by GC");
                    }
                }
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("CanReliveObj finalize called");
        obj=this;
    }

    @Override
    public String toString() {
        return "I am CanReliveObj";
    }

    public static void main(String[] args) {
        Thread t=new CheckRefQueue();
      //  t.setDaemon(true);
        t.start();
        phantomQueue =new ReferenceQueue<TraceCanReliveObj>();
        obj=new TraceCanReliveObj();
        PhantomReference<TraceCanReliveObj> phantomRef=new PhantomReference<>(obj,phantomQueue);
        obj=null;
        System.gc();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (obj==null){
            System.out.println("obj 是 null");
        }else {
            System.out.println("obj 可用");
        }

        System.out.println("第2 次gc");
        obj=null;
        System.gc();

        if (obj==null){
            System.out.println("obj 是 null");
        }else {
            System.out.println("obj 可用");
        }
    }
}
