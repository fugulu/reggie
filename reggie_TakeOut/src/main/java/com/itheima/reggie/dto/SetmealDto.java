package com.itheima.reggie.dto;

import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ApiModel("套餐数据传输对象")
@ToString(callSuper = true) //显示父类继承的属性
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;  //多个菜品信息
    private String categoryName;  //分类的名字
}
