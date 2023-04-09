package com.itheima.reggie.controller;

/**
 * @Projectname: reggie
 * @Filename: DishController
 * @Author: Steven
 * @Data:2022-10-12 11:45
 */

import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Page;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController extends BaseController{
    @Autowired
    private DishService dishService;

    /**
     * 保存菜品并且携带口味信息
     * @param dishDto 用于接收菜品与口味信息
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        //注：看不到Dish中的属性，要设置断点才可见
        log.info("添加菜品和口味信息：{}", dishDto);
        dishService.saveWithFlavor(dishDto);
        return R.success("添加菜品成功");
    }

    /**
     * 分页查询，因为页面提交的参数是page，形参名是pageNum
     */
    @GetMapping("/page")
    public R<Page<DishDto>> findPage(@RequestParam("page") Integer pageNum, Integer pageSize, String name){
        log.info("查询条件是：第{}页，每页{}条，菜品名字是：{}", pageNum, pageSize, name);
        Page<DishDto> page = dishService.findPage(pageNum, pageSize, name);
        return R.success(page);
    }

    /**
     * 根据id查找菜品和菜品的口味
     */
    @GetMapping("/{id}")
    public R<DishDto> findById(@PathVariable("id") Long id){
        DishDto dishDto = dishService.findById(id);
        return R.success(dishDto);
    }

    /**
     * 修改菜品
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        //更新菜品信息
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

    /**
     * 修改销售状态
     */
    @PostMapping("status/{status}")
    public R<String> updateStatus(@PathVariable Integer status, Long[] ids){
        //调用业务层更新
        dishService.changeStatus(ids, status);
        log.info("修改id为：{}的菜品，状态为：{}", ids, status);
        return R.success("菜品状态修改成功");
    }

    /**
     * 根据id删除
     */
    @DeleteMapping
    public R<String> delete(Long[] ids){
        //调用业务层更新
        dishService.deleteById(ids);
        log.info("删除菜品，id为：{}", ids);
        return R.success("删除菜品成功");
    }

    /**
     * 通过分类id或菜品名字查询菜品
     * @param categoryId 分类id
     * @param name 菜品名字，需要模糊查询
     * 以上2个参数是2选1
     */
    @GetMapping("list")
    public R<List<Dish>> findDishes(Long categoryId, String name) {
        List<Dish> dishes = dishService.findByCategoryId(categoryId, name);
        return R.success(dishes);
    }
}
