package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Page;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dish")
@Slf4j
public class DishController extends BaseController {

    @Autowired
    private DishService dishService;

    /**
     * 添加菜品和口味
     * @param dishDto 同时封装菜品和口味
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("添加菜品成功");
    }

    /**
     * 分页查询
     */
    @GetMapping("page")
    public R<Page<DishDto>> findPage(@RequestParam("page") Integer pageNum, Integer pageSize, String name) {
        //调用业务层查询
        Page<DishDto> page = dishService.findByPage(pageNum, pageSize, name);
        return R.success(page);
    }

    /**
     * 通过id查询菜品和口味
     */
    @GetMapping("{id}")
    public R<DishDto> findById(@PathVariable Long id) {
        DishDto dishDto = dishService.findById(id);
        return R.success(dishDto);
    }

    /**
     * 修改菜品和口味
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
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

    /**
     * 根据菜品类别的id查找菜品和它的口味信息
     */
    @GetMapping("listFlavor")
    public R<List<DishDto>> listFlavor(Long categoryId, Integer status){
        List<DishDto> dishList = dishService.findDishWithFlavor(categoryId, status);
        return R.success(dishList);
    }
}
