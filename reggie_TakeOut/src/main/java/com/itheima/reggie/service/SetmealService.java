package com.itheima.reggie.service;

import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Page;
import com.itheima.reggie.entity.Setmeal;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 套餐的业务接口
 */
public interface SetmealService {

    /**
     * 添加套餐和菜品的数据
     * 注：要开启事务
     */
    @Transactional
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 分页查询
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @param name 套餐名
     */
    Page<SetmealDto> findByPage(Integer pageNum, Integer pageSize, String name);

    /**
     * 批量删除套餐
     * 要开启事务
     */
    @Transactional
    void removeWithIds(Long[] ids);

    /**
     * 根据套餐的类别展示套餐
     */
    List<Setmeal> list(Long categoryId, Integer status);

    /**
     * 通过套餐id查询对应的菜品
     */
    List<Map<String, Object>> findSetmealDish(Long setmealId);
}
