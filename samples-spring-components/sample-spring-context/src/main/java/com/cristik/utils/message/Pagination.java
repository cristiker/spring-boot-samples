package com.cristik.utils.message;

import java.util.List;

/**
 * @author cristik
 */
public class Pagination<T, Q> {

    public static final String KEY_PAGINATION = "page";

    public static final int DEFAULT_PAGESIZE = 10;

    private Integer pageNo;

    private Integer pageSize;

    private Integer start;

    private Integer total;

    private Integer totalPage;

    private List<T> data;

    private Q query;

    public Pagination() {
        this.pageNo = 1;
        this.pageSize = DEFAULT_PAGESIZE;
    }

    public Pagination(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo != null ? pageNo : 1;
        this.pageSize = pageSize != null ? pageSize : DEFAULT_PAGESIZE;
        this.start = (this.pageNo - 1) * this.pageSize;
    }

    public Pagination(Integer pageNo, Integer pageSize, Q query) {
        this(pageNo, pageSize);
        this.query = query;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
        this.totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Q getQuery() {
        return query;
    }

    public void setQuery(Q query) {
        this.query = query;
    }
}
