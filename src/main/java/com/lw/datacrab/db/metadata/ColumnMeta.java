package com.lw.datacrab.db.metadata;

import java.sql.Blob;
import java.sql.Types;
import java.util.Date;

/**
 * 列元数据
 *
 * @author lw
 * @date 2017-2-8 11:20:15
 */
public class ColumnMeta {

    //列名
    private String name;

    //列类型
    private int sqlType;

    //列大小
    private int columnSize;

    //是否允许空
    private boolean nullable;

    //是否是主键
    private boolean primaryKey;

    //列注释
    private String remarks;

    //列默认值
    private String columnDef;

    //字段名
    private String fieldName;

    //字段类型
    private Class<?> javaType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSqlType() {
        return sqlType;
    }

    public void setSqlType(int sqlType) {
        this.sqlType = sqlType;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getColumnDef() {
        return columnDef;
    }

    public void setColumnDef(String columnDef) {
        this.columnDef = columnDef;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        if (fieldName == null) {
            fieldName = getFieldNameFromColumn(name);
        }
        return fieldName;
    }

    @Override
    public String toString() {
        return "ColumnMeta{" +
                "name='" + name + '\'' +
                ", sqlType=" + sqlType +
                ", javaType=" + javaType +
                ", columnSize=" + columnSize +
                ", nullable=" + nullable +
                ", primaryKey=" + primaryKey +
                ", remarks='" + remarks + '\'' +
                ", columnDef='" + columnDef + '\'' +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }

    /**
     * 列名转换为字段名
     *
     * @param columnName
     * @return
     */
    public String getFieldNameFromColumn(String columnName) {
        String cname = columnName.toLowerCase();
        int idx = cname.indexOf("_");
        while (idx != -1) {
            if (idx < cname.length() - 1) {
                cname = String.format("%s%s%s", cname.substring(0, idx),
                        cname.substring(idx + 1, idx + 2).toUpperCase(),
                        cname.substring(idx + 2));
            }
            idx = cname.indexOf("_");
        }
        return cname;
    }

    /**
     * 列的sql类型转换java字段类型
     *
     * @param columnType
     * @return
     */
    public Class<?> getJavaTypeFromColumn(int columnType) {
        Class<?> clazz = null;
        switch (columnType) {
            // 字符
            case Types.CLOB:
//            case Types.NCLOB:
            case Types.CHAR:
            case Types.VARCHAR:
//            case Types.NVARCHAR:
//            case Types.LONGNVARCHAR:
            case Types.LONGVARCHAR:
                clazz = String.class;
                break;

            case Types.BIT:
            case Types.BOOLEAN:
                clazz = Boolean.class;
                break;

            // 数字
            case Types.NUMERIC:
            case Types.DECIMAL:
                clazz = Number.class;
                break;

            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
                clazz = Integer.class;
                break;

            case Types.VARBINARY:
            case Types.BIGINT:
                clazz = Long.class;
                break;

            case Types.REAL:
                clazz = Float.class;
                break;
            case Types.FLOAT:
            case Types.DOUBLE:
                clazz = Double.class;
                break;

            //日期
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
                clazz = Date.class;
                break;

            //BLOB
            case Types.BLOB:
                clazz = Blob.class;
                break;
            default:
                clazz = Object.class;
        }
        return clazz;
    }

}