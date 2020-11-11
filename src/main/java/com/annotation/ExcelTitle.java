package com.annotation;

import java.lang.annotation.*;

/**
 * 创建Excel时,列名
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelTitle {
    String value();
}
