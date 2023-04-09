package com.itheima.reggie.mapper;

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
     * 最后的is_delete字段直接设置为0
     */
    @Insert("insert into dish_flavor values (NULL,#{dishId},#{name},#{value},#{createTime},#{updateTime},#{createUser},#{updateUser},0)")
    void save(DishFlavor dishFlavor);

    /**
     * 通过dish_id查询对应的多个口味信息
     */
    @Select("SELECT * FROM dish_flavor WHERE dish_id=#{dishId}")
    List<DishFlavor> findByDishId(Long dishId);

    /**
     * 通过dish_id删除对应的口味
     */
    @Delete("DELETE FROM dish_flavor WHERE dish_id=#{dishId}")
    int deleteByDishId(Long dishId);
}
