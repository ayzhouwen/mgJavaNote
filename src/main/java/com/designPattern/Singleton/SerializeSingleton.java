package com.designPattern.Singleton;


import java.io.*;

//<<java程序性能优化第二章单例>>序列化和反序列化破坏单例
public class SerializeSingleton {
public  static  void test() throws IOException, ClassNotFoundException {
    SerSingleton s1=null;
    SerSingleton s=SerSingleton.getInstance();
    //先将实例串行话到文件
    String filename="SerSingletion.txt";
    FileOutputStream fos=new FileOutputStream(filename);
    ObjectOutputStream oos=new ObjectOutputStream(fos);
    oos.writeObject(s);
    oos.flush();
    oos.close();
    //从文件读取原有的单例类
    FileInputStream fis=new FileInputStream(filename);
    ObjectInputStream ois=new ObjectInputStream(fis);
    s1=(SerSingleton)ois.readObject();
    System.out.println("s1:"+s1+",s:"+s+",s==s1:"+(s==s1));
}

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        test();
    }
}

class SerSingleton implements Serializable{
        String name;
        private SerSingleton(){
            System.out.println("Singleton is create"); //真实场景创建单例可能会比较慢
            name="SerSingleton";
        }
        private static SerSingleton instance=new SerSingleton();
        public static  SerSingleton getInstance(){
            return  instance;
        }

        public static void createString(){
            System.out.println("cretaeString in Singleton");
        }

        //组织生成新的实例,总是返回当前对象,注意如果去掉这句话那么序列化和反序列化后无法实现单例
        private Object readResolve(){
            return instance;
        }

}