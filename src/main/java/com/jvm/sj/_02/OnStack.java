package com.jvm.sj._02;
/**
 * //<<实战java虚拟机>>2.7 非逃逸对象转栈上分配
 * jvm 测试1:  -XX:+PrintGC -Xmx10m -Xms10m  -XX:-EliminateAllocations
 * 测试1会打印gc 100多次,执行完100毫秒左右,去掉-XX:-EliminateAllocations  参数后6毫秒左右
 * -XX:-DoEscapeAnalysis 跟-XX:-EliminateAllocations效果类似
 */
public class OnStack {
  public static class User{
      public  int id=0;
      public String name="";
  }

  public static void alloc(){
      User u=new User();
      u.id=5;
      u.name="geym";

  }

    public static void main(String[] args) {
        long b=System.currentTimeMillis();
        for (int i=0;i<10000000;i++){
            alloc();
        }
        long e=System.currentTimeMillis();
        System.out.println(e-b);

    }
}


