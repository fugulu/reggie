package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Page;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("setmeal")
@Api(tags = "套餐相关接口")   //  加上tags属性
public class SetmealController extends BaseController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 添加套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    @ApiOperation("新增套餐接口")
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("添加套餐: {}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("添加套餐成功");
    }

    /**
     * 分页查询
     */
    @GetMapping("page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", required = true,  value = "页码"),
            @ApiImplicitParam(name = "pageSize", required = true,  value = "页面大小"),
            @ApiImplicitParam(name = "name", value = "套餐名")
    })
    @ApiOperation("分页查询接口")
    public R<Page<SetmealDto>> findPage(@RequestParam("page") Integer pageNum, Integer pageSize, String name) {
        Page<SetmealDto> page = setmealService.findByPage(pageNum, pageSize, name);
        return R.success(page);
    }

    /**
     * 删除1个或多个套餐
     */
    @DeleteMapping
    @ApiOperation("套餐删除接口")
    public R<String> delete(Long[] ids) {
        log.info("删除多个套餐：{}", Arrays.toString(ids));
        //调用业务层删除
        setmealService.removeWithIds(ids);
        return R.success("删除套餐成功");
    }

    /**
     * 根据套餐的类型展示套餐
     */
    @GetMapping("list")
    @ApiOperation("套餐某个分类的套餐接口")
    public R<List<Setmeal>>list(Long categoryId, Integer status){
        List<Setmeal> setmealList = setmealService.list(categoryId, status);
        return R.success(setmealList);
    }

    /**
     * 通过套餐id，查询相对应的菜品
     */
    @GetMapping("dish/{setmealId}")
    public R<List<Map<String, Object>>> findSetmealDish(@PathVariable Long setmealId){
        List<Map<String, Object>> setmealDishes = setmealService.findSetmealDish(setmealId);
        return R.success(setmealDishes);
    }
}
