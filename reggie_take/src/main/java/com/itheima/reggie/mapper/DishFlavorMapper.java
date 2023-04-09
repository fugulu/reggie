package com.itheima.reggie.mapper;

/**
 * @Projectname: reggie
 * @Filename: DishFlavorMapper
 * @Author: Steven
 * @Data:2022-10-12 10:20
 */

import com.itheima.reggie.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 口味表持久层
 */
public interface DishFlavorMapper {
    /**
     * 保存口味信息
     */
    @Insert("insert into dish_flavor values (NULL,#{dishId},#{name},#{value},#{createTime},#{updateTime},#{createUser},#{updateUser},0)")
    void save(DishFlavor dishFlavor);

    /**
     * 通过dishId查找对应的口味
     */
    @Select("select * from dish_flavor where dish_id=#{dishId}")
    List<DishFlavor> findByDishId(Long dishId);

    /**
     * 通过dishId删除相应的口味
     */
    @Delete("delete from dish_flavor where dish_id=#{dishId}")
    int deleteByDishId(Long dishId);
}
