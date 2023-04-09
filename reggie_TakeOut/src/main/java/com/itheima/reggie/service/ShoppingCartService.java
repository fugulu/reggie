package com.itheima.reggie.service;

import com.itheima.reggie.entity.ShoppingCart;

import java.util.List;

/**
 * @Projectname: reggie
 * @Filename: ShoppingCartService
 * @Author: Steven
 * @Data:2022-10-15 16:11
 */
public interface ShoppingCartService {

    /**
     * 添加购物车项
     * @param shoppingCart 提交的购物项的数据
     * @return 操作完成的购物车
     */
    ShoppingCart add(ShoppingCart shoppingCart);

    /**
     * 减少购物车数量
     */
    ShoppingCart sub(ShoppingCart shoppingCart);

    /**
     * 通过用户id查看购物车
     */
    List<ShoppingCart> findCartByUserId(Long userId);

    /**
     * 清空购物车
     */
    void cleanCart(Long userId);
}
