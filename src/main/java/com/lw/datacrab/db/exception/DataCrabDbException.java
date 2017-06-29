package com.lw.datacrab.db.exception;

/**
 * 数据库操作异常
 *
 * @author lw
 * @date 2015-4-17 11:24:07
 */
public class DataCrabDbException extends Exception {

    public DataCrabDbException(String msg) {
        super(msg);
    }

    public DataCrabDbException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
