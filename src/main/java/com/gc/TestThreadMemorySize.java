package com.gc;

/**
 * 写这个例子主要原因是项目上有个现象是winserver 2008 r2 上出现大量线程创建病结束后
 * 操作系统内存不释放现象,用NMT 发现 thread 的内存一直很大,所以来测试下
 * 注意启动时,要加上 -XX:NativeMemoryTracking=detail 或者 -XX:NativeMemoryTracking=summary
 * 查看时 jcmd 进程id VM.native_memory baseline
 * jcmd  进程id  VM.native_memory summary.diff scale=mb
 */
public class TestThreadMemorySize {
    public static void main(String[] args) throws InterruptedException {
        int i=0;
        while (i<10000){
            i++;
            int finalI = i;
            new Thread(()->{
                int j=0;
                while (j<60*2){
                    j++;
                    System.out.println("第"+ finalI +"个线程"+"第"+j+"次输出");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }).start();
            Thread.sleep(10*1);
        }

        Thread.sleep(1000*3600);
    }
}
