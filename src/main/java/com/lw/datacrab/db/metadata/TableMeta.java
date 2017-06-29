package com.lw.datacrab.db.metadata;

import java.util.List;

/**
 * 表元数据
 *
 * @author lw
 * @date 2017-2-8 11:17:57
 */
public class TableMeta {

    //表名
    private String name;

    //表注释
    private String remarks;

    //模式
    private String tableSchema;

    private String tableType;

    private String tableCat;

//    private String typeName;
//
//    private String typeCat;
//
//    private String typeSchem;

    //表的列
    private List<ColumnMeta> columns;

    //entity类名
    private String entityName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getTableCat() {
        return tableCat;
    }

    public void setTableCat(String tableCat) {
        this.tableCat = tableCat;
    }

    public List<ColumnMeta> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnMeta> columns) {
        this.columns = columns;
    }

    public String getEntityName() {
        if (entityName == null) {
            entityName = getEntityNameFromTable(name);
        }
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String toString() {
        return "TableMeta{" +
                "name='" + name + '\'' +
                ", remarks='" + remarks + '\'' +
                ", tableSchema='" + tableSchema + '\'' +
                ", tableType='" + tableType + '\'' +
                ", tableCat='" + tableCat + '\'' +
                ", columns=" + columns +
                ", entityName='" + entityName + '\'' +
                '}';
    }

    /**
     * 表名转换为entity类名
     *
     * @param tableName
     * @return
     */
    public String getEntityNameFromTable(String tableName) {
        String pname = tableName.toLowerCase();
        int idx = pname.indexOf("_");
        while (idx != -1) {
            if (idx < pname.length() - 1) {
                pname = String.format("%s%s%s", pname.substring(0, idx),
                        pname.substring(idx + 1, idx + 2).toUpperCase(),
                        pname.substring(idx + 2));
            }
            idx = pname.indexOf("_");
        }
        return String.format("%s%s", pname.substring(0, 1).toUpperCase(), pname.substring(1));
    }
}
