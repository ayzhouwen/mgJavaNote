package com.nio.buffer;

import java.nio.CharBuffer;

public class TestCharBuffer {
    public  static  void test(){
        char [] myArray=new char [100];
        CharBuffer charBuffer=CharBuffer.wrap(myArray);
        charBuffer.put('权');
        myArray=null;//注意此处并不会清空char[100],注意哦,因为charBuffer还在引用这块内存,所以gc是不会释放掉的
        System.out.println(charBuffer.hasArray());
        charBuffer.put('力');
        System.out.println(charBuffer.array());
        System.out.println(charBuffer.arrayOffset());
    }

    public static void main(String[] args) {
        test();
    }
}
