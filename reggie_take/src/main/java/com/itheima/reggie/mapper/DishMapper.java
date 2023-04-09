package com.itheima.reggie.mapper;

import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import org.apache.ibatis.annotations.*;

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
     * 插入菜单，需要获取生成的主键
     */
    @Insert("insert into dish values(null,#{name},#{categoryId},#{price},#{code},#{image},#{description},#{status},#{sort},#{createTime},#{updateTime},#{createUser},#{updateUser},0)")
    //获取生成的主键，keyColumn指定表列名，keyProperty指定实体类的属性名
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void save(DishDto dishDto);

    /**
     * 按菜品的名字，分页查询菜品和分类名字，按修改时间的降序排序
     * @param name 菜品名字
     */
    List<DishDto> findByName(@Param("name") String name);

    /**
     * 根据id查找菜品
     */
    @Select("select * from dish where id=#{id}")
    Dish findById(Long id);

    /**
     * 通过id更新
     */
    int updateById(DishDto dishDto);

    /**
     * 通过id删除
     */
    @Delete("delete from dish where id=#{id}")
    void deleteById(Long id);

    /**
     * 通过分类id或菜品名字，查询菜品，查询指定状态的菜品
     */
    List<Dish> findByCategoryId(@Param("categoryId") Long categoryId, @Param("name") String name, @Param("status") Integer status);
}
