package com.threeparty.guice.demo;

import com.google.inject.Singleton;

/**
 * 如果加上此参数,getInstance则是一个单例
 */
//@Singleton
public class Cat implements Animal {
    @Override
    public void run() {
        System.out.println("cat run...");
    }
}