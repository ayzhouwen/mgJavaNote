package com.jvm.sj._02;


//<<实战java虚拟机>>2.4 模拟局部变量堆栈溢出,可以在idea中设置运行-Xss128k -Xss5000k可以看出区别
public class TestStackDeep2 {
    private static int count=0;
    public static void recursion(){
          count++;
          recursion();
    }

    public static void recursion(long a,long b,long c){
        count++;
        long e=1,f=2,g=3,h=4,i=5,k=6,q=7,x=8,y=9,z=10;
        recursion(a,b,c);
    }

    public static void main(String[] args) {
        try {
          //  recursion(0,0,0);
            recursion();
        } catch (Throwable e) { //注意此处不能是Except,否则拦截不到异常
            System.out.println("deep of calling ="+count);
            e.printStackTrace();
        }
    }

}
