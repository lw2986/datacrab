package com.lw.datacrab.db.dao;

import com.lw.datacrab.db.metadata.ColumnMeta;
import com.lw.datacrab.db.metadata.TableMeta;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.*;

/**
 * 数据库表元数据工具类
 *
 * @author lw
 * @date 2017-2-8 11:36:26
 */
public class DbMetaDataHandler {

    /**
     * 数据源，一般的数据源是一个连接池，所以本类获取到连接不主动关闭
     */
    private DataSource dataSource;

    /**
     * 根据表名获取表信息
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    public TableMeta getTable(String tableName) throws Exception {
        Connection conn = connect();
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet tableRS = metaData.getTables(null, null, tableName, null);

        TableMeta table = null;
        if (tableRS.next()) {
            tableName = tableRS.getString("TABLE_NAME");
            String tableCat = tableRS.getString("TABLE_CAT");
            String tableSchem = tableRS.getString("TABLE_SCHEM");
            String tableType = tableRS.getString("TABLE_TYPE");
            String remarks = tableRS.getString("REMARKS");
//            String typeCat = tableRS.getString("TYPE_CAT");
//            String typeSchem = tableRS.getString("TYPE_SCHEM");
//            String typeName = tableRS.getString("TYPE_NAME");

            table = new TableMeta();
            table.setName(tableName);
            table.setTableCat(tableCat);
            table.setTableSchema(tableSchem);
            table.setTableType(tableType);
            table.setRemarks(trimRemarks(remarks));
//            table.setTypeCat(typeCat);
//            table.setTypeSchem(typeSchem);
//            table.setTypeName(typeName);
        }
        tableRS.close();

        if (table != null) {
            //获取主键
            Set<String> primarySet = new HashSet<String>();
            ResultSet pkRS = metaData.getPrimaryKeys(null, null, table.getName());
            while (pkRS.next()) {// 查询表主键
                primarySet.add(pkRS.getString("COLUMN_NAME"));
            }
            pkRS.close();

            //获取列信息
            List<ColumnMeta> columnList = new ArrayList<ColumnMeta>();
            ResultSet columnRS = metaData.getColumns(null, null, table.getName(), null);
            while (columnRS.next()) {
                String columnName = columnRS.getString("COLUMN_NAME");
                int dataType = columnRS.getInt("DATA_TYPE");//来自 java.sql.Types 的 SQL 类型
                String typeName = columnRS.getString("TYPE_NAME");//数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的
                int columnSize = columnRS.getInt("COLUMN_SIZE");//列大小
                boolean nullable = columnRS.getBoolean("NULLABLE");//是否允许使用 NULL columnNoNulls-可能不允许使用NULL值 columnNullable-明确允许使用NULL值 columnNullableUnknown-不知道是否可使用null
                String remarks = columnRS.getString("REMARKS");//列注释
//                    String columnDef = columnRS.getString("COLUMN_DEF ");//列的默认值
                boolean isPrimaryKey = primarySet.contains(columnName);//是否是主键

                ColumnMeta column = new ColumnMeta();
                column.setName(columnName);
                column.setSqlType(dataType);
                column.setJavaType(column.getJavaTypeFromColumn(dataType));
                column.setColumnSize(columnSize);
                column.setNullable(nullable);
                column.setPrimaryKey(isPrimaryKey);
                column.setRemarks(trimRemarks(remarks));
//                    column.setColumnDef(columnDef);
                column.setFieldName(column.getFieldNameFromColumn(columnName));
                columnList.add(column);
            }
            if (!columnList.isEmpty()) {
                table.setColumns(columnList);
            }
            columnRS.close();
        }
        release(conn);
        return table;
    }

    /**
     * 获取当前连接的所有表
     *
     * @return
     * @throws Exception
     */
    public List<TableMeta> getAllTables() throws Exception {
        Connection conn = connect();
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet tableRS = metaData.getTables(null, null, null, null);

        List<TableMeta> tableList = new ArrayList<TableMeta>();
        while (tableRS.next()) {
            String tableName = tableRS.getString("TABLE_NAME");
            String tableCat = tableRS.getString("TABLE_CAT");
            String tableSchem = tableRS.getString("TABLE_SCHEM");
            String tableType = tableRS.getString("TABLE_TYPE");
            String remarks = tableRS.getString("REMARKS");
//            String typeCat = tableRS.getString("TYPE_CAT");
//            String typeSchem = tableRS.getString("TYPE_SCHEM");
//            String typeName = tableRS.getString("TYPE_NAME");

            TableMeta table = new TableMeta();
            table.setName(tableName);
            table.setTableCat(tableCat);
            table.setTableSchema(tableSchem);
            table.setTableType(tableType);
            table.setRemarks(trimRemarks(remarks));
//            table.setTypeCat(typeCat);
//            table.setTypeSchem(typeSchem);
//            table.setTypeName(typeName);
            tableList.add(table);
        }
        tableRS.close();

        if (!tableList.isEmpty()) {
            for (Iterator<TableMeta> iterator = tableList.iterator(); iterator.hasNext(); ) {
                TableMeta table = iterator.next();
                //获取主键
                Set<String> primarySet = new HashSet<String>();
                ResultSet pkRS = metaData.getPrimaryKeys(null, null, table.getName());
                while (pkRS.next()) {// 查询表主键
                    primarySet.add(pkRS.getString("COLUMN_NAME"));
                }
                pkRS.close();

                //获取列信息
                List<ColumnMeta> columnList = new ArrayList<ColumnMeta>();
                ResultSet columnRS = metaData.getColumns(null, null, table.getName(), null);
                while (columnRS.next()) {
                    String columnName = columnRS.getString("COLUMN_NAME");
                    int dataType = columnRS.getInt("DATA_TYPE");//来自 java.sql.Types 的 SQL 类型
                    String typeName = columnRS.getString("TYPE_NAME");//数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的
                    int columnSize = columnRS.getInt("COLUMN_SIZE");//列大小
                    boolean nullable = columnRS.getBoolean("NULLABLE");//是否允许使用 NULL columnNoNulls-可能不允许使用NULL值 columnNullable-明确允许使用NULL值 columnNullableUnknown-不知道是否可使用null
                    String remarks = columnRS.getString("REMARKS");//列注释
//                    String columnDef = columnRS.getString("COLUMN_DEF ");//列的默认值
                    boolean isPrimaryKey = primarySet.contains(columnName);//是否是主键

                    ColumnMeta column = new ColumnMeta();
                    column.setName(columnName);
                    column.setSqlType(dataType);
                    column.setJavaType(column.getJavaTypeFromColumn(dataType));
                    column.setColumnSize(columnSize);
                    column.setNullable(nullable);
                    column.setPrimaryKey(isPrimaryKey);
                    column.setRemarks(trimRemarks(remarks));
//                    column.setColumnDef(columnDef);
                    column.setFieldName(column.getFieldNameFromColumn(columnName));
                    columnList.add(column);
                }
                if (!columnList.isEmpty()) {
                    table.setColumns(columnList);
                }
                columnRS.close();
            }
        }
        release(conn);
        return tableList;
    }

    /**
     * 从url中获取数据库名
     *
     * @param jdbcUrl
     * @return
     */
    private String getDbName(String jdbcUrl) {
        if (!isEmpty(jdbcUrl)) {
            int index = jdbcUrl.indexOf("//");
            if (index >= 0) {
                String tmp = jdbcUrl.substring(index + 2);
                index = index + 2 + tmp.indexOf("/");
            }
            int endIndex = jdbcUrl.indexOf("?");
            if (index > 0 && endIndex > 0) {
                return jdbcUrl.substring(index + 1, endIndex);
            } else if (index > 0 && endIndex < 0) {
                return jdbcUrl.substring(index);
            } else {
                return "";
            }
        }
        return "";
    }

    /**
     * 数据库类型映射
     *
     * @param type
     * @return
     */
    private Class<?> getType(int type) {
        Class<?> clz = null;
        switch (type) {
            // 字符类型
            case Types.CLOB:
//            case Types.NCLOB:
            case Types.CHAR:
            case Types.VARCHAR:
//            case Types.NVARCHAR:
//            case Types.LONGNVARCHAR:
            case Types.LONGVARCHAR:
                clz = String.class;
                break;
            // 数字类型
            case Types.NUMERIC:
            case Types.DECIMAL:
                clz = Number.class;
                break;
            case Types.BIT:
            case Types.BOOLEAN:
                clz = Boolean.class;
                break;
            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
                clz = Integer.class;
                break;
            case Types.VARBINARY:
            case Types.BIGINT:
                clz = Long.class;
                break;
            case Types.REAL:
                clz = Float.class;
                break;
            case Types.FLOAT:
            case Types.DOUBLE:
                clz = Double.class;
                break;
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
                clz = Date.class;
                break;
            // 其他
            default:
                clz = Object.class;
        }
        return clz;
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    /**
     * 注释处理
     *
     * @param remarks
     * @return
     */
    private String trimRemarks(String remarks) {
        if (remarks == null) {
            return null;
        }
        remarks = remarks.replace("\r\n", " ").trim();
        return remarks;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取数据库连接
     *
     * @return
     * @throws Exception
     */
    private Connection connect() throws Exception {
        if (dataSource == null) {
            throw new Exception("没有初始化数据源");
        }
        return dataSource.getConnection();
    }

    /**
     * 释放数据库连接，失败后不抛出异常
     *
     * @param connection
     */
    private void release(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
