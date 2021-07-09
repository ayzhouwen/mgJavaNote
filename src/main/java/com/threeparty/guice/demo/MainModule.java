package com.threeparty.guice.demo;

import com.google.inject.*;
import com.google.inject.name.Named;

import java.util.Arrays;
import java.util.List;

/**
 * 任何需要依赖注入的资源，只有先实现了绑定才能注入，本处开始介绍它的绑定方式。
 */
public class MainModule extends AbstractModule {
    @Override
    protected void configure() {
        //非单例模式绑定
        bind(Animal.class).to(Dog.class);
        //绑定时指定单例
//        bind(Animal.class).to(Dog.class).in(Singleton.class);
        //绑定时指定单例,并且是一个指定对的对象
//        bind(Animal.class).toInstance(new Dog());
        bind(new TypeLiteral<List<Animal>>(){}).toInstance(Arrays.asList(new Dog(),new Cat()));
    }

    @Provides
    @Named("中国")
    public Person getPerson(){
        Person person=new Person();
        person.setCountry("中国");
        return  person;
    }

    @Named("日本")
    @Provides
    public Person getPerson2(){
        Person person=new Person();
        person.setCountry("日本");
        return  person;
    }
}