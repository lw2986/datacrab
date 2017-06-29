package com.lw.datacrab.db.criterion;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 *
 * @param <T>
 * @author lw
 * @date 2015-4-17 11:32:38
 */
public class Page<T> implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    //数据总行数
    private long amount = 0L;
    private long totalCount = 0L;

    //总页数
    private long pageCount;

    //数据集
    private List<T> data = null;
    private List<T> result = null;

    //当前页码
    private long curPage = 1;
    private long currentPage = 1;
    private long pageNo = 1;

    //分页大小
    private int size = 10;
    private int pageSize = 10;

    /*-----------------------------------------------------*/
    /*           构建page对象                              */
    /*-----------------------------------------------------*/
    public Page() {
    }

    public Page(long curPage) {
        this.curPage = curPage;
    }

    public Page(long curPage, int pageSize) {
        this.curPage = curPage;
        this.size = pageSize;
    }

    /**
     * 创建Page对象
     *
     * @param curPage 当前页
     * @return
     */
    public static Page set(long curPage) {
        return new Page(curPage);
    }

    /**
     * 创建Page对象
     *
     * @param curPage
     * @param curPage
     * @return
     */
    public static Page build(long curPage, int pageSize) {
        return new Page(curPage, pageSize);
    }

    /**
     * 创建Page对象
     *
     * @param curPage
     * @param pageSize
     * @param amount
     * @return
     */
    public static Page build(long curPage, int pageSize, long amount) {
        Page page = new Page();
        page.curPage = curPage;
        page.pageSize = pageSize;
        page.amount = amount;
        return page;
    }

    /*-----------------------------------------------------*/
    /*         参数设置和获取                              */
    /*-----------------------------------------------------*/

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getTotalCount() {
        return getAmount();
    }

    public void setTotalCount(long totalCount) {
        setAmount(totalCount);
    }

    /**
     * 取总页数
     *
     * @return
     */
    public long getPageCount() {
        if (amount % size == 0) {
            return amount / size;
        } else {
            return amount / size + 1;
        }
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getResult() {
        return getData();
    }

    public void setResult(List<T> result) {
        setData(result);
    }

    public long getCurPage() {
        if (curPage > 0) {
            return curPage;
        }
        return 1;
    }

    public void setCurPage(long curPage) {
        this.curPage = curPage;
    }

    public long getCurrentPage() {
        return getCurPage();
    }

    public void setCurrentPage(long currentPage) {
        setCurPage(currentPage);
    }

    public long getPageNo() {
        return getCurPage();
    }

    public void setPageNo(long pageNo) {
        setCurPage(pageNo);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPageSize() {
        return getSize();
    }

    public void setPageSize(int pageSize) {
        setSize(pageSize);
    }

    /*-----------------------------------------------------*/
    /*           分页导航参数                              */
    /*-----------------------------------------------------*/

    /**
     * 获取开始索引
     *
     * @return
     */
    public long getStartIndex() {
        return (getCurPage() - 1) * this.size;
    }

    /**
     * 获取开始索引
     *
     * @return
     */
    public long getOffset() {
        return getStartIndex();
    }

    /**
     * 获取结束索引
     *
     * @return
     */
    public long getEndIndex() {
        return getCurPage() * this.size;
    }

    /**
     * 是否第一页
     *
     * @return
     */
    public boolean isFirstPage() {
        return getCurPage() <= 1;
    }

    /**
     * 是否末页
     *
     * @return
     */
    public boolean isLastPage() {
        return getCurPage() >= getPageCount();
    }

    /**
     * 该页是否有下一页
     *
     * @return
     */
    public boolean hasNextPage() {
        return getCurPage() < getPageCount();
    }

    /**
     * 该页是否有上一页
     *
     * @return
     */
    public boolean hasPreviousPage() {
        return getCurPage() > 1;
    }

    /**
     * 获取上一页页码
     *
     * @return
     */
    public long getPreviousPage() {
        if (isFirstPage()) {
            return 1;
        }
        return getCurPage() - 1;
    }

    /**
     * 获取下一页页码
     *
     * @return
     */
    public long getNextPage() {
        if (isLastPage()) {
            return getCurrentPage();
        }
        return getCurPage() + 1;
    }

    /*-----------------------------------------------------*/
    /*           扩展字段                                  */
    /*-----------------------------------------------------*/

    //单位
    private String unit = "条";

    //扩展信息
    private String extInfo;

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    public String getExtInfo() {
        return extInfo;
    }

    @Override
    protected Page<T> clone() throws CloneNotSupportedException {
        return (Page<T>) super.clone();
    }
}
