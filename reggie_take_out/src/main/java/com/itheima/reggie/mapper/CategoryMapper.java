package com.itheima.reggie.mapper;

import com.itheima.reggie.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 分类持久层接口
 */
public interface CategoryMapper {

    /**
     * 新增分类
     */
    @Insert("INSERT INTO category VALUES (null, #{type}, #{name}, #{sort}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void save(Category category);

    /**
     * 查询所有分类
     */
    @Select("SELECT * FROM category ORDER BY sort")
    List<Category> findAll();

    /**
     * 通过分类id删除一条记录
     */
    @Delete("delete from category where id=#{id}")
    void deleteById(Long id);

    /**
     * 更新菜品
     */
    @Update("update category set name=#{name},sort=#{sort},update_time=#{updateTime},update_user=#{updateUser} where id=#{id}")
    void update(Category category);

    /**
     * 通过类型查询对应的分类
     */
    List<Category> list(@Param("type") Integer type);
}
