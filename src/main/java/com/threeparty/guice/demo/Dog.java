package com.threeparty.guice.demo;

import com.google.inject.Singleton;

/**
 * 如果加上此参数则getInstance出来的是一个单例
 */
//@Singleton
public class Dog implements Animal {
    @Override
    public void run() {
        System.out.println("dog run...");
    }
}