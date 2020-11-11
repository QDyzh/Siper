package com.util;

public class StringUtil {
    /**
     * 判空
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }

    /**
     * 获取非Null的值
     * @param data
     * @return
     */
    public static String notNullValue(String data) {return data == null ? "" : data; }
}
