package com.lw.datacrab.db.criterion;

/**
 * 构建数据库查询条件
 *
 * @author lw
 * @date 2015-4-17 11:29:26
 */
public class DataCrabDb {

    /**
     * @param entityClass
     * @return
     */
    public static DbQueryCriterion build(Class<?> entityClass) {
        return new DbQueryCriterion(entityClass, false);
    }

    /**
     * @param entityClass
     * @param transZero   是否把数字0(包括0.0..)转换成字符串"0"
     * @return
     */
    public static DbQueryCriterion build(Class<?> entityClass, boolean transZero) {
        return new DbQueryCriterion(entityClass, transZero);
    }

    /**
     * @param entityClass
     * @param tableAlias
     * @return
     */
    public static DbQueryCriterion build(Class<?> entityClass, String tableAlias) {
        return new DbQueryCriterion(entityClass, tableAlias, false);
    }

    /**
     * @param entityClass
     * @param tableAlias
     * @param transZero   是否把数字0(包括0.0..)转换成字符串"0"
     * @return
     */
    public static DbQueryCriterion build(Class<?> entityClass, String tableAlias, boolean transZero) {
        return new DbQueryCriterion(entityClass, tableAlias, transZero);
    }

    /**
     * @param entity
     * @return
     */
    public static DbQueryCriterion build(Object entity) {
        return new DbQueryCriterion(entity, false);
    }

    /**
     * @param entity
     * @param transZero 是否把数字0(包括0.0..)转换成字符串"0"
     * @return
     */
    public static DbQueryCriterion build(Object entity, boolean transZero) {
        return new DbQueryCriterion(entity, transZero);
    }

    /**
     * @param entity
     * @param tableAlias
     * @return
     */
    public static DbQueryCriterion build(Object entity, String tableAlias) {
        return new DbQueryCriterion(entity, tableAlias, false);
    }

    /**
     * @param entity
     * @param tableAlias
     * @param transZero  是否把数字0(包括0.0..)转换成字符串"0"
     * @return
     */
    public static DbQueryCriterion build(Object entity, String tableAlias, boolean transZero) {
        return new DbQueryCriterion(entity, tableAlias, transZero);
    }

}
