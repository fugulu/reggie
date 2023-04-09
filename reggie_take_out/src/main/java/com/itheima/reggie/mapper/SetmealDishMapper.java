package com.itheima.reggie.mapper;

import com.itheima.reggie.entity.SetmealDish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @Projectname: reggie
 * @Filename: SetmealDishMapper
 * @Author: Steven
 * @Data:2022-10-12 18:35
 */
public interface SetmealDishMapper {
    /**
     * 添加1个套餐菜品信息
     * 删除状态设置为0
     */
    @Insert("insert into setmeal_dish values (null,#{setmealId},#{dishId},#{name},#{price},#{copies},#{sort},#{createTime},#{updateTime},#{createUser},#{updateUser},0)")
    void save(SetmealDish setmealDish);

    /**
     * 删除套餐菜品表中外键是这些id的记录
     * @param ids   套餐的id
     * @return
     */
    int deleteBySetmealIds(@Param("ids") Long[] ids);
}
