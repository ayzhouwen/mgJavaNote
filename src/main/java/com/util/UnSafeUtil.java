package com.util;


import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnSafeUtil {
    public static final Unsafe unsafe = getUnsafe();
    static sun.misc.Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return  (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
