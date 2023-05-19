package com.meConcurrent.jdk8;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 函数式接口测试
 * 参考：
 * https://developer.aliyun.com/article/988949#slide-10
 */
@Slf4j
public class FunInterfaceTest {
    /**
     * 自定义接口函数测试
     * @param f1
     */
    void testFun1(F1 f1){
        f1.go("97k");
        f1.goDef1(null);
        f1.goDef2(null);
        F1.staticGo1();
    }

    /**
     * demo上的jdk自带的函数接口
     */
    void testJDKFun(){
        //方法引用
        Consumer consumer = System.out::println;
        consumer.accept("hello function");

        //消费型函数接口
        String[] strings = {"保时捷,白色", "法拉利,红色"};
        Consumer<String> consumer1 = s -> System.out.print("车名："+s.split(",")[0]);
        Consumer<String> consumer2 = s -> System.out.println("-->颜色："+s.split(",")[1]);
        for (String e : strings) {
            consumer1.andThen(consumer2).accept(e);
        }
        //提供型函数接口
        Supplier<String> supplier = () -> "我要变的很有钱";
        System.out.println(supplier.get());

        //函数型函数接口
        Function<Integer, Integer> function1 = e -> e * 6;
        System.out.println(function1.apply(2));

        //逆向执行
        Function<Integer, Integer> function2 = e -> e * 2;
        Function<Integer, Integer> function3 = e -> e * e;
        //执行顺序function3->function2
        Integer apply2 = function2.compose(function3).apply(3);
        System.out.println(apply2);


        //顺序执行
        Function<Integer, Integer> function4 = e -> e * 2;
        Function<Integer, Integer> function5 = e -> e * e;
        //执行顺序function4->function5
        Integer apply3 = function4.andThen(function5).apply(3);
        System.out.println(apply3);

        //输入什么就返回什么
        Function<Integer, Integer> identity = Function.identity();
        Integer apply = identity.apply(66);
        System.out.println(apply);

        //断言 函数接口
        Predicate<Integer> predicate = t -> t > 0;
        boolean testR = predicate.test(1);
        System.out.println(testR);


        //相等判断
        predicate = t -> t > 0;
        Predicate t2 = Predicate.isEqual("123");
        System.out.println(t2.test("123"));

        //and判断
        Predicate<String> predicate1=s->s!=null&&s.length()>0;
        Predicate<String> predicate2= Objects::nonNull;
        boolean andTestR=predicate1.and(predicate2).test(null);
        System.out.println(andTestR);
        //取反测试
        System.out.println(predicate1.negate().test(null));




    }
    public static void main(String[] args) {
        FunInterfaceTest test=new FunInterfaceTest();
//        test.testFun1(p->log.info("拉姆达F1，参数:{}",p));
        test.testJDKFun();
    }
}

/**
 * 自定义函数接口实现，只能有有一个普通方法定义
 * @param <T>
 */
@FunctionalInterface
interface  F1<T>{
    void  go(T t);
    /**
     *  默认方法1
     */

    default void goDef1(T t){
        System.out.println("这是F1的默认方法1");
    };
    /**
     * 默认方法2
     */
    default void goDef2(T t){
        System.out.println("这是F1的默认方法2");
    };

    /**
     * 默认方法3 ，返回的是一个拉姆达
     * @param t
     * @return
     */
    default F1<T> goDef3(T t){
        return p->{
            System.out.println("这是F1的默认方法3,");
        };
    }

    static void staticGo1(){
        System.out.println("这是F1的静态方法1");
    }
}