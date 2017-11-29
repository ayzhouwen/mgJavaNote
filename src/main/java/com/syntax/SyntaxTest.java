package com.syntax;

import com.syntax.Interface.Const;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 */
//临时随手代码测试类,可以忽略次类
class SyntaxTest {

    static enum Type {

        /**
         * 普通订单
         */
        general,

        /**
         * 兑换订单
         */
        exchange,

        /**
         * 福袋订单
         */
        fudai
    }

    static int add(int n) {
        if (n <= 1) {
            return 1;
        } else {
            return add(n - 1) + n;
        }
    }


    static int recur(int n) {
        if (n <= 1) {
            return 1;
        } else {
            return recur(n - 1) * n;
        }
    }

    volatile int sk[];

    //   private static final Unsafe unsafe = Unsafe.getUnsafe();
    public static void main(String[] args) throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

//        Map<String,Object> map=new HashedMap();
//        A a=new A(3,"abc"); map.put("a",a);
//        A b=new A(4,"abce");map.put("b",b);
//        A c=new A(4,"abcea省道"); map.put("c",c);
//        String json= JSON.toJSONString(map);
//
//        Map map1=(Map)JSON.parse(json);

//        Socket socket = new Socket("127.0.0.1", 10101);
//        // 消息内容
//        String message = "hello";
//        byte[] bytes = message.getBytes();
//        // 构造字节数组，长度为（4+内容长度）
//        // 其中4个字节长度字段是int为4个字节
//        ByteBuffer buffer = ByteBuffer.allocate(4 + bytes.length);
//        // 设置长度字段（仅仅是内容的长度）
//        buffer.putInt(bytes.length);
//        // 设置内容
//       // buffer.put(bytes);
//        buffer.get();
//        buffer.get(2);
//        buffer.get(4);
//
//        byte[] array = buffer.array();
//        for (int i = 0; i < 20; i++) {
//            socket.getOutputStream().write(array);
//        }
//        socket.close();

        System.out.println(2<<3);

        System.out.println(Const.ip+","+Const.port);
    }


}


class A {
    protected int id;
    public String name;
    public A( int id,String name){
        this.id=id;
        this.name=name;
    }
}

class B extends A {

    public B(int id, String name) {
        super(id, name);
    }
}

class SuperClass {
    static List<Integer> getlist() {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        return list;
    }

    List<Integer> getChangeList() {
        List<Integer> list = SuperClass.getlist();
        for (Integer node : list) {
            list.set(0, 444);

        }
        List<SuperClass> sulist = new ArrayList<>();
        SuperClass sc1 = new SuperClass(3);
        SuperClass sc2 = new SuperClass(4);
        sulist.add(sc1);
        sulist.add(sc2);

        for (SuperClass node : sulist) {
            node.n += 100;
        }
        return list;

    }

    public int n;

    //SuperClass(){
    //    System.out.println("SuperClass()");
    //}
    SuperClass(int n) {
        System.out.println("SuperClass(int n)");
        this.n = n;
    }
}

class SubClass extends SuperClass {
    private int n;

    SubClass() {
        super(300);
        System.out.println("SuperClass");

    }

    SubClass(int n) {
        super(n);
        System.out.println("SubClass(int n):" + n);
        this.n = n;
    }


}

