package com.itheima.reggie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Page;
import com.itheima.reggie.entity.SetmealDish;
import com.itheima.reggie.mapper.SetmealDishMapper;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * @Projectname: reggie
 * @Filename: SetmealServiceImpl
 * @Author: Steven
 * @Data:2022-10-12 18:19
 */

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增套餐
     * @param setmealDto
     */
    @Override
    @Transactional  //开启事务
    public void saveWithDish(SetmealDto setmealDto) {
        //设置一下查询编码
        setmealDto.setCode("00000");
        //1.    添加套餐数据，获取主键
        setmealMapper.save(setmealDto);
        //2.    获取套餐菜品的集合
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //3.    遍历每个套餐菜品的数据，设置套餐的外键
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealDto.getId());
            //3.1   设置随机排序
            setmealDish.setSort(Math.abs(new Random().nextInt()));
            //4.    保存到套餐菜品表中
            setmealDishMapper.save(setmealDish);
        });
    }

    /**
     * 分页显示套餐列表
     * @param pageNum  当前页
     * @param pageSize 页面叶
     * @param name     菜品的名称
     * @return
     */
    @Override
    public Page<SetmealDto> findPage(Integer pageNum, Integer pageSize, String name) {
        //1.设置当前页和页面的大小
        PageHelper.startPage(pageNum, pageSize);
        //2.根据名字查询数据，得到list集合
        List<SetmealDto> setmeals = setmealMapper.findByName(name);
        //3.将查询到的list集合封装到PageInfo中
        PageInfo<SetmealDto> pageInfo = new PageInfo<>(setmeals);
        //4.创建page对象，设置属性，返回
        return new Page<>(pageInfo.getList(), pageInfo.getTotal(), pageSize, pageNum);
    }

    /**
     * 批量删除
     * @param ids 要删除的套餐的id
     */
    @Override
    @Transactional  //开启事务
    public void removeWithIds(Long[] ids) {
        //1.查询所有待删除的套餐状态是1的数量
        long count = setmealMapper.findSetmealCountByStatus(ids, 1);
        //  如果数量大于0，则删除失败
        if(count > 0){
            throw new CustomException("选中的套餐有在售的，删除失败");
        }
        //2.删除套餐对应的套餐菜品表
        setmealDishMapper.deleteBySetmealIds(ids);
        //3.如果都是停售的状态则直接删除
        setmealMapper.deleteByIds(ids);
    }

    /**
     * 单个/批量修改套餐销售状态
     * @param ids
     * @param status
     */
    @Override
    @Transactional  //开启事务
    public void changeStatus(Long[] ids, Integer status) {
        for (Long id : ids) {
            SetmealDto setmealDto = new SetmealDto();
            setmealDto.setId(id);
            setmealDto.setStatus(status);
            //调用业务层更新
            setmealMapper.updateById(setmealDto);
        }
    }
}
