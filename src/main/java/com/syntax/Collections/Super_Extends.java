package com.syntax.Collections;

/**
 * Created by Administrator on 2017/6/3.
 */
//测试集合类泛型中Super与Extends使用
//https://www.zhihu.com/question/20400700
//http://bbs.csdn.net/topics/390054281(一楼回答的很经典)
public class Super_Extends {
    public static void main(String[] args) {
       // Plate<Fruit> p=new Plate<Apple>(new Apple()); //编译报错
         Plate<? extends Fruit> p=new Plate<Apple>(new Apple()); //定义继承树的上界即(顶点),set函数里参数是能是object,只能get
        Plate<? super Apple> p1=new Plate<Fruit>(new Fruit()); //当时get函数里参数只能是object
        p1.set((Apple)new Fruit());

    }
}
class Plant{}
class Fruit extends Plant {}
class Apple extends Fruit {}


class Plate<T>{
    private T item;
    public Plate(T t){item=t;}
    public void set(T t){item=t;}
    public T get(){return item;}
    public <E> E getKey(E key){return  key;}
}