package com.jvm.sj._02;

/**
 * //<<实战java虚拟机>>2.6 局部变量对垃圾回收的影响.可以 启动时加上-XX:+PrintGC
 */
public class LocalVarGc {

    //不回收,如果死循环去执行这个函数,堆空间使用基本上也是12m,其它的还是被回收掉的
    public void localVarGc1(){
        byte[] a=new byte[6*1024*1024];
        System.gc();
    }

    //回收
    public void localVarGc2(){
        byte[] a=new byte[6*1024*1024];
        a=null;
        System.gc();
    }

    //不回收
    public void localVarGc3(){
        {
            byte[] a = new byte[6 * 1024 * 1024];
        }

        System.gc();
    }

    //回收
    public void localVarGc4(){
        {
            byte [] a=new byte[6*1024*1024];
        }
        int c=10;
        System.gc();
    }

    //localVarGc1在localVarGc5执行完毕后回收
    public void localVarGc5(){
        localVarGc1();
        System.gc();
    }

    public static void main(String[] args) {
        LocalVarGc ins=new LocalVarGc();
       // ins.localVarGc1();
        ins.localVarGc2();
    }


}
