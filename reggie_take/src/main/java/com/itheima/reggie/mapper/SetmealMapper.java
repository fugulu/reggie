package com.itheima.reggie.mapper;

import com.itheima.reggie.dto.SetmealDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 套餐持久层
 */
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐数量
     */
    @Select("SELECT COUNT(*) FROM setmeal WHERE category_id=#{categoryId}")
    long findSetmealCountByCategoryId(Long categoryId);

    /**
     * 添加套餐，封装返回的主键id
     */
    @Insert("INSERT INTO setmeal VALUES (null,#{categoryId},#{name},#{price},#{status},#{code},#{description},#{image},#{createTime},#{updateTime},#{createUser},#{updateUser},0)")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void save(SetmealDto setmealDto);

    /**
     * 根据名字查找套餐
     * @param name
     * @return
     */
    List<SetmealDto> findByName(@Param("name") String name);

    /**
     * 查询所有删除的套餐中，起售/禁售的数量
     */
    long findSetmealCountByStatus(@Param("ids")Long[] ids, @Param("status") Integer status);

    /**
     * 根据套餐的id删除套餐
     */
    int deleteByIds(@Param("ids") Long[] ids);

    /**
     * 通过id更新
     */
    int updateById(SetmealDto setmealDto);
}
