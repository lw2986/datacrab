package com.lw.datacrab.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库表注解
 *
 * @author lw
 * @date 2015-4-17 11:17:57
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DbTable {

    /**
     * 表名
     *
     * @return
     */
    String value();

}
