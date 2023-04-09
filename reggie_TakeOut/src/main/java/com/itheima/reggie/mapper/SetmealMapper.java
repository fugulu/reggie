package com.itheima.reggie.mapper;

import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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
     * 添加套餐数据，并且获取新加主键值
     * is_deleted设置为0
     */
    @Insert("INSERT INTO setmeal VALUES (null,#{categoryId},#{name},#{price},#{status},#{code},#{description},#{image},#{createTime},#{updateTime},#{createUser},#{updateUser},0)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(SetmealDto setmealDto);

    /**
     * 通过套餐名字，查询套餐和分类名字
     */
    List<SetmealDto> findByName(@Param("name") String name);

    /**
     * 删除前判断这些套餐是否在销售 (统计所有即将删除的套餐中status为1的数量)
     * @param ids 要删除的id数组
     * @param status 状态1或0
     */
    long findSetmealCountByStatus(@Param("ids") Long[] ids, @Param("status") Integer status);

    /**
     * 删除多个套餐
     */
    int deleteByIds(@Param("ids") Long[] ids);

    /**
     * 根据套餐的类别展示套餐
     */
    @Select("select * from setmeal where category_id=#{categoryId} and status=#{status}")
    List<Setmeal> list(@Param("categoryId") Long categoryId, @Param("status") Integer status);
}
