package com.itheima.reggie.service.impl;

import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.entity.Page;
import com.itheima.reggie.mapper.DishFlavorMapper;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加菜品和多个口味信息
     * 注：要开启事务
     * @param dishDto 封装了一个菜品和多个口味数据
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //1.补充菜品表中其它的字段
        dishDto.setCode("0000");  //code提交的是空字符串，这里用不上
        dishDto.setSort(Math.abs(new Random().nextInt()));  //随机生成一个正整数
        //2.调用持久层添加菜品信息
        dishMapper.save(dishDto);
        //3.添加多个口味信息
        List<DishFlavor> flavors = dishDto.getFlavors();
        //遍历集合
        for (DishFlavor flavor : flavors) {
            //添加外键的值
            flavor.setDishId(dishDto.getId());  //使用新添加的主键值
            //写到数据库中
            dishFlavorMapper.save(flavor);
        }

        log.info("添加菜品和口味信息：{}", dishDto);
        //  清空redis缓存
        Set<String> keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
    }

    /**
     * 分页查询菜品
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @param name 菜品名字
     */
    @Override
    public Page<DishDto> findByPage(Integer pageNum, Integer pageSize, String name) {
        //使用AOP实现分页功能
        List<DishDto> dishDtos = dishMapper.findByName(name);
        return new Page<>(dishDtos);
    }

    /**
     * 通过id查询菜品和口味信息
     * @param id
     */
    @Override
    public DishDto findById(Long id) {
        //1.通过id查询菜品
        Dish dish = dishMapper.findById(id);
        //2.查询对应口味信息
        List<DishFlavor> flavors = dishFlavorMapper.findByDishId(id);
        //3.封装成DishDto
        DishDto dishDto = new DishDto();
        //将dish中所有的值复制到dishDto中同名的属性中
        BeanUtils.copyProperties(dish, dishDto);  //源 -> 目标
        //设置口味信息
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    /**
     * 通过id修改菜品和口味信息
     * @param dishDto
     */
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //1.修改菜品信息
        dishMapper.updateById(dishDto);
        //2.删除原有口味信息
        dishFlavorMapper.deleteByDishId(dishDto.getId());
        //3.添加新的口味信息
        //获取所有口味信息
        List<DishFlavor> flavors = dishDto.getFlavors();
        //遍历集合
        flavors.forEach(dishFlavor -> {
            //设置口味对应的菜品id
            dishFlavor.setDishId(dishDto.getId());
            //调用口味添加的方法写到数据库中
            dishFlavorMapper.save(dishFlavor);
        });
    }

    /**
     * 通过分类id或菜品名字，查询菜品，只查询启用的菜品
     */
    @Override
    public List<Dish> findByCategoryId(Long categoryId, String name) {
        //查询启用菜品
        return dishMapper.findByCategoryId(categoryId, name, 1);
    }

    /**
     * 通过分类id和状态查询菜品和口味信息
     * @param categoryId
     * @param status
     */
    @Override
    public List<DishDto> findDishWithFlavor(Long categoryId, Integer status) {
        //  1.通过key从redis中获取值
        String key = "dish_" + categoryId + "_" +  status;
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        //  2.如果为空，则从数据库中查询
        List<DishDto> dishDtos = (List<DishDto>) ops.get(key);
        if(dishDtos == null){
            //  2.1 根据菜品类别的id和状态查找菜品
            dishDtos = dishMapper.findDishWithFlavor(categoryId, status);
            //  2.2 并且向redis中保存一份，保留1天
            ops.set(key, dishDtos,1, TimeUnit.DAYS);
        }
        //  3.返回List<DishDto>集合
        return dishDtos;
    }
}
