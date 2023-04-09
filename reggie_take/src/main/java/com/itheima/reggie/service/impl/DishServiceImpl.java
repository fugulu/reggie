package com.itheima.reggie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.entity.Page;
import com.itheima.reggie.mapper.DishFlavorMapper;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * @Projectname: reggie
 * @Filename: DishServiceImpl
 * @Author: Steven
 * @Data:2022-10-12 11:17
 */

/**
 * 菜品的业务实现类
 */
@Service
public class DishServiceImpl implements DishService {
    /**
     * 调用数据层保存一条菜品和多条口味信息
     * @param dishDto 用于接收菜品与口味信息
     */
    @Autowired
    private DishMapper dishMapper;  //菜品数据层

    @Autowired
    private DishFlavorMapper dishFlavorMapper;      //口味数据层

    /**
     * 添加菜品和口味信息
     */
    @Override
    //注：如果有多条增删改的操作要访问数据库，要使用事务
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //1.添加菜品信息
        //1.1   填充code字段，没有用到的，填充6个0，字符串类型
        dishDto.setCode("000000");
        //1.2   随机生成一个排序字段，生成一个正整数
        dishDto.setSort(Math.abs(new Random().nextInt()));
        //1.3   添加到菜品表中
        dishMapper.save(dishDto);

        //2.添加多条口味信息
        //2.1   获取多条口味信息
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            //2.2   需要设置口味的外键，新添加的菜品主键值
            flavor.setDishId(dishDto.getId());
            //添加到口味表中
            dishFlavorMapper.save(flavor);
        }
    }

    /**
     * 分页查询菜品
     * @param pageNum  第几页
     * @param pageSize 每页大小
     * @param name     查询条件
     * @return 自定义的分页对象
     */
    @Override
    public Page<DishDto> findPage(Integer pageNum, Integer pageSize, String name) {
        //1.设置当前页和每页大小
        PageHelper.startPage(pageNum, pageSize);
        //2.调用数据层查找页面的数据
        List<DishDto> dishes = dishMapper.findByName(name);
        //3.构建pageInfo对象
        PageInfo<DishDto> pageInfo = new PageInfo<>(dishes);
        //4.返回封装后的Page对象数据
        return new Page<>(dishes, pageInfo.getTotal(), pageSize, pageNum);
    }

    /**
     * 根据id查找菜品和菜品的口味
     * @param id 菜品id
     * @return
     */
    @Override
    public DishDto findById(Long id) {
        //1.根据id查找菜品
        Dish dish = dishMapper.findById(id);
        //2.根据菜品的id查找菜品对应的口味
        List<DishFlavor> dishFlavorList = dishFlavorMapper.findByDishId(id);
        //3.创建DishDto
        DishDto dishDto = new DishDto();
        //3.1   把菜品信息复制到DishDto里面
        BeanUtils.copyProperties(dish, dishDto);
        //4.再设置对应的口味信息
        dishDto.setFlavors(dishFlavorList);
        //5.返回DishDto对象
        return dishDto;
    }

    /**
     * 修改菜品
     * @param dishDto 页面传递过来的参数 包含菜品和口味
     */
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //1.调用数据层修改菜品
        dishMapper.updateById(dishDto);
        //2.调用口味的持久层方法 删除该菜品所有的口味信息
        dishFlavorMapper.deleteByDishId(dishDto.getId());
        //3.获取所有的口味信息，重新添加
        List<DishFlavor> flavors = dishDto.getFlavors();
        //4.循环多条口味信息
        flavors.forEach(flavor -> {
            //4.1   设置口味的id为dishDto的id
            flavor.setDishId(dishDto.getId());
            //4.2   调用添加口味信息自动填充通用属性
            dishFlavorMapper.save(flavor);
        });
    }

    /**
     * 修改单个(批量)销售状态
     * @param ids    更新的id
     * @param status 状态 0：停售  1：起售
     */
    @Override
    @Transactional  //开启事务
    public void changeStatus(Long[] ids, Integer status) {
        for (Long id : ids) {
            DishDto dishDto = new DishDto();
            dishDto.setId(id);
            dishDto.setStatus(status);
            //调用dao更新
            dishMapper.updateById(dishDto);
        }
    }

    /**
     * 通过id删除
     * @param ids
     */
    @Override
    @Transactional  //开启事务
    public void deleteById(Long[] ids) {
        for (Long id : ids) {
            //1.删除前需要先判断菜品的状态，如果是1(在售)则不能删除
            Dish dish = dishMapper.findById(id);
            if(dish.getStatus() == 1){
                throw new CustomException("菜品处于启售状态不能删除");
            }
            //2.先删除对应的口味
            dishFlavorMapper.deleteByDishId(id);
            //3.再删除菜品
            dishMapper.deleteById(id);
        }
    }

    /**
     * 通过分类id或菜品名字，查询菜品，只查询启用的菜品
     */
    @Override
    public List<Dish> findByCategoryId(Long categoryId, String name) {
        //查询启用菜品
        return dishMapper.findByCategoryId(categoryId, name, 1);
    }
}
