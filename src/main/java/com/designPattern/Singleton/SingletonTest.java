package com.designPattern.Singleton;
//<<java程序性能优化第二章单例>>,同时也可以参考http://wuchong.me/blog/2014/08/28/how-to-correctly-write-singleton-pattern/
//饿汉模式无法实现懒加载

//总结:不考虑懒加载直接恶汉模式,考虑懒加载推荐静态内部类,其次双重锁检测
public class SingletonTest {

    public static void main(String[] args) {
        //Singleton.createString();
        testSingleton();
    }

    //测试各个单例的性能
    public static void  testSingleton(){
        for (int i=0;i<100;i++){
            new  Thread(new Runnable() {
                @Override
                public void run() {
                    long beginTime=System.currentTimeMillis();
                    for (int j=0;j<1000000;j++){
                        //Singleton.getInstance();
                     //   LazySingleton.getInstance();
                     //   StaticSingleton.getInstance();
                      //  DoubleCheckSingleton.getInstance();
                        EnumSingleton.INSTANCE.getInstance();
                    }

                    System.out.println(Thread.currentThread().getName()+  ",spend:"+(System.currentTimeMillis()-beginTime));
                }
            }).start();

        }
    }
}

 class Singleton {
    private Singleton(){
        System.out.println("Singleton is create"); //真实场景创建单例的过程可能会有点慢
    }

    private static  Singleton instance=new Singleton();

    public static Singleton getInstance(){
        return  instance;
    }
    //这是模拟单例类扮演其他角色,即还有其他功能,执行此代码时会引起单例私有构造函数的调用
    public static void createString(){
        System.out.println("createString in Singleton");
    }


}

//懒加载单例,注意这种实现方式并发性能很低,远远大于饿汉模式
class LazySingleton{
    private LazySingleton(){
        System.out.println("LazySingleton is create"); //真实场景创建单例的过程可能会比较慢
    }
    private static LazySingleton instance=null;
    public static synchronized  LazySingleton getInstance(){
        if (instance==null){
            instance=new LazySingleton();
        }
        return  instance;
    }
}

//内部类实现单例并发上有很大的优势,又是懒加载,所以被很多人推荐推荐
class  StaticSingleton{
    private  StaticSingleton(){
        System.out.println("StaicSingletion is create");
    }
    private static class SingletonHolder{
        private   static StaticSingleton  instance=new StaticSingleton();
    }

    public static StaticSingleton getInstance(){
        return  SingletonHolder.instance;
    }
}

//双重检测实现单例,可以实现懒加载,高并发性能不错,但是还是没有内部类效率高,差几毫秒,可能都是锁竞争造成的

class  DoubleCheckSingleton{
    private DoubleCheckSingleton(){};
    private  static  volatile   DoubleCheckSingleton instance=null;
    public  static  DoubleCheckSingleton getInstance() {
        if (instance==null){
            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
            return  instance;
    }
}

//枚举实现单例,性能也不错.跟静态内部类接近
class Resource{
}

 enum  EnumSingleton {
    INSTANCE;
    private Resource instance;
     EnumSingleton() {
        instance = new Resource();
    }
    public Resource getInstance() {
        return instance;
    }
}
