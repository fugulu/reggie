package com.itheima.reggie.service;

import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜品业务类
 */
public interface DishService {

    /**
     * 添加菜品和多个口味信息
     * 注：要开启事务
     * @param dishDto 封装了一个菜品和多个口味数据
     */
    @Transactional
    void saveWithFlavor(DishDto dishDto);

    /**
     * 分页查询菜品
     */
    Page<DishDto> findByPage(Integer pageNum, Integer pageSize, String name);

    /**
     * 通过id查询菜品和口味信息
     */
    DishDto findById(Long id);

    /**
     * 通过id修改菜品和口味信息
     */
    @Transactional
    void updateWithFlavor(DishDto dishDto);

    /**
     * 通过分类id或菜品名字，查询菜品，只查询启用的菜品
     */
    List<Dish> findByCategoryId(Long categoryId, String name);

    /**
     * 通过分类id和状态查询菜品和口味信息
     */
    List<DishDto> findDishWithFlavor(Long categoryId, Integer status);
}
