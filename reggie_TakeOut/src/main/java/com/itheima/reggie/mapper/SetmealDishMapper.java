package com.itheima.reggie.mapper;

import com.itheima.reggie.entity.SetmealDish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 套餐-菜品表 持久层
 */
public interface SetmealDishMapper {

    /**
     * 添加1个套餐菜品信息
     */
    @Insert("insert into setmeal_dish values (null,#{setmealId},#{dishId},#{name},#{price},#{copies},#{sort},#{createTime},#{updateTime},#{createUser},#{updateUser},0)")
    void save(SetmealDish setmealDish);

    /**
     * 删除套餐对应的套餐菜品表中所有记录
     */
    int deleteBySetmealIds(@Param("ids") Long[] ids);

    /**
     * 通过套餐id查询相应的菜品id
     * 套餐项和菜品一对一查询
     */
    @Select("SELECT * FROM setmeal_dish s INNER JOIN dish d ON s.dish_id = d.id WHERE setmeal_id = #{setmealId}")
    List<Map<String,Object>> findSetmealDish(Long setmealId);
}
