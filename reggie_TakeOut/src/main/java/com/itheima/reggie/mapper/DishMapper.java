package com.itheima.reggie.mapper;

import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜品持久层
 */
public interface DishMapper {

    /**
     * 通过分类查询菜品数量
     */
    @Select("SELECT COUNT(*) FROM dish WHERE category_id=#{categoryId}")
    long findDishCountByCategoryId(Long categoryId);

    /**
     * 添加1个菜品信息
     * 最后的is_delete字段直接设置为0
     * 需要获取新添加的主键值
     */
    @Insert("insert into  dish values(null,#{name},#{categoryId},#{price},#{code},#{image},#{description},#{status},#{sort},#{createTime},#{updateTime},#{createUser},#{updateUser},0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(DishDto dishDto);

    /**
     * 分页查询菜品和分类名字
     */
    List<DishDto> findByName(@Param("name") String name);

    /**
     * 通过id查询某个菜品
     */
    @Select("SELECT * FROM dish WHERE id=#{id}")
    Dish findById(Long id);

    /**
     * 更新菜品信息
     */
    int updateById(DishDto dishDto);

    /**
     * 通过分类id或菜品名字，查询菜品，查询指定状态的菜品
     */
    List<Dish> findByCategoryId(@Param("categoryId") Long categoryId, @Param("name") String name, @Param("status") Integer status);

    /**
     * 通过分类id和状态查询菜品和品位信息
     */
    List<DishDto> findDishWithFlavor(@Param("categoryId") Long categoryId, @Param("status") Integer status);
}
