package com.itheima.reggie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Page;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;  //分类

    @Autowired
    private DishMapper dishMapper;  //菜品

    @Autowired
    private SetmealMapper setmealMapper;  //套餐

    /**
     * 保存分类
     * @param category
     */
    @Override
    public void save(Category category) {
        categoryMapper.save(category);
    }

    /**
     * 分页查询
     * @param pageNum 页码
     * @param pageSize 页面大小
     */
    @Override
    public Page<Category> findByPage(Integer pageNum, Integer pageSize) {
        //1.查询所有
        List<Category> categories = categoryMapper.findAll();
        //2.只封装一个参数
        return new Page<>(categories);
    }

    /**
     * 通过id删除菜品
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        //1. 查询当前类别是否关联有菜品 ，返回菜品的总数
        long count = dishMapper.findDishCountByCategoryId(id);
        //2. 如果总数大于0，则抛出自定义异常
        if (count > 0) {
            throw new CustomException("该分类下有菜品，不能删除");
        }

        //3. 查询当前套餐是否关联有菜品，返回菜品的总数
        count = setmealMapper.findSetmealCountByCategoryId(id);
        //4. 如果总数大于0，则抛出自定义异常
        if (count > 0) {
            throw new CustomException("该分类下有套餐，不能删除");
        }

        //5. 再通过id删除
        categoryMapper.deleteById(id);
    }

    /**
     * 更新
     * @param category
     */
    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    /**
     * 查询所有分类
     */
    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    /**
     * 根据type查询分类
     * 1 菜品
     * 2 套餐
     * @param type
     */
    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }
}
