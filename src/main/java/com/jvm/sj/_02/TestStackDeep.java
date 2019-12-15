package com.jvm.sj._02;


//<<实战java虚拟机>>2.3 模拟堆栈溢出,可以在idea中设置运行-Xss128k -Xss5000k可以看出区别
public class TestStackDeep {
    private static int count=0;
    public static void recursion(){
        count++;
          recursion();
    }

    public static void main(String[] args) {
        try {
            recursion();
        } catch (Throwable e) { //注意此处不能是Except,否则拦截不到异常
            System.out.println("deep of calling ="+count);
            e.printStackTrace();
        }
    }

}
