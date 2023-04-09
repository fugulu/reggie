package com.itheima.reggie.service.impl;

import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Page;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import com.itheima.reggie.mapper.SetmealDishMapper;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;  //套餐持久层

    @Autowired
    private SetmealDishMapper setmealDishMapper;  //套餐菜品持久层

    /**
     * 添加套餐和菜品的数据
     * 注：要开启事务
     * @param setmealDto
     */
    @Override
    @Transactional  //开启事务
    @CacheEvict(cacheNames = "setmeal", allEntries = true)
    public void saveWithDish(SetmealDto setmealDto) {
        //1.添加套餐
        setmealMapper.save(setmealDto);
        //2.获取多条套餐菜品信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //3.遍历添加每一条套餐菜品信息
        setmealDishes.forEach(setmealDish -> {
            //设置外键，从id中获取
            setmealDish.setSetmealId(setmealDto.getId());
            //添加sort值
            setmealDish.setSort(Math.abs(new Random().nextInt()));
            //保存到数据库
            setmealDishMapper.save(setmealDish);
        });
    }

    /**
     * 分页查询
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @param name 套餐名
     */
    @Override
    public Page<SetmealDto> findByPage(Integer pageNum, Integer pageSize, String name) {
        //查询所有套餐
        List<SetmealDto> list = setmealMapper.findByName(name);
        //封装成Page对象
        return new Page<>(list);
    }

    /**
     * 批量删除套餐
     * 要开启事务
     * @param ids
     */
    @Override
    @Transactional  //开启事务
    @CacheEvict(cacheNames = "setmeal", allEntries = true)
    public void removeWithIds(Long[] ids) {
        //1.删除前判断这些套餐是否在销售
        long count = setmealMapper.findSetmealCountByStatus(ids, 1);
        //如果数量大于0的，则删除失败
        if (count > 0) {
            throw new CustomException("要删除的套餐中有在销售的，删除失败");
        }
        //2.删除套餐对应的套餐菜品表中所有记录
        setmealDishMapper.deleteBySetmealIds(ids);
        //3.删除多个套餐
        setmealMapper.deleteByIds(ids);
    }

    /**
     * 根据套餐的类别展示套餐
     * @param categoryId    分类id
     * @param status    状态
     */
    @Override
    @Cacheable(cacheNames = "setmeal", key = "#categoryId+'_'+#status")
    public List<Setmeal> list(Long categoryId, Integer status) {
        return setmealMapper.list(categoryId, status);
    }

    /**
     * 通过套餐id查询对应的菜品
     * @param setmealId
     */
    @Override
    public List<Map<String, Object>> findSetmealDish(Long setmealId) {
        return setmealDishMapper.findSetmealDish(setmealId);
    }
}
