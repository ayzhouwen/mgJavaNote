package com.syntax;

public class IntegerTest {
    public static void main(String[] args) {
        IntegerTrap1();
    }

    /**
     *  Integer和Long 的坑,虽然有些老生常谈,但是是提示自己不要忘记,
     */
    public static void IntegerTrap1(){
        Integer a = 100;
        Integer b = 100;

        //-128到127直接走内存缓存,所以结果为true
        System.out.println(a == b);
        Integer i = 100001;
        Integer j = 100001;

        //不走缓存创建新对象,所以结果为false
        System.out.println(i == j);
        //暂时理解为拆箱操作,有时间跟踪class字节码
        System.out.println(i >= a);
        //拆箱操作,有时间跟踪class字节码
        System.out.println(i >= j);
    }
}
