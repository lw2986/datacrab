package com.lw.datacrab.db.criterion;

import com.lw.datacrab.db.annotation.DbColumn;
import com.lw.datacrab.db.annotation.DbTable;
import com.lw.datacrab.db.exception.DataCrabDbConditionException;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/**
 * 数据库查询模板
 *
 * @author lw
 * @date 2015-4-17 11:29:26
 */
public class DbQueryCriterion implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    /**
     * 构造方法
     *
     * @param entityClass
     */
    public DbQueryCriterion(Class<?> entityClass) {
        this.entityClass = entityClass;
        alias = getTableName();
        initColumnMap(entityClass);
    }

    /**
     * 构造方法
     *
     * @param entityClass
     * @param tableAlias
     */
    public DbQueryCriterion(Class<?> entityClass, String tableAlias) {
        this.entityClass = entityClass;
        alias = tableAlias;
        initColumnMap(entityClass);
    }

    /**
     * 构造方法
     *
     * @param entityClass
     * @param transZero
     */
    public DbQueryCriterion(Class<?> entityClass, boolean transZero) {
        this.entityClass = entityClass;
        alias = getTableName();
        initColumnMap(entityClass);
        this.transZero = transZero;
    }

    /**
     * 构造方法
     *
     * @param entityClass
     * @param tableAlias
     * @param transZero
     */
    public DbQueryCriterion(Class<?> entityClass, String tableAlias, boolean transZero) {
        this.entityClass = entityClass;
        alias = tableAlias;
        initColumnMap(entityClass);
        this.transZero = transZero;
    }

    /**
     * 构造方法
     *
     * @param entity
     */
    public DbQueryCriterion(Object entity) {
        this.entityClass = entity.getClass();
        alias = getTableName();
        initColumnMap(entity.getClass());
        setEntity(entity);
    }

    /**
     * 构造方法
     *
     * @param entity
     * @param tableAlias
     */
    public DbQueryCriterion(Object entity, String tableAlias) {
        this.entityClass = entity.getClass();
        alias = tableAlias;
        initColumnMap(entity.getClass());
        setEntity(entity);
    }

    /**
     * 构造方法
     *
     * @param entity
     * @param transZero
     */
    public DbQueryCriterion(Object entity, boolean transZero) {
        this.entityClass = entity.getClass();
        alias = getTableName();
        initColumnMap(entity.getClass());
        setEntity(entity);
        this.transZero = transZero;
    }

    /**
     * 构造方法
     *
     * @param entity
     * @param tableAlias
     * @param transZero
     */
    public DbQueryCriterion(Object entity, String tableAlias, boolean transZero) {
        this.entityClass = entity.getClass();
        alias = tableAlias;
        initColumnMap(entity.getClass());
        setEntity(entity);
        this.transZero = transZero;
    }

    //---------------------------------------------------------------------------------------

    /**
     * 字段大于值
     *
     * @param fieldName
     * @param val
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion gt(String fieldName, Object val) throws DataCrabDbConditionException {
        return this.gt(transZero, fieldName, val);
    }

    /**
     * 字段大于值
     *
     * @param fieldName
     * @param val
     * @param transZero
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion gt(boolean transZero, String fieldName, Object val) throws DataCrabDbConditionException {
        String columnName = col(fieldName);
        if (gtField == null)
            gtField = new HashMap<String, Object>();
        if (transZero)
            val = transZero(val);
        gtField.put(columnName, val);
        return this;
    }

    /**
     * 字段大于或等于值
     *
     * @param fieldName
     * @param val
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion gte(String fieldName, Object val) throws DataCrabDbConditionException {
        return this.gte(transZero, fieldName, val);
    }

    /**
     * 字段大于或等于值
     *
     * @param fieldName
     * @param val
     * @param transZero
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion gte(boolean transZero, String fieldName, Object val) throws DataCrabDbConditionException {
        String columnName = col(fieldName);
        if (gteField == null)
            gteField = new HashMap<String, Object>();
        if (transZero)
            val = transZero(val);
        gteField.put(columnName, val);
        return this;
    }

    /**
     * 字段小于值
     *
     * @param fieldName
     * @param val
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion lt(String fieldName, Object val) throws DataCrabDbConditionException {
        return this.lt(transZero, fieldName, val);
    }

    /**
     * 字段小于值
     *
     * @param fieldName
     * @param val
     * @param transZero
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion lt(boolean transZero, String fieldName, Object val) throws DataCrabDbConditionException {
        String columnName = col(fieldName);
        if (ltField == null)
            ltField = new HashMap<String, Object>();
        if (transZero)
            val = transZero(val);
        ltField.put(columnName, val);
        return this;
    }

    /**
     * 字段小于或等于值
     *
     * @param fieldName
     * @param val
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion lte(String fieldName, Object val) throws DataCrabDbConditionException {
        return this.lte(transZero, fieldName, val);
    }

    /**
     * 字段小于或等于值
     *
     * @param fieldName
     * @param val
     * @param transZero
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion lte(boolean transZero, String fieldName, Object val) throws DataCrabDbConditionException {
        String columnName = col(fieldName);
        if (lteField == null)
            lteField = new HashMap<String, Object>();
        if (transZero)
            val = transZero(val);
        lteField.put(columnName, val);
        return this;
    }

    /**
     * 字段等于值
     *
     * @param fieldName
     * @param val
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion eq(String fieldName, Object val) throws DataCrabDbConditionException {
        return this.eq(transZero, fieldName, val);
    }

    /**
     * 字段等于值
     *
     * @param fieldName
     * @param val
     * @param transZero
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion eq(boolean transZero, String fieldName, Object val) throws DataCrabDbConditionException {
        String columnName = col(fieldName);
        if (eqField == null)
            eqField = new HashMap<String, Object>();
        if (transZero)
            val = transZero(val);
        eqField.put(columnName, val);
        return this;
    }

    /**
     * 字段不等于值
     *
     * @param fieldName
     * @param val
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion neq(String fieldName, Object val) throws DataCrabDbConditionException {
        return this.neq(transZero, fieldName, val);
    }

    /**
     * 字段不等于值
     *
     * @param fieldName
     * @param val
     * @param transZero
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion neq(boolean transZero, String fieldName, Object val) throws DataCrabDbConditionException {
        String columnName = col(fieldName);
        if (neqField == null)
            neqField = new HashMap<String, Object>();
        if (transZero)
            val = transZero(val);
        neqField.put(columnName, val);
        return this;
    }

    /**
     * 字段in列表
     *
     * @param fieldName
     * @param vals      数值列表
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion in(String fieldName, Object... vals) throws DataCrabDbConditionException {
        return this.in(transZero, fieldName, vals);
    }

    /**
     * 字段in列表
     *
     * @param fieldName
     * @param transZero 是否处理数字0
     * @param vals      数值列表
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion in(boolean transZero, String fieldName, Object... vals) throws DataCrabDbConditionException {
        if (vals != null && vals.length > 0) {
            String columnName = col(fieldName);
            if (inField == null)
                inField = new HashMap<String, List<?>>();
            if (transZero) {
                List<Object> nvals = new ArrayList<Object>();
                for (int i = 0; i < vals.length; i++) {
                    nvals.add(transZero(vals[i]));
                }
                inField.put(columnName, nvals);
            } else {
                inField.put(columnName, Arrays.asList(vals));
            }
        }
        return this;
    }

    /**
     * 字段in列表
     *
     * @param fieldName
     * @param vals      数值列表
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion in(String fieldName, List<?> vals) throws DataCrabDbConditionException {
        return this.in(transZero, fieldName, vals);
    }

    /**
     * 字段in列表
     *
     * @param fieldName
     * @param transZero 是否处理数字0
     * @param vals      数值列表
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion in(boolean transZero, String fieldName, List<?> vals) throws DataCrabDbConditionException {
        if (vals != null && vals.size() > 0) {
            String columnName = col(fieldName);
            if (inField == null)
                inField = new HashMap<String, List<?>>();
            if (transZero) {
                List<Object> nvals = new ArrayList<Object>();
                for (int i = 0; i < vals.size(); i++) {
                    nvals.add(transZero(vals.get(i)));
                }
                inField.put(columnName, nvals);
            } else {
                inField.put(columnName, vals);
            }
        }
        return this;
    }

    /**
     * 字段not in列表
     *
     * @param fieldName 字段名
     * @param vals      数值列表
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion notIn(String fieldName, Object... vals) throws DataCrabDbConditionException {
        return this.notIn(transZero, fieldName, vals);
    }

    /**
     * 字段not in列表
     *
     * @param fieldName 字段名
     * @param transZero 是否处理数字0
     * @param vals      数值列表
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion notIn(boolean transZero, String fieldName, Object... vals) throws DataCrabDbConditionException {
        if (vals != null && vals.length > 0) {
            String columnName = col(fieldName);
            if (notInField == null)
                notInField = new HashMap<String, List<?>>();
            if (transZero) {
                List<Object> nval = new ArrayList<Object>();
                for (int i = 0; i < vals.length; i++) {
                    nval.add(transZero(vals[i]));
                }
                notInField.put(columnName, nval);
            } else {
                notInField.put(columnName, Arrays.asList(vals));
            }
        }
        return this;
    }

    /**
     * 字段not in列表
     *
     * @param fieldName 字段名
     * @param vals      数值列表
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion notIn(String fieldName, List<?> vals) throws DataCrabDbConditionException {
        return this.notIn(transZero, fieldName, vals);
    }

    /**
     * 字段not in列表
     *
     * @param fieldName 字段名
     * @param transZero 是否处理数字0
     * @param vals      数值列表
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion notIn(boolean transZero, String fieldName, List<?> vals) throws DataCrabDbConditionException {
        if (vals != null && vals.size() > 0) {
            String columnName = col(fieldName);
            if (notInField == null)
                notInField = new HashMap<String, List<?>>();
            if (transZero) {
                List<Object> nval = new ArrayList<Object>();
                for (int i = 0; i < vals.size(); i++) {
                    nval.add(transZero(vals.get(i)));
                }
                notInField.put(columnName, nval);
            } else {
                notInField.put(columnName, vals);
            }
        }
        return this;
    }

    /**
     * 字段为空
     *
     * @param fieldName 字段名
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion isNull(String fieldName) throws DataCrabDbConditionException {
        String columnName = col(fieldName);
        if (nullField == null)
            nullField = new LinkedList<String>();
        nullField.add(columnName);
        return this;
    }

    /**
     * 字段为空
     *
     * @param fieldNames 字段名列表
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion isNull(String... fieldNames) throws DataCrabDbConditionException {
        if (fieldNames != null && fieldNames.length > 0) {
            if (nullField == null)
                nullField = new LinkedList<String>();
            for (int i = 0; i < fieldNames.length; i++) {
                String columnName = col(fieldNames[i]);
                nullField.add(columnName);
            }
        }
        return this;
    }

    /**
     * 字段不为空
     *
     * @param fieldName 字段名
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion isNotNull(String fieldName) throws DataCrabDbConditionException {
        String columnName = col(fieldName);
        if (notNullField == null)
            notNullField = new LinkedList<String>();
        notNullField.add(columnName);
        return this;
    }

    /**
     * 字段不为空
     *
     * @param fieldNames 字段名列表
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion isNotNull(String... fieldNames) throws DataCrabDbConditionException {
        if (fieldNames != null && fieldNames.length > 0) {
            if (notNullField == null)
                notNullField = new LinkedList<String>();
            for (int i = 0; i < fieldNames.length; i++) {
                String columnName = col(fieldNames[i]);
                notNullField.add(columnName);
            }
        }
        return this;
    }

    /**
     * 字段模糊匹配
     *
     * @param fieldName
     * @param val
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion like(String fieldName, String val) throws DataCrabDbConditionException {
        String columnName = col(fieldName);
        if (likeField == null)
            likeField = new HashMap<String, String>();
        likeField.put(columnName, val);
        return this;
    }

    /**
     * 字段反向模糊匹配
     *
     * @param fieldName
     * @param val
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion notLike(String fieldName, String val) throws DataCrabDbConditionException {
        String columnName = col(fieldName);
        if (notLikeField == null)
            notLikeField = new HashMap<String, String>();
        notLikeField.put(columnName, val);
        return this;
    }

    /**
     * 字段在上下边界之内
     *
     * @param fieldName
     * @param minVal
     * @param maxVal
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion between(String fieldName, Object minVal, Object maxVal) throws DataCrabDbConditionException {
        return this.between(transZero, fieldName, minVal, maxVal);
    }

    /**
     * 字段在上下边界之内
     *
     * @param fieldName
     * @param minVal
     * @param maxVal
     * @param transZero
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion between(boolean transZero, String fieldName, Object minVal, Object maxVal) throws DataCrabDbConditionException {
        String columnName = col(fieldName);
        if (betweenField == null)
            betweenField = new HashMap<String, DbQueryBoundary>();
        if (transZero) {
            minVal = transZero(minVal);
            maxVal = transZero(maxVal);
        }
        betweenField.put(columnName, new DbQueryBoundary(minVal, maxVal));
        return this;
    }

    /**
     * 字段在上下边界值之外
     *
     * @param fieldName
     * @param minVal
     * @param maxVal
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion notBetween(String fieldName, Object minVal, Object maxVal) throws DataCrabDbConditionException {
        return this.notBetween(transZero, fieldName, minVal, maxVal);
    }

    /**
     * 字段在上下边界值之外
     *
     * @param fieldName
     * @param minVal
     * @param maxVal
     * @param transZero
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion notBetween(boolean transZero, String fieldName, Object minVal, Object maxVal) throws DataCrabDbConditionException {
        String columnName = col(fieldName);
        if (notBetweenField == null)
            notBetweenField = new HashMap<String, DbQueryBoundary>();
        if (transZero) {
            minVal = transZero(minVal);
            maxVal = transZero(maxVal);
        }
        notBetweenField.put(columnName, new DbQueryBoundary(minVal, maxVal));
        return this;
    }

    //---------------------------------------------------------------------------------------

    /**
     * 字段去重
     *
     * @param fieldName
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion distinct(String fieldName) throws DataCrabDbConditionException {
        String columnName = col(fieldName);
        if (distinctFieldList == null) {
            distinctFieldList = new ArrayList<String>();
        }
        distinctFieldList.add(columnName);
        return this;
    }

    /**
     * 字段去重
     *
     * @param fieldNames
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion distinct(String... fieldNames) throws DataCrabDbConditionException {
        if (fieldNames != null && fieldNames.length > 0) {
            if (distinctFieldList == null) {
                distinctFieldList = new ArrayList<String>();
            }
            for (int i = 0; i < fieldNames.length; i++) {
                String columnName = col(fieldNames[i]);
                distinctFieldList.add(columnName);
            }
        }
        return this;
    }

    /**
     * 字段去重
     *
     * @param fieldNames
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion distinct(List<String> fieldNames) throws DataCrabDbConditionException {
        if (fieldNames != null && fieldNames.size() > 0) {
            if (distinctFieldList == null) {
                distinctFieldList = new ArrayList<String>();
            }
            for (int i = 0; i < fieldNames.size(); i++) {
                String columnName = col(fieldNames.get(i));
                distinctFieldList.add(columnName);
            }
        }
        return this;
    }

    /**
     * 字段排序
     *
     * @param fieldName
     * @param val
     * @return
     * @throws DataCrabDbConditionException
     */
    private DbQueryCriterion orderby(String fieldName, String val) throws DataCrabDbConditionException {
        String columnName = col(fieldName);
        if (orderbyField == null)
            orderbyField = new LinkedHashMap<String, String>();
        orderbyField.put(columnName, val);
        return this;
    }

    /**
     * 按字段正向排序
     *
     * @param fieldName
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion orderbyAsc(String fieldName) throws DataCrabDbConditionException {
        orderby(fieldName, "asc");
        return this;
    }

    /**
     * 按字段正向排序
     *
     * @param fieldNames
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion orderbyAsc(String... fieldNames) throws DataCrabDbConditionException {
        if (fieldNames != null && fieldNames.length > 0) {
            for (int i = 0; i < fieldNames.length; i++) {
                orderby(fieldNames[i], "asc");
            }
        }
        return this;
    }

    /**
     * 按字段反向排序
     *
     * @param fieldName
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion orderbyDesc(String fieldName) throws DataCrabDbConditionException {
        orderby(fieldName, "desc");
        return this;
    }

    /**
     * 按字段反向排序
     *
     * @param fieldNames
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion orderbyDesc(String... fieldNames) throws DataCrabDbConditionException {
        if (fieldNames != null && fieldNames.length > 0) {
            for (int i = 0; i < fieldNames.length; i++) {
                orderby(fieldNames[i], "desc");
            }
        }
        return this;
    }

    //---------------------------------------------------------------------------------------

    /**
     * 构建分页对象
     *
     * @param pageNo     当前页码
     * @param pageSize   分页大小
     * @return
     */
    public DbQueryCriterion page(long pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.startIdx = (pageNo - 1) * pageSize;
        this.endIdx = pageNo * pageSize - 1;
        return this;
    }

    /**
     * 构建分页对象
     *
     * @param pageNo     当前页码
     * @param pageSize   分页大小
     * @param dataAmount 数据总行数
     * @return
     */
    public DbQueryCriterion page(long pageNo, int pageSize, long dataAmount) {
        this.page(pageNo, pageSize);
        this.dataAmount = dataAmount;
        return this;
    }

    /**
     * 附加参数
     *
     * @param columnName 列名,直接传入sql,不转换
     * @param val
     * @return
     */
    public DbQueryCriterion plus(String columnName, Object val) {
        return this.plus(transZero, columnName, val);
    }

    /**
     * 附加参数
     *
     * @param columnName 列名,直接传入sql,不转换
     * @param val
     * @param transZero
     * @return
     */
    public DbQueryCriterion plus(boolean transZero, String columnName, Object val) {
        if (transZero) {
            val = transZero(val);
        }
        if (plusParam == null)
            plusParam = new HashMap<String, Object>();
        plusParam.put(columnName, val);
        return this;
    }

    /**
     * 附加参数
     *
     * @param entityClass
     * @param fieldName   对象字段名,会转成表中的列名传入sql
     * @param val
     * @return
     */
    public DbQueryCriterion plus(Class<?> entityClass, String fieldName, Object val) throws DataCrabDbConditionException {
        return this.plus(transZero, entityClass, fieldName, val, null);
    }

    /**
     * 附加参数
     *
     * @param entityClass
     * @param fieldName   对象字段名,会转成表中的列名传入sql
     * @param val
     * @return
     */
    public DbQueryCriterion plus(Class<?> entityClass, String fieldName, Object val, String alias) throws DataCrabDbConditionException {
        return this.plus(transZero, entityClass, fieldName, val, alias);
    }

    /**
     * 附加参数
     *
     * @param entityClass
     * @param fieldName   对象字段名,会转成表中的列名传入sql
     * @param val
     * @param transZero
     * @return
     */
    public DbQueryCriterion plus(boolean transZero, Class<?> entityClass, String fieldName, Object val) throws DataCrabDbConditionException {
        return this.plus(transZero, entityClass, fieldName, val, null);
    }

    /**
     * 附加参数
     *
     * @param entityClass
     * @param fieldName
     * @param val
     * @param alias
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion plus(boolean transZero, Class<?> entityClass, String fieldName, Object val, String alias) throws DataCrabDbConditionException {
        if (transZero) {
            val = transZero(val);
        }
        if (extColumnMap == null || !extColumnMap.containsKey(entityClass)) {
            initExtColumnMap(entityClass);
        }
        Map<String, String> columnMap = extColumnMap.get(entityClass);
        String colName = null;
        if (fieldName != null && "*".equals(fieldName.trim())) {
            colName = "*";
        } else {
            colName = columnMap.get(fieldName);
            if (colName == null || colName.isEmpty()) {
                throw new DataCrabDbConditionException(String.format("不存在的字段名:%s,对象:%s", fieldName, entityClass.getName()));
            }
        }
        if (alias != null && !alias.isEmpty()) {
            colName = alias + "." + colName;
        }
        if (plusParam == null)
            plusParam = new HashMap<String, Object>();
        plusParam.put(colName, val);
        return this;
    }

    //---------------------------------------------------------------------------------------

    /**
     * 分组查询
     *
     * @param querys
     * @return
     */
    @SuppressWarnings("unchecked")
    public DbQueryCriterion group(DbQueryCriterion... querys) {
        if (groups == null)
            groups = new LinkedList<Map<String, Object>>();
        Map<String, Object> group = new HashMap<String, Object>();
        for (DbQueryCriterion qc : querys) {
            if (qc.getGtField() != null)
                group.put("gtField", grpAdd(group.get("gtField"), qc.getGtField()));
            if (qc.getGteField() != null)
                group.put("gteField", grpAdd(group.get("gteField"), qc.getGteField()));
            if (qc.getLtField() != null)
                group.put("ltField", grpAdd(group.get("ltField"), qc.getLtField()));
            if (qc.getLteField() != null)
                group.put("lteField", grpAdd(group.get("lteField"), qc.getLteField()));
            if (qc.getEqField() != null)
                group.put("eqField", grpAdd(group.get("eqField"), qc.getEqField()));
            if (qc.getNeqField() != null)
                group.put("neqField", grpAdd(group.get("neqField"), qc.getNeqField()));
            if (qc.getInField() != null) {
                Map<String, List<?>> oldVal = (Map<String, List<?>>) group.get("inField");
                if (oldVal == null) {
                    group.put("inField", qc.getInField());
                } else {
                    oldVal.putAll(qc.getInField());
                    group.put("inField", oldVal);
                }
            }
            if (qc.getNotInField() != null) {
                Map<String, List<?>> oldVal = (Map<String, List<?>>) group.get("notInField");
                if (oldVal == null) {
                    group.put("notInField", qc.getNotInField());
                } else {
                    oldVal.putAll(qc.getNotInField());
                    group.put("notInField", oldVal);
                }
            }
            if (qc.getNullField() != null) {
                List<String> oldVal = (List<String>) group.get("nullField");
                if (oldVal == null) {
                    group.put("nullField", qc.getNullField());
                } else {
                    oldVal.addAll(qc.getNullField());
                    group.put("nullField", oldVal);
                }
            }
            if (qc.getNotNullField() != null) {
                List<String> oldVal = (List<String>) group.get("notNullField");
                if (oldVal == null) {
                    group.put("notNullField", qc.getNotNullField());
                } else {
                    oldVal.addAll(qc.getNotNullField());
                    group.put("notNullField", oldVal);
                }
            }
            if (qc.getLikeField() != null) {
                Map<String, String> oldVal = (Map<String, String>) group.get("likeField");
                if (oldVal == null) {
                    group.put("likeField", qc.getLikeField());
                } else {
                    oldVal.putAll(qc.getLikeField());
                    group.put("likeField", oldVal);
                }
            }
            if (qc.getNotLikeField() != null) {
                Map<String, String> oldVal = (Map<String, String>) group.get("notLikeField");
                if (oldVal == null) {
                    group.put("notLikeField", qc.getNotLikeField());
                } else {
                    oldVal.putAll(qc.getNotLikeField());
                    group.put("notLikeField", oldVal);
                }
            }
            if (qc.getBetweenField() != null) {
                Map<String, DbQueryBoundary> oldVal = (Map<String, DbQueryBoundary>) group.get("betweenField");
                if (oldVal == null) {
                    group.put("betweenField", qc.getBetweenField());
                } else {
                    oldVal.putAll(qc.getBetweenField());
                    group.put("betweenField", oldVal);
                }
            }
            if (qc.getNotBetweenField() != null) {
                Map<String, DbQueryBoundary> oldVal = (Map<String, DbQueryBoundary>) group.get("notBetweenField");
                if (oldVal == null) {
                    group.put("notBetweenField", qc.getNotBetweenField());
                } else {
                    oldVal.putAll(qc.getNotBetweenField());
                    group.put("notBetweenField", oldVal);
                }
            }
            if (qc.getPlusParam() != null)
                group.put("plusParam", grpAdd(group.get("plusParam"), qc.getPlusParam()));
        }
        groups.add(group);
        return this;
    }

    /**
     * 添加分组
     *
     * @param oldVal
     * @param addVal
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> grpAdd(Object oldVal, Map<String, Object> addVal) {
        if (oldVal == null) {
            return addVal;
        } else {
            Map<String, Object> subGtField = (Map<String, Object>) oldVal;
            subGtField.putAll(addVal);
            return subGtField;
        }
    }

    //---------------------------------------------------------------------------------------

    public Map<String, Object> getGtField() {
        return gtField;
    }

    public Map<String, Object> getGteField() {
        return gteField;
    }

    public Map<String, Object> getLtField() {
        return ltField;
    }

    public Map<String, Object> getLteField() {
        return lteField;
    }

    public Map<String, Object> getEqField() {
        return eqField;
    }

    public Map<String, Object> getNeqField() {
        return neqField;
    }

    public Map<String, List<?>> getInField() {
        return inField;
    }

    public Map<String, List<?>> getNotInField() {
        return notInField;
    }

    public List<String> getNullField() {
        return nullField;
    }

    public List<String> getNotNullField() {
        return notNullField;
    }

    public Map<String, String> getLikeField() {
        return likeField;
    }

    public Map<String, String> getNotLikeField() {
        return notLikeField;
    }

    public Map<String, DbQueryBoundary> getBetweenField() {
        return betweenField;
    }

    public Map<String, DbQueryBoundary> getNotBetweenField() {
        return notBetweenField;
    }

    public Map<String, Object> getPlusParam() {
        return plusParam;
    }

    public List<Map<String, Object>> getGroups() {
        return groups;
    }

    public Map<String, String> getOrderbyField() {
        return orderbyField;
    }

    public String getDistinctField() {
        if (distinctFieldList != null && distinctFieldList.size() > 0) {
            StringBuilder dstBuilder = new StringBuilder();
            for (int i = 0; i < distinctFieldList.size(); i++) {
                String df = distinctFieldList.get(i);
                if ("*".equals(df)) {
                    return "*";
                } else {
                    dstBuilder.append(df).append(",");
                }
            }
            return dstBuilder.deleteCharAt(dstBuilder.length() - 1).toString();
        }
        return null;
    }

    public Map<String, String> getColumnMap() {
        return columnMap;
    }

    public List<String> getDistinctFieldList() {
        return distinctFieldList;
    }

    public Map<Class<?>, Map<String, String>> getExtColumnMap() {
        return extColumnMap;
    }

    public Long getPageNo() {
        return pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Long getDataAmount() {
        return dataAmount;
    }

    public Long getStartIdx() {
        return startIdx;
    }

    public Long getEndIdx() {
        return endIdx;
    }

    public String getAlias() {
        return alias;
    }

    //---------------------------------------------------------------------------------------

    private Class<?> entityClass;

    //是否处理数字0
    private boolean transZero = false;

    //大于
    protected Map<String, Object> gtField;

    //大于或等于
    protected Map<String, Object> gteField;

    //小于
    protected Map<String, Object> ltField;

    //小于或等于
    protected Map<String, Object> lteField;

    //等于
    protected Map<String, Object> eqField;

    //不等于
    protected Map<String, Object> neqField;

    //in
    protected Map<String, List<?>> inField;

    //not in
    protected Map<String, List<?>> notInField;

    //is null
    protected List<String> nullField;

    //is not null
    protected List<String> notNullField;

    //like
    protected Map<String, String> likeField;

    //not like
    protected Map<String, String> notLikeField;

    //between
    protected Map<String, DbQueryBoundary> betweenField;

    //not between
    protected Map<String, DbQueryBoundary> notBetweenField;

    //扩展参数
    protected Map<String, Object> plusParam;

    //分组字段
    protected List<Map<String, Object>> groups;

    //排序字段
    protected Map<String, String> orderbyField;

    //去重字段
    protected List<String> distinctFieldList;

    //去重字段拼接
    protected String distinctField;

    //字段映射
    protected Map<String, String> columnMap;

    //扩展字段映射
    protected Map<Class<?>, Map<String, String>> extColumnMap;

    //当前页码
    protected Long pageNo;

    //分页大小
    protected Integer pageSize;

    //数据总数量
    protected Long dataAmount;

    //分页起始索引
    protected Long startIdx;

    //分页结束索引
    protected Long endIdx;

    //表别名
    protected String alias;

    //---------------------------------------------------------------------------------------

    /**
     * 初始化字段映射
     *
     * @param entityClass
     */
    private void initColumnMap(Class<?> entityClass) {
        columnMap = getColumnMapByClass(entityClass);
    }

    /**
     * 初始扩展entity对象的字段映射
     *
     * @param entityClass
     */
    private void initExtColumnMap(Class<?> entityClass) {
        if (extColumnMap == null) {
            extColumnMap = new HashMap<Class<?>, Map<String, String>>();
        }
        extColumnMap.put(entityClass, getColumnMapByClass(entityClass));
    }

    /**
     * 取得entity对象的字段映射
     *
     * @param entityClass
     * @return
     */
    private Map<String, String> getColumnMapByClass(Class<?> entityClass) {
        Map<String, String> colMap = new HashMap<String, String>();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field f : fields) {
            DbColumn an = f.getAnnotation(DbColumn.class);
            String fieldName = f.getName();
            String colName = "";
            if (an != null) {
                colName = an.value();
            } else {
                StringBuilder cBuilder = new StringBuilder();
                for (int i = 0; i < fieldName.length(); i++) {
                    char ch = fieldName.charAt(i);
                    if (Character.isUpperCase(ch)) {
                        cBuilder.append("_").append(Character.toLowerCase(ch));
                    } else {
                        cBuilder.append(ch);
                    }
                }
                colName = cBuilder.toString();
            }
            colMap.put(fieldName, colName);
        }
        return colMap;
    }

    /**
     * 获取注解表名
     *
     * @return
     */
    public String getTableName() {
        DbTable table = entityClass.getAnnotation(DbTable.class);
        if (table != null) {
            return table.value();
        }
        String entityName = entityClass.getSimpleName();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entityName.length(); i++) {
            char c = entityName.charAt(i);
            if (Character.isLowerCase(c)) {
                sb.append(c);
            } else {
                if (i > 0) {
                    sb.append("_");
                }
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    /**
     * 组装列名
     *
     * @param fieldName
     * @return
     * @throws DataCrabDbConditionException
     */
    protected String col(String fieldName) throws DataCrabDbConditionException {
        if (fieldName != null && "*".equals(fieldName.trim())) {
            return "*";
        }
        String colName = columnMap.get(fieldName);
        if (colName == null || colName.isEmpty()) {
            throw new DataCrabDbConditionException(String.format("不存在的字段名:%s", fieldName));
        }
        if (alias != null && !alias.isEmpty()) {
            return alias + "." + colName;
        }
        return colName;
    }

    /**
     * 设置对象的值不为空的字段为查询条件
     *
     * @param t
     * @return
     */
    @SuppressWarnings("static-access")
    public DbQueryCriterion setEntity(Object t) {
        Method[] ms = t.getClass().getDeclaredMethods();
        String getterRegex = "get(\\w+)";
        String typeRegex = "String|Integer|Long|Float|Double|BigDecimal|BigInteger|Date|Time|TimeStamp";
        Pattern getter = Pattern.compile(getterRegex);
        for (int i = 0; i < ms.length; i++) {
            Method m = ms[i];
            String mName = m.getName();
            if (getter.matches(getterRegex, mName) && m.getReturnType().getSimpleName().matches(typeRegex)) {
                try {
                    Object fieldVal = m.invoke(t);
                    if (fieldVal != null) {
                        String fieldName;
                        String n = getter.matcher(mName).replaceAll("$1");
                        if (n.length() == 1) {
                            fieldName = n.toLowerCase();
                        } else {
                            fieldName = n.substring(0, 1).toLowerCase() + n.substring(1);
                        }
                        eq(fieldName, fieldVal);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    /**
     * 数字0转换成字符"0"
     *
     * @param val
     * @return
     */
    private Object transZero(Object val) {
        if (val != null) {
            String type = val.getClass().getSimpleName();
            if (type.matches("Integer|Long|Float|Double|BigDecimal|BigInteger")) {
                if (val.toString().matches("^[+-]?(0+\\.?0*)([eE][+-]?[0-9]+)?$")) {
                    return "0";
                }
            }
        }
        return val;
    }

    //---------------------------------------------------------------------------------------

    @Override
    public DbQueryCriterion clone() {
        try {
            DbQueryCriterion qc = (DbQueryCriterion) super.clone();
            qc.entityClass = this.entityClass;
            qc.transZero = this.transZero;
            qc.gtField = copyMapObj(this.getGtField());
            qc.gteField = copyMapObj(this.getGteField());
            qc.ltField = copyMapObj(this.getLtField());
            qc.lteField = copyMapObj(this.getLteField());
            qc.eqField = copyMapObj(this.getEqField());
            qc.neqField = copyMapObj(this.getNeqField());
            qc.inField = copyMapList(this.getInField());
            qc.notInField = copyMapList(this.getNotInField());
            qc.nullField = copyListStr(this.getNullField());
            qc.notNullField = copyListStr(this.getNotNullField());
            qc.likeField = copyMapStr(this.getLikeField());
            qc.notLikeField = copyMapStr(this.getNotLikeField());
            qc.betweenField = copyMapBoundary(this.getBetweenField());
            qc.notBetweenField = copyMapBoundary(this.getNotBetweenField());
            qc.plusParam = copyMapObj(this.getPlusParam());
            qc.groups = copyListMap(this.getGroups());
            qc.orderbyField = copyMapStrSorted(this.getOrderbyField());
            qc.distinctFieldList = copyListStr(this.getDistinctFieldList());
            qc.distinctField = this.getDistinctField();
            qc.columnMap = copyMapStr(this.getColumnMap());
            qc.extColumnMap = copyMapMap(this.getExtColumnMap());
            qc.pageNo = this.getPageNo();
            qc.pageSize = this.getPageSize();
            qc.dataAmount = this.getDataAmount();
            qc.startIdx = this.getStartIdx();
            qc.endIdx = this.getEndIdx();
            qc.alias = this.getAlias();

            return qc;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> copyMapObj(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        Map<String, Object> newMap = new HashMap<String, Object>();
        newMap.putAll(map);
        return newMap;
    }

    private Map<String, List<?>> copyMapList(Map<String, List<?>> map) {
        if (map == null) {
            return null;
        }
        Map<String, List<?>> newMap = new HashMap<String, List<?>>();
        Set<Entry<String, List<?>>> entries = map.entrySet();
        for (Entry<String, List<?>> entry : entries) {
            newMap.put(entry.getKey(), copyListObj(entry.getValue()));
        }
        return newMap;
    }

    private Map<String, String> copyMapStr(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        Map<String, String> newMap = new HashMap<String, String>();
        newMap.putAll(map);
        return newMap;
    }

    private Map<String, String> copyMapStrSorted(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        Map<String, String> newMap = new LinkedHashMap<String, String>();
        newMap.putAll(map);
        return newMap;
    }

    private Map<String, DbQueryBoundary> copyMapBoundary(Map<String, DbQueryBoundary> map) {
        if (map == null) {
            return null;
        }
        Map<String, DbQueryBoundary> newMap = new HashMap<String, DbQueryBoundary>();
        Set<Entry<String, DbQueryBoundary>> entries = map.entrySet();
        for (Entry<String, DbQueryBoundary> entry : entries) {
            newMap.put(entry.getKey(), entry.getValue().clone());
        }
        return newMap;
    }

    private Map<Class<?>, Map<String, String>> copyMapMap(Map<Class<?>, Map<String, String>> map) {
        if (map == null) {
            return null;
        }
        Map<Class<?>, Map<String, String>> newMap = new HashMap<Class<?>, Map<String, String>>();
        Set<Entry<Class<?>, Map<String, String>>> entries = map.entrySet();
        for (Entry<Class<?>, Map<String, String>> entry : entries) {
            newMap.put(entry.getKey(), copyMapStr(entry.getValue()));
        }
        return newMap;
    }

    private List<String> copyListStr(List<String> list) {
        if (list == null) {
            return null;
        }
        List<String> newList = new ArrayList<String>();
        newList.addAll(list);
        return newList;
    }

    private List copyListObj(List<?> list) {
        if (list == null) {
            return null;
        }
        List newList = new ArrayList<Object>();
        newList.addAll(list);
        return newList;
    }

    private List<Map<String, Object>> copyListMap(List<Map<String, Object>> list) {
        if (list == null) {
            return null;
        }
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list) {
            newList.add(copyMapObj(map));
        }
        return newList;
    }

    //---------------------------------------------------------------------------------------

    /**
     * where条件为空的时候中断
     *
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion abortNullWhere() throws DataCrabDbConditionException {
        if (isNullWhere()) {
            throw new DataCrabDbConditionException("WHERE条件不能为空!");
        }
        return this;
    }

    /**
     * 分页条件为空的时候中断
     *
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion abortNullPage() throws DataCrabDbConditionException {
        if (isNullPage()) {
            throw new DataCrabDbConditionException("分页条件不能为空!");
        }
        return this;
    }

    /**
     * where或者分页条件为空的时候中断，全都不允许为空
     *
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion abortNullWhereOrPage() throws DataCrabDbConditionException {
        if (isNullWhere()) {
            throw new DataCrabDbConditionException("WHERE条件不能为空!");
        }
        if (isNullPage()) {
            throw new DataCrabDbConditionException("分页条件不能为空!");
        }
        return this;
    }

    /**
     * 所有条件为空的时候中断，允许有一个为空
     *
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion abortNullWhereAndPage() throws DataCrabDbConditionException {
        if (isNullPage() && isNullWhere()) {
            throw new DataCrabDbConditionException("WHERE和分页条件不能全部为空!");
        }
        return this;
    }

    /**
     * where或者分页条件为空的时候中断，全都不允许为空
     *
     * @return
     * @throws DataCrabDbConditionException
     */
    public DbQueryCriterion abortNull() throws DataCrabDbConditionException {
        abortNullWhereOrPage();
        return this;
    }

    /**
     * where条件是否为空
     *
     * @return
     */
    private boolean isNullWhere() {
        return isNullMapObj(gtField)
                && isNullMapObj(gteField)
                && isNullMapObj(ltField)
                && isNullMapObj(lteField)
                && isNullMapObj(eqField)
                && isNullMapObj(neqField)
                && isNullMapList(inField)
                && isNullMapList(notInField)
                && isNullListStr(nullField)
                && isNullListStr(notNullField)
                && isNullMapStr(likeField)
                && isNullMapStr(notLikeField)
                && isNullMapBoundary(betweenField)
                && isNullMapBoundary(notBetweenField)
                && isNullMapObj(plusParam);
    }

    /**
     * 分页条件是否为空
     *
     * @return
     */
    private boolean isNullPage() {
        return startIdx == null || pageSize == null || pageNo == null || endIdx == null;
    }

    private boolean isNullMapObj(Map<String, Object> map) {
        if (map == null || map.size() == 0) {
            return true;
        }
        for (Entry<String, Object> en : map.entrySet()) {
            Object value = en.getValue();
            if (value != null && String.valueOf(value).trim().length() > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isNullMapStr(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return true;
        }
        for (Entry<String, String> en : map.entrySet()) {
            String value = en.getValue();
            if (value != null && value.trim().length() > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isNullMapList(Map<String, List<?>> map) {
        if (map == null || map.size() == 0) {
            return true;
        }
        for (Entry<String, List<?>> en : map.entrySet()) {
            List<?> value = en.getValue();
            if (value != null && value.size() > 0) {
                for (Object obj : value) {
                    if (obj != null && String.valueOf(obj).trim().length() > 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isNullMapBoundary(Map<String, DbQueryBoundary> map) {
        if (map == null || map.size() == 0) {
            return true;
        }
        for (Entry<String, DbQueryBoundary> en : map.entrySet()) {
            DbQueryBoundary value = en.getValue();
            if (value != null && value.getMaxVal() != null && value.getMinVal() != null) {
                return false;
            }
        }
        return true;
    }

    private boolean isNullListStr(List<String> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        for (String str : list) {
            if (str != null && str.trim().length() > 0) {
                return false;
            }
        }
        return true;

    }


    //----set 方法提供转换操作

    public DbQueryCriterion setGtField(Map<String, Object> gtField) {
        this.gtField = gtField;
        return this;
    }

    public DbQueryCriterion setGteField(Map<String, Object> gteField) {
        this.gteField = gteField;
        return this;
    }

    public DbQueryCriterion setLtField(Map<String, Object> ltField) {
        this.ltField = ltField;
        return this;
    }

    public DbQueryCriterion setLteField(Map<String, Object> lteField) {
        this.lteField = lteField;
        return this;
    }

    public DbQueryCriterion setEqField(Map<String, Object> eqField) {
        this.eqField = eqField;
        return this;
    }

    public DbQueryCriterion setNeqField(Map<String, Object> neqField) {
        this.neqField = neqField;
        return this;
    }

    public DbQueryCriterion setInField(Map<String, List<?>> inField) {
        this.inField = inField;
        return this;
    }

    public DbQueryCriterion setNotInField(Map<String, List<?>> notInField) {
        this.notInField = notInField;
        return this;
    }

    public DbQueryCriterion setNullField(List<String> nullField) {
        this.nullField = nullField;
        return this;
    }

    public DbQueryCriterion setNotNullField(List<String> notNullField) {
        this.notNullField = notNullField;
        return this;
    }

    public DbQueryCriterion setBetweenField(Map<String, DbQueryBoundary> betweenField) {
        this.betweenField = betweenField;
        return this;
    }

    public DbQueryCriterion setNotBetweenField(Map<String, DbQueryBoundary> notBetweenField) {
        this.notBetweenField = notBetweenField;
        return this;
    }

    // ---- set 方法提供转换操作 end


    //---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return this.info();
    }

    /**
     * 返回详细信息
     *
     * @return
     */
    public String info() {
        return "DbQueryCriterion{" +
                "entityClass=" + entityClass +
                ", transZero=" + transZero +
                ", gtField=" + gtField +
                ", gteField=" + gteField +
                ", ltField=" + ltField +
                ", lteField=" + lteField +
                ", eqField=" + eqField +
                ", neqField=" + neqField +
                ", inField=" + inField +
                ", notInField=" + notInField +
                ", nullField=" + nullField +
                ", notNullField=" + notNullField +
                ", likeField=" + likeField +
                ", notLikeField=" + notLikeField +
                ", betweenField=" + betweenField +
                ", notBetweenField=" + notBetweenField +
                ", plusParam=" + plusParam +
                ", groups=" + groups +
                ", orderbyField=" + orderbyField +
//				", distinctFieldList=" + distinctFieldList +
                ", distinctField='" + distinctField + '\'' +
//				", columnMap=" + columnMap +
//				", extColumnMap=" + extColumnMap +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", dataAmount=" + dataAmount +
                ", startIdx=" + startIdx +
                ", endIdx=" + endIdx +
                ", alias='" + alias + '\'' +
                '}';
    }

    //---------------------------------------------------------------------------------------

}
