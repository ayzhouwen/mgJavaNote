package com.threeparty.guice.demo;

import com.google.inject.Singleton;

@Singleton
public class Person  implements Animal {
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private String country="暂无国籍";
    @Override
    public void run() {
        System.out.println("Person run...我的国家:"+country);
    }
}
