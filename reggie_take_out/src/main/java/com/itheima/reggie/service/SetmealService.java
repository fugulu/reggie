package com.itheima.reggie.service;

/**
 * @Projectname: reggie
 * @Filename: SetmealService
 * @Author: Steven
 * @Data:2022-10-12 18:17
 */

import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Page;

/**
 * 套餐的业务层
 */
public interface SetmealService {

    /**
     * 新增套餐
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 分页显示套餐列表
     * @param pageNum   当前页
     * @param pageSize    页面叶
     * @param name      菜品的名称
     * @return
     */
    Page<SetmealDto> findPage(Integer pageNum, Integer pageSize, String name);

    /**
     * 批量删除
     * @param ids   要删除的套餐的id
     */
    void removeWithIds(Long[] ids);

    /**
     * 单个/批量修改套餐销售状态
     */
    void changeStatus(Long[] ids, Integer status);
}
