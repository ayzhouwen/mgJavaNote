package com.jvm.sj._03;
/**
 * //<<实战java虚拟机>>3.3 新生代内存测试,注意现在的jdk1.8已经与书中的年轻代的收集器不一样了,现在的是PSYoungGen ,可以用参数指定其收集器
 * 1.在 -Xmx20m -Xms20m -Xmn1m -XX:SurvivorRatio=2 -XX:+PrintGCDetails 下如果循环执行10000000次,每次线程休眠5毫秒
 * 那么并不会出现内存溢出,因为老年代在不停的gc与存储,
 * 2. 在 -Xmx20m -Xms20m -Xmn7m -XX:SurvivorRatio=2 -XX:+PrintGCDetails 下如果循环执行10次,每次线程休眠5毫秒
 * 没有发生老年gc 3次年轻gc
 * 3.  在 -Xmx20m -Xms20m -Xmn15m -XX:SurvivorRatio=2 -XX:+PrintGCDetails 下如果循环执行10次,每次线程休眠5毫秒
 * 没有老年gc,只有一次年轻gc
 */
public class NewSizeDemo {
    public static void main(String[] args) throws InterruptedException {
        byte[] b=null;
        for (int i=0;i<1000;i++){
            b=new byte[1*1024*1024];
           // Thread.sleep(5000);
        }
    }
}
