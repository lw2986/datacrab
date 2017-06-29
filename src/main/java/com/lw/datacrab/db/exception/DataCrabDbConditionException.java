package com.lw.datacrab.db.exception;

/**
 * 构建查询条件异常
 *
 * @author lw
 * @date 2015-4-17 11:24:07
 */
public class DataCrabDbConditionException extends DataCrabDbException {

    public DataCrabDbConditionException(String msg) {
        super(msg);
    }

    public DataCrabDbConditionException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
