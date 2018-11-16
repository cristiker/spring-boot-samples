package com.cristik.common.message;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author cristik on 2016/4/20.
 */
public class PageInfo {

    public static final Integer PAGE_LENGTH = 10;

    private Integer start;

    private Integer length;

    private Integer draw;

    private Integer recordsTotal;

    private Integer recordsFiltered;

    private Integer pageNum;

    private Integer pageSize;

    private List<Object> data;

    private Object param;

    private Long total;

    private Integer totalPage;

    private List<Map<Order, String>> order;

    private List<Map<Column, String>> columns;

    private String sortName;

    private String sortType;

    public enum Order {
        column, dir
    }

    public enum Column {
        data,
        name,
        searchable,
        orderable
    }

    public PageInfo(){
        if(pageNum==null){
            pageNum = 1;
        }
        if(pageSize==null){
            pageSize= PAGE_LENGTH;
        }
    }

    public PageInfo(Integer start, Integer length,Integer draw) {
        this.pageNum = (int)Math.floor(start/length+1);
        this.pageSize = length;
        this.draw = draw;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getRecordsFiltered() {
        return this.recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public Integer getRecordsTotal() {
        return this.recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<Map<Order, String>> getOrder() {
        return order;
    }

    public void setOrder(List<Map<Order, String>> order) {
        this.order = order;
    }

    public List<Map<Column, String>> getColumns() {
        return columns;
    }

    public void setColumns(List<Map<Column, String>> columns) {
        this.columns = columns;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "start=" + start +
                ", length=" + length +
                ", draw=" + draw +
                ", recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", data=" + data +
                ", param=" + param +
                ", total=" + total +
                ", totalPage=" + totalPage +
                ", order=" + order +
                ", columns=" + columns +
                ", sortName='" + sortName + '\'' +
                ", sortType='" + sortType + '\'' +
                '}';
    }

    public String toJSONString(){
        return JSONObject.toJSONString(this);
    }
}
