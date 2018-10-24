package com.syntax.dynamic.reflex;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

//反射,构造函数 ,很好的例子
//来源https://blog.csdn.net/qq1130141391/article/details/12281113
public class Teacher {
    private String name;
    public Integer age;

    public Teacher(){
        System.out.println("empty constructor invoked");
    }

    public Teacher(String name,Integer age){
        this.name = name;
        this.age = age;
        System.out.println("Str Int constructor invoked" + " , " +
                "name = " + this.name + " , age = " + this.age);
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Integer getAge() {
        return age;
    }


    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "Teacher [name=" + name + ", age=" + age + "]";
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ClassLoader classLoader = Teacher.class.getClassLoader();
        System.out.println("1 classLoader = " + classLoader);
        classLoader = Class.class.getClassLoader();
        System.out.println("2 classLoader = " + classLoader);
        classLoader = new Teacher().getClass().getClassLoader();
        System.out.println("3 classLoader = " + classLoader);
        Class clazz = classLoader.loadClass("com.syntax.dynamic.reflex.Teacher");
        System.out.println("4 clazz = " + clazz);
        clazz = Class.forName("com.syntax.dynamic.reflex.Teacher");
        System.out.println("5 clazz = " + clazz);

        Constructor[] constructors = clazz.getConstructors();
        int i = 6;
        for(Constructor c : constructors){
            System.out.println(i + " " + c);
            i++;
        }

        Constructor cttEmpty = clazz.getConstructor(new Class[]{});
        Object objEmpty = cttEmpty.newInstance(new Object[]{});

        Constructor cttParamsStrInt = clazz.getConstructor(new Class[]{String.class,Integer.class});
        Object objPramsStrint = cttParamsStrInt.newInstance(new Object[]{"lihuiming",45});

    }

}
