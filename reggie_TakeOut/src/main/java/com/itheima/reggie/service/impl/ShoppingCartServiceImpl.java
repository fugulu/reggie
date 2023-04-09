package com.itheima.reggie.service.impl;

import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.mapper.ShoppingCartMapper;
import com.itheima.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Projectname: reggie
 * @Filename: ShoppingCartServiceImpl
 * @Author: Steven
 * @Data:2022-10-15 16:13
 */

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    /**
     * 添加购物车项
     * @param cart 提交的购物项的数据
     * @return 操作完成的购物车
     */
    @Override
    public ShoppingCart add(ShoppingCart cart) {
        //1.    查询当前菜品或套餐是否已经存在购物车中
        ShoppingCart cartItem = shoppingCartMapper.findCartItem(cart);
        //2.    判断购物车是否存在
        if(cartItem == null){
            //2.1   设置数量为1
            cart.setNumber(1);
            //2.2   下订时间
            cart.setCreateTime(LocalDateTime.now());
            //2.3   将一个对象指向另一个对象
            cartItem = cart;
            //2.4   写入数据库中
            shoppingCartMapper.add(cartItem);
        }else{
            //3.    不为空，则数量加1，并且更新
            cartItem.setNumber(cartItem.getNumber() + 1);
            shoppingCartMapper.modifyNumber(cartItem);
        }
        return cartItem;
    }

    /**
     * 购物车数量减一
     * @param shoppingCart
     * @return
     */
    @Override
    public ShoppingCart sub(ShoppingCart shoppingCart) {
        //1.    根据用户的id与dish_id 或者setmeal_id获取购物车项
        ShoppingCart cartItem = shoppingCartMapper.findCartItem(shoppingCart);
        //2.    判断购物车的数量，如果大于一，则数量减一
        if(cartItem.getNumber() > 1){
            cartItem.setNumber(cartItem.getNumber() - 1);
            shoppingCartMapper.modifyNumber(cartItem);
        }else{
            //否则设置为0
            cartItem.setNumber(0);
            //3.    删除当前记录
            shoppingCartMapper.delete(cartItem.getId());
        }
        //4.    返回修改后的购物车
        return cartItem;
    }

    /**
     * 通过用户id查看购物车
     *
     * @param userId
     */
    @Override
    public List<ShoppingCart> findCartByUserId(Long userId) {
        return shoppingCartMapper.findCartByUserId(userId);
    }

    /**
     * 清空购物车
     * @param userId
     */
    @Override
    public void cleanCart(Long userId) {
        shoppingCartMapper.deleteByUserId(userId);
    }
}
