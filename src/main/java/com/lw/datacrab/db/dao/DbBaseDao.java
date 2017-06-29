package com.lw.datacrab.db.dao;

import com.lw.datacrab.db.criterion.DbQueryCriterion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据库访问接口
 *
 * @param <T>  entity类
 * @param <ID> ID类
 * @author lw
 */
public interface DbBaseDao<T, ID extends Number> {

    /**
     * 查询数量
     *
     * @param criterion
     * @return
     * @throws Exception
     */
    long selectCount(DbQueryCriterion criterion) throws Exception;

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    T selectById(ID id) throws Exception;

    /**
     * 查询列表
     *
     * @param criterion
     * @return
     * @throws Exception
     */
    List<T> selectList(DbQueryCriterion criterion) throws Exception;

    /**
     * 根据主键更新一条
     *
     * @param t
     * @return
     * @throws Exception
     */
    int updateById(@Param("t") T t) throws Exception;

    /**
     * 批量更新
     *
     * @param t
     * @param criterion
     * @return
     * @throws Exception
     */
    int updateList(@Param("t") T t, @Param("criterion") DbQueryCriterion criterion) throws Exception;

    /**
     * 新增一条
     *
     * @param t
     * @return
     * @throws Exception
     */
    long insert(T t) throws Exception;

    /**
     * 根据主键删除一条
     *
     * @param id
     * @return
     * @throws Exception
     */
    int deleteById(ID id) throws Exception;

    /**
     * 批量删除
     *
     * @param criterion
     * @return
     * @throws Exception
     */
    int deleteList(DbQueryCriterion criterion) throws Exception;

}
