package com.lw.datacrab.db.service;

import com.lw.datacrab.db.criterion.DbQueryCriterion;
import com.lw.datacrab.db.criterion.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据库读写分离service基类
 *
 * @param <T>  entity类
 * @param <ID> ID类
 * @author lw
 * @date 2015-4-20 2:11:11
 */
public abstract class DbWRSeparateService<T, ID extends Number> extends DbBaseService<T, ID> {

    /*-----------------------------------------------------*/
    /*           以下方法在读写分离时查询从库              */
    /*-----------------------------------------------------*/

    /**
     * 查询数量
     *
     * @param criterion
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public long getCountReadOnly(DbQueryCriterion criterion) throws Exception {
        return getDao().selectCount(criterion);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public T getByIdReadOnly(ID id) throws Exception {
        return getDao().selectById(id);
    }

    /**
     * 查询一条
     *
     * @param criterion
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public T getOneReadOnly(DbQueryCriterion criterion) throws Exception {
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
    @Transactional(readOnly = true)
    public List<T> getListReadOnly(DbQueryCriterion criterion) throws Exception {
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
    @Transactional(readOnly = true)
    public Page<T> getPageReadOnly(DbQueryCriterion criterion) throws Exception {
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
    @Transactional(readOnly = true)
    public Page<T> getPageReadOnly(DbQueryCriterion criterion, Page page) throws Exception {
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

}
