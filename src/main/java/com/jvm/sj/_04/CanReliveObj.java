package com.jvm.sj._04;
/**
 * <<实战java虚拟机>>4.3.1 对象触及性-复活
 */
public class CanReliveObj {
    public static CanReliveObj obj;
    //书上说finalize是很糟糕的模式,不推荐在这里释放资源,因为这个函数只要用一次,一般是留给系统调用的,推荐使用try catch finally进行资源的释放
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("CanReliveObj finalize called");
        obj =this;
    }

    @Override
    public String toString() {
        return "I am CanReliveObj";
    }

    public static void main(String[] args) throws InterruptedException {
        obj=new CanReliveObj();
        obj=null;
        System.gc();
        Thread.sleep(1000);
        if (obj==null){
            System.out.println("obj 是null");
        }else {
            System.out.println("obj 可用");
        }
        System.out.println("第 2次gc");
        obj=null;
        System.gc();
        if (obj==null){
            System.out.println("obj 是null");
        }else {
            System.out.println("obj 可用");
        }

    }
}
