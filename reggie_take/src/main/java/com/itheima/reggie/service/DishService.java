package com.itheima.reggie.service;

import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Page;

import java.util.List;

/**
 * @Projectname: reggie
 * @Filename: DishService
 * @Author: Steven
 * @Data:2022-10-12 11:08
 */
public interface DishService {
    /**
     * 调用数据层保存一条菜品和多条口味信息
     * @param dishDto 用于接收菜品与口味信息
     */
    void saveWithFlavor(DishDto dishDto);

    /**
     * 分页查询菜品
     * @param pageNum 第几页
     * @param pageSize 每页大小
     * @param name 查询条件
     * @return 自定义的分页对象
     */
    Page<DishDto> findPage(Integer pageNum, Integer pageSize, String name);

    /**
     * 根据id查找菜品和菜品的口味
     * @param id 菜品id
     * @return
     */
    DishDto findById(Long id);

    /**
     * 修改菜品
     * @param dishDto 页面传递过来的参数 包含菜品和口味
     */
    void updateWithFlavor(DishDto dishDto);

    /**
     * 修改单个(批量)销售状态
     * @param ids 更新的id
     * @param status 状态 0：停售  1：起售
     */
    void changeStatus(Long[] ids, Integer status);

    /**
     * 通过id删除
     * @param ids
     */
    void deleteById(Long[] ids);

    /**
     * 通过分类id或菜品名字，查询菜品，只查询启用的菜品
     */
    List<Dish> findByCategoryId(Long categoryId, String name);
}
