package com.ai.user.common;

import lombok.Data;

import java.util.List;

/**
 * 分页结果包装
 */
@Data
public class PageResult<T> {

    /** 总记录数 */
    private long total;

    /** 页码 */
    private int pageNum;

    /** 每页条数 */
    private int pageSize;

    /** 数据列表 */
    private List<T> list;

    public PageResult() {
    }

    public PageResult(long total, int pageNum, int pageSize, List<T> list) {
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.list = list;
    }
}