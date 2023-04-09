package com.itheima.reggie.dto;

import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 同时包含了菜品和口味的信息
 */
@Data
@ToString(callSuper = true)  //显示父类的属性
public class DishDto extends Dish {
    private List<DishFlavor> flavors = new ArrayList<>();  //口味信息
	
    private String categoryName;  //所属分类的名字
    private Integer copies;   //份数
}