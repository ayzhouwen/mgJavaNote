package com.io.serialize.demo1;

import lombok.Data;

import java.io.Serializable;

/**
 * 参考:https://www.cnblogs.com/doudouxiaoye/p/5774327.html
 */

/**
 * <p>ClassName: Person<p>
 * <p>Description:测试对象序列化和反序列化<p>
 */
@Data
public class Person implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -5809782578272943999L;
    private int age;
    private String name;
    private String sex;
    private String abcd;


}