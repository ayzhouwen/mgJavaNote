package com.jvm.my;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * new Byte[]与new Long[]和new Person[]占用的内存空间初始化时候一直是new  byte[]的4倍
 * jvm非基本类数组内部存的是元素内存地址（4个字节），就像c的指针一样
 * 对象存指针可以理解，像包装类Byte存指针血亏，增加了3倍的存储量，所以能用基本类就用基本类
 */
@Slf4j
public class ByteSizeTest {
    static byte [] bArray1=null;
    static byte [] bArray2=null;
    static byte [] bArray3=null;
    static Byte [] BArray1=null;
    public static void main(String[] args) {
        Byte  b1=127;
        byte  b2=127;
        Long  lg1=127L;
        log.info("一个Byte大小:{}", ClassLayout.parseInstance(b1).toPrintable());
        log.info("一个Byte大小:{}", ClassLayout.parseInstance(b2).toPrintable());
        log.info("一个Long大小:{}", ClassLayout.parseInstance(lg1).toPrintable());
        log.info("*****************************************");
        byte b3 [] =new  byte[1000];
        Byte  b4[]=new Byte[1000];
        Long[] l1=new Long[1000];
        Person[] p1=new Person[1000];
        log.info("一个byte数组大小:{}", ClassLayout.parseInstance(b3).toPrintable());
        log.info("一个Byte数组大小:{}", ClassLayout.parseInstance(b4).toPrintable());
        log.info("一个Long数组大小:{}", ClassLayout.parseInstance(l1).toPrintable());
        log.info("一个Person数组大小:{}", ClassLayout.parseInstance(p1).toPrintable());
    }
}

class Person  {

    public Long age;
    public String name;
    public Long id;
}
