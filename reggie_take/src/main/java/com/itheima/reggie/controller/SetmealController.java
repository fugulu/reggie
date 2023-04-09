package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Page;
import com.itheima.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Projectname: reggie
 * @Filename: SetmealController
 * @Author: Steven
 * @Data:2022-10-12 18:38
 */

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController extends BaseController{

    @Autowired
    private SetmealService setmealService;  //注入套餐业务类

    /**
     * 新增套餐
     * @param setmealDto 封装了套餐和套餐菜品的信息
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("添加套餐：{}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("保存套餐成功");
    }

    /**
     * 分页显示套餐列表
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> findPage(@RequestParam("page") Integer pageNum, Integer pageSize, String name){
        Page<SetmealDto> pageResult = setmealService.findPage(pageNum, pageSize, name);
        return R.success(pageResult);
    }

    /**
     * 删除单个/多个套餐
     */
    @DeleteMapping
    public R<String> delete(Long[] ids){
        setmealService.removeWithIds(ids);
        return R.success("删除套餐成功");
    }

    /**
     * 单个/批量修改销售状态
     */
    @PostMapping("status/{status}")
    public R<String> updateStatus(@PathVariable Integer status, Long[] ids){
        //调用业务层更新
        setmealService.changeStatus(ids, status);
        log.info("修改id为：{}的菜品，状态为：{}", ids, status);
        return R.success("修改菜品状态成功");
    }
}
