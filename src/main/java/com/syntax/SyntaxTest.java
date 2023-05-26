package com.syntax;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author admin
 */
//临时随手代码测试类,可以忽略次类d


class MyDog<A extends String,B,C,D,E,F>{
    A getDog(){
       return (A) "AAAAA";
    }
}
@Slf4j
class SyntaxTest {
    public static void test(List<?> list) {
        Object o = list.get(1);
    }

    public static void main(String[] args) throws InterruptedException {
        Consumer<String> consumer1 = s -> System.out.print("车名："+s.split(",")[0]);
        Consumer<String> consumer2 = s -> System.out.println("-->颜色："+s.split(",")[1]);

        String[] strings = {"保时捷,白色", "法拉利,红色"};
        for (String string : strings) {
            consumer1.andThen(consumer2).accept(string);
        }

        Function<Integer, Integer> function1 = e -> e * 2;
        Function<Integer, Integer> function2 = e -> e * e;
        Function<Integer, Integer> function3 =new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer+integer;
            }
        };

                Integer apply2 = function1.compose(function2).apply(3);
        System.out.println(apply2);
        System.out.println(function3.apply(5));


    }

    private static  DateTime getDateTime(String time ) {
        return time.compareTo( DateTime.now().toString( "HH:mm" ) ) >= 0
                ? DateUtil.parseDateTime( DateTime.now().toDateStr() + " " + time + ":00" )
                : DateUtil.parseDateTime( DateUtil.offsetDay( DateUtil.parseDateTime( DateTime.now().toDateStr() + " " + time + ":00" ), 1 )
                .toDateStr() + " " + time + ":00" );
    }

}



@Getter
enum DeviceTypeEnum {
    cabinet("0001", "机柜"),
    assetBar("0002", "资产条"),
    ODF("0004", "ODF"),
    DDF("0005", "DDF"),
    VDF("0006", "VDF"),
    NDF("0007", "NDF"),
    PDU("0008", "PDU");

    private String title;
    private String code;

    DeviceTypeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public static String getTitleBycode(int code) {
        DeviceTypeEnum[] values = DeviceTypeEnum.values();
        for (DeviceTypeEnum value : values) {
            if (value.code.equals(code)) {
                return value.title;
            }
        }
        return "UNKNOWN";
    }

    public static DeviceTypeEnum getEnumByCode(String code) {
        return Arrays.stream(DeviceTypeEnum.values()).filter(e -> e.code.equals(code)).findFirst().orElse(null);
    }
}

@Data
class Person implements Serializable {

    public Integer age;
    public String name;

    public Person next;
}

enum Color {
    red("红色", 1), yellow("黄色", 2), green("绿色", 3);
    // 成员变量
    private String name;
    private int index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    // 构造方法
    private Color(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (Color c : Color.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    //覆盖方法


    @Override
    public String toString() {
        return this.index + "_" + this.name;
    }


}






