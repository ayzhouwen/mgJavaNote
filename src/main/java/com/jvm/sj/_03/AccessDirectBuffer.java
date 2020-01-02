package com.jvm.sj._03;

import java.nio.ByteBuffer;

/**
 * //<<实战java虚拟机>>3.3.3 直接内存配置,看来jdk1.8做优化了,即使加上-server参数,并没有书中说的那么离谱,高出一个数量级(10倍)
 * i<100000000下
 * testBufferWrite:16361
 * testDirectWrite:9695
 * testBufferWrite:29951
 * testDirectWrite:11222
 *
 * i<1000000下
*    testBufferWrite:184
*    testDirectWrite:104
*    testBufferWrite:322
*    testDirectWrite:109
 *  *
 */
public class AccessDirectBuffer {
    public void directAccess(){
        long starttime=System.currentTimeMillis();
        ByteBuffer b=ByteBuffer.allocateDirect(400);
        for (int i=0;i<1000000;i++){
            for (int j=0;j<99;j++){
                b.putInt(j);
            }
            b.flip();
            for (int j=0;j<99;j++){
                b.getInt();
            }
            b.clear();
        }
        long endtime=System.currentTimeMillis();
        System.out.println("testDirectWrite:"+(endtime-starttime));
    }


    public void bufferAccess(){
        long starttime=System.currentTimeMillis();
        ByteBuffer b=ByteBuffer.allocate(400);
        for (int i=0;i<1000000;i++){
            for (int j=0;j<99;j++){
                b.putInt(j);
            }
            b.flip();
            for (int j=0;j<99;j++){
                b.getInt();
            }
            b.clear();
        }
        long endtime=System.currentTimeMillis();
        System.out.println("testBufferWrite:"+(endtime-starttime));
    }
    public static void main(String[] args) {
        AccessDirectBuffer alloc=new AccessDirectBuffer();
        alloc.bufferAccess();
        alloc.directAccess();

        alloc.bufferAccess();
        alloc.directAccess();

    }
}
