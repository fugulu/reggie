package com.itheima.reggie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Page<T>  {
    private List<T> records;  //当前页的数据
    private long total;  //总记录数
    private long pageSize;  //页面大小
    private long page;  //当前页码
    private long pages; //总页数



    //加一个构造方法
    public Page(List<T> records) {
        this.records = records;
    }

    public Page(List<T> records, long total, long pageSize, long page) {
        this.records = records;
        this.total = total;
        this.pageSize = pageSize;
        this.page = page;
    }

    public Page(List<T> records, long total, long pageSize, long page, long pages) {
        this.records = records;
        this.total = total;
        this.pageSize = pageSize;
        this.page = page;
        this.pages = pages;
    }
}
