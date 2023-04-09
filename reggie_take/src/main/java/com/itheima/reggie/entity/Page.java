package com.itheima.reggie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T>  {
    private List<T> records;  //当前页的数据
    private long total;  //总记录数
    private long pageSize;  //页面大小
    private long page;  //当前页码

    //加一个构造方法
    public Page(List<T> records) {
        this.records = records;
    }
}
