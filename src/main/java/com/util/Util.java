package com.util;

import com.annotation.ExcelTitle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    public static <T> List<Map<String, Object>> getCommonValueForClass(T data) throws Exception {
        String label;
        Object value;
        List<Map<String, Object>> resultList = new ArrayList<>();

        Class cl = data.getClass();
        Field[] fields = cl.getDeclaredFields();
        for(Field f: fields) {
            Map<String, Object> res = new HashMap<>();
            label = f.getName();
            Method m = cl.getMethod("get"+label.substring(0,1).toUpperCase() + label.substring(1));
            value = m.invoke(data);
            // 存在标题注解
            if(f.isAnnotationPresent(ExcelTitle.class)) {
                label = f.getAnnotation(ExcelTitle.class).value();
            }
            res.put(label, value);
            resultList.add(res);
        }
        return resultList;
    }
}
