package com.syntax;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.util.MyDateUtil;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;
import sun.reflect.CallerSensitive;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author admin
 */
//临时随手代码测试类,可以忽略次类d
@Slf4j
class SyntaxTest {
    public static void test(List<?> list) {
        Object o = list.get(1);
    }

    public static void main(String[] args) throws InterruptedException {
        long s =System.currentTimeMillis();
        Thread mainT=Thread.currentThread();
        LockSupport.unpark(mainT);
        new  Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LockSupport.unpark(mainT);
        }).start();
        LockSupport.parkNanos(1000000000L*60);
        log.info(MyDateUtil.execTime("测试纳秒",s));


    }

    private static  DateTime getDateTime(String time ) {
        return time.compareTo( DateTime.now().toString( "HH:mm" ) ) >= 0
                ? DateUtil.parseDateTime( DateTime.now().toDateStr() + " " + time + ":00" )
                : DateUtil.parseDateTime( DateUtil.offsetDay( DateUtil.parseDateTime( DateTime.now().toDateStr() + " " + time + ":00" ), 1 )
                .toDateStr() + " " + time + ":00" );
    }

    @CallerSensitive
    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            return null;
        }
    }


    private static class Node<E> {
        volatile E item;
        volatile Node<E> next;

        /**
         * Constructs a new node.  Uses relaxed write because item can
         * only be seen after publication via casNext.
         */
        Node(E item) {
            UNSAFE.putObject(this, itemOffset, item);
        }

        boolean casItem(E cmp, E val) {
            return UNSAFE.compareAndSwapObject(this, itemOffset, cmp, val);
        }

        void lazySetNext(Node<E> val) {
            UNSAFE.putOrderedObject(this, nextOffset, val);
        }

        boolean casNext(Node<E> cmp, Node<E> val) {
            return UNSAFE.compareAndSwapObject(this, nextOffset, cmp, val);
        }

        // Unsafe mechanics

        private static final sun.misc.Unsafe UNSAFE;
        private static final long itemOffset;
        private static final long nextOffset;

        static {
            try {
                UNSAFE = getUnsafe();
                Class<?> k = Node.class;
                itemOffset = UNSAFE.objectFieldOffset
                        (k.getDeclaredField("item"));
                nextOffset = UNSAFE.objectFieldOffset
                        (k.getDeclaredField("next"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
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






