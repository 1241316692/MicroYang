package com.zuoyue.weiyang.bean;

import io.swagger.annotations.ApiParam;

public class PageParam {

    @ApiParam(value = "当前页", defaultValue = "1")
    private Integer page_num = 1;
    @ApiParam(value = "每页大小", defaultValue = "20")
    private Integer page_size =10;
    @ApiParam(value = "关键字")
    private String keyword;

    public Integer getPage_num() {
        return page_num;
    }

    public void setPage_num(Integer page_num) {
        this.page_num = page_num;
    }

    public Integer getPage_size() {
        return page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }

    public String getKeyword() {
        return keyword;
    }

    public PageParam setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }
}
