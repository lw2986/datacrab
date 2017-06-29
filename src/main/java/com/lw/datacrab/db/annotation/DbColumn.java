package com.lw.datacrab.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库表字段注解
 *
 * @author lw
 * @date 2015-4-17 11:19:08
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DbColumn {

    /**
     * 列名
     *
     * @return
     */
    String value();

}
