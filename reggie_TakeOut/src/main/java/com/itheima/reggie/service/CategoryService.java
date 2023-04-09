package com.itheima.reggie.service;

import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Page;

import java.util.List;

/**
 * 分类业务层
 */
public interface CategoryService {

    /**
     * 保存分类
     * @param category
     */
    void save(Category category);

    /**
     * 分页查询
     * @param pageNum 页码
     * @param pageSize 页面大小
     */
    Page<Category> findByPage(Integer pageNum, Integer pageSize);

    /**
     * 通过id删除菜品
     */
    void deleteById(Long id);

    /**
     * 更新
     */
    void update(Category category);

    /**
     * 查询所有分类
     */
    List<Category> findAll();

    /**
     * 根据type查询分类
     * 1 菜品
     * 2 套餐
     */
    List<Category> list(Integer type);
}
