package com.itheima.reggie.controller;

/**
 * @Projectname: reggie
 * @Filename: ShoppingCartController
 * @Author: Steven
 * @Data:2022-10-15 16:20
 */

import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车控制类
 */
@Slf4j
@RestController
@RequestMapping("shoppingCart")
public class ShoppingCartController extends BaseController{

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart  当前提交的购物项的数据
     * @return  返回更新以后的购物车
     */
    @PostMapping("add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        //1.    获取当前登录者
        Long userId = getLoginIdFromSession();
        shoppingCart.setUserId(userId);
        //2.    添加成功后，把当前的购物项返回
         shoppingCart = shoppingCartService.add(shoppingCart);
         return R.success(shoppingCart);
    }

    /**
     * 购物车减少处理
     */
    @PostMapping("sub")
    public R<ShoppingCart> sun(@RequestBody ShoppingCart shoppingCart){
        //1.获取当前登录者id，添加到userId的字段中
        Long userId = getLoginIdFromSession();
        shoppingCart.setUserId(userId);
        //2.将返回的数据为修改后的购物车对象
        ShoppingCart cart = shoppingCartService.sub(shoppingCart);
        return R.success(cart);
    }

    /**
     * 查看购物车
     */
    @GetMapping("list")
    public R<List<ShoppingCart>> list() {
        //1.    获取当前登录的用户的id
        Long userId = getLoginIdFromSession();
        //2.    根据当前登陆者查看购物车
        List<ShoppingCart> shoppingCartList = shoppingCartService.findCartByUserId(userId);

        return R.success(shoppingCartList);
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("clean")
    public R<String> clean() {
        //1.    获取当前登录的用户的id
        Long userId = getLoginIdFromSession();
        //2.    清空购物车
        shoppingCartService.cleanCart(userId);

        return R.success("清空购物车成功");
    }
}
