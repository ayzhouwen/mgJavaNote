package com.threeparty.guice.demo;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 参考:3分钟带你了解轻量级依赖注入框架Google Guice【享学Java】
 * https://cloud.tencent.com/developer/article/1605665
 */
@Slf4j
public class TestGuice {
    @Inject
    private Animal animal;
    @Inject
    private List<Animal> animalList;
    @Named("日本")
    @Inject Person person;
    @Named("中国")
    @Inject Person person1;

    /**
     * 这便是一个最简单的使用Guice来实现依赖注入的示例，和Spring的ApplicationContext还蛮像的有木有。针对此实例的输出，你应该也意识到此处一个非常非常不一样的不同：默认是多例的（每次get/注入的都是不同的实例）。
     */
    private void  test1(){
        Injector injector = Guice.createInjector(new MainModule());
        // 为当前实例注入容器内的对象
        injector.injectMembers(this);

        System.out.println(animal);
        //注意: getInstance 是重新生成一个实例,不管类定义是否为单例
        System.out.println(injector.getInstance(Animal.class));
        System.out.println(injector.getInstance(Animal.class));
        animal.run();

        animalList.forEach(e->{
            log.info("测试泛型list注入:"+e);
            e.run();
        });

        System.out.println(injector.getInstance(Person.class));
        System.out.println(injector.getInstance(Person.class));
        System.out.println(injector.getInstance(Person.class));
        person.run();
    }
    public static void main(String[] args) {
        TestGuice tg=new TestGuice();
        tg.test1();
    }
}