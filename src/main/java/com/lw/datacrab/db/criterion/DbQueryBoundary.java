package com.lw.datacrab.db.criterion;

import java.io.Serializable;

/**
 * 查询边界
 *
 * @author lw
 * @date 2015-4-17 11:26:19
 */
public class DbQueryBoundary implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    //下边界
    private Object minVal;

    //上边界
    private Object maxVal;

    public DbQueryBoundary(Object min, Object max) {
        this.minVal = min;
        this.maxVal = max;
    }

    public Object getMinVal() {
        return this.minVal;
    }

    public Object getMaxVal() {
        return this.maxVal;
    }

    @Override
    protected DbQueryBoundary clone() {
        try {
            return (DbQueryBoundary) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "DbQueryBoundary{" +
                "minVal=" + minVal +
                ", maxVal=" + maxVal +
                '}';
    }
}
