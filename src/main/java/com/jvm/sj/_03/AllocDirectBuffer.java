package com.jvm.sj._03;

import java.nio.ByteBuffer;

/**
 * <<实战java虚拟机>>3.3.3 直内存与堆内存分配时间测试
 */
public class AllocDirectBuffer {
    public void directAllocate(){
        long starttime=System.currentTimeMillis();
        for (int i=0;i<20000;i++){
            ByteBuffer b=ByteBuffer.allocateDirect(1000);
        }
        long endtime=System.currentTimeMillis();
        System.out.println("directAllocate:"+(endtime-starttime));
    }

    public void bufferAllocate(){
        long starttime=System.currentTimeMillis();
        for (int i=0;i<20000;i++){
            ByteBuffer b=ByteBuffer.allocate(1000);
        }
        long endtime=System.currentTimeMillis();
        System.out.println("ByteBufferAllocate:"+(endtime-starttime));
    }

    public static void main(String[] args) {
        AllocDirectBuffer alloc=new AllocDirectBuffer();
        //alloc.bufferAccess();
        alloc.directAllocate();
    }
}
