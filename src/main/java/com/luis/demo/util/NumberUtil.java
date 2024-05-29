package com.luis.demo.util;

public class NumberUtil {

    public static Long toLong(Object any) {
        if (any instanceof Number) {
            return ((Number) any).longValue();
        } else if (any instanceof String) {
            return Long.valueOf(((String) any).replaceAll("[^\\d.]", ""));
        }
        return 0L;
    }
}
