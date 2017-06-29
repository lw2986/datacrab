package com.lw.datacrab.db.service;

import com.lw.datacrab.db.dao.DbBaseDao;
import com.lw.datacrab.db.criterion.Page;
import com.lw.datacrab.db.criterion.DbQueryCriterion;

import java.util.List;

/**
 * 数据库service基类
 *
 * @param <T>  entity类
 * @param <ID> ID类
 * @author lw
 * @date 2015-4-20 14:11:11
 */
public abstract class DbBaseService<T, ID extends Number> {

    /**
     * dao接口
     */
    protected abstract DbBaseDao<T, ID> getDao();

    /**
     * 查询数量
     *
     * @param criterion
     * @return
     * @throws Exception
     */
    public long getCount(DbQueryCriterion criterion) throws Exception {
        return getDao().selectCount(criterion);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    public T getById(ID id) throws Exception {
        return getDao().selectById(id);
    }

    /**
     * 查询一条
     *
     * @param criterion
     * @return
     * @throws Exception
     */
    public T getOne(DbQueryCriterion criterion) throws Exception {
        criterion.page(1, 1);
        List<T> list = getList(criterion);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询列表
     *
     * @param criterion
     * @return
     * @throws Exception
     */
    public List<T> getList(DbQueryCriterion criterion) throws Exception {
        return getDao().selectList(criterion);
    }

    /**
     * 分页查询，如果没有传入分页参数，默认查第1页前10条
     * 分页参数传入方法：
     * criterion.page(long pageNo, int pageSize)
     * criterion.page(long pageNo, int pageSize, long dataAmount)
     *
     * @param criterion
     * @return
     * @throws Exception
     */
    public Page<T> getPage(DbQueryCriterion criterion) throws Exception {
        Long amount = criterion.getDataAmount();
        Long pageNo = criterion.getPageNo();
        Integer pageSize = criterion.getPageSize();
        amount = amount == null ? 0L : amount;
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? 10 : pageSize;

        Page<T> page = new Page();
        page.setCurPage(pageNo);
        page.setPageSize(pageSize);

        criterion.page(pageNo, pageSize);
        //如果已传入总数,则不用重新查数量
        if (amount < 1L) {
            amount = getDao().selectCount(criterion);
        }
        if (amount > 0L) {
            page.setData(getDao().selectList(criterion));
        }
        page.setAmount(amount);
        return page;
    }

    /**
     * 分页查询
     *
     * @param criterion
     * @param page
     * @return
     * @throws Exception
     */
    public Page<T> getPage(DbQueryCriterion criterion, Page page) throws Exception {
        long amount = page.getAmount();
        criterion.page(page.getCurPage(), page.getSize());
        //如果已传入总数,则不用重新查数量
        if (amount < 1L) {
            amount = getDao().selectCount(criterion);
        }
        if (amount > 0L) {
            page.setData(getDao().selectList(criterion));
        }
        page.setAmount(amount);
        return page;
    }

    /**
     * 根据主键更新
     *
     * @param t
     * @return
     */
    public int updateById(T t) throws Exception {
        return getDao().updateById(t);
    }

    /**
     * 批量更新
     *
     * @param t
     * @param criterion
     * @return
     */
    public int updateList(T t, DbQueryCriterion criterion) throws Exception {
        return getDao().updateList(t, criterion);
    }

    /**
     * 新增一条
     *
     * @param t
     * @return
     */
    public long add(T t) throws Exception {
        return getDao().insert(t);
    }

    /**
     * 根据主键删除一条
     *
     * @param id
     * @return
     */
    public int deleteById(ID id) throws Exception {
        return getDao().deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param criterion
     * @return
     */
    public int deleteList(DbQueryCriterion criterion) throws Exception {
        return getDao().deleteList(criterion);
    }

}
