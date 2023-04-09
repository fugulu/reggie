package com.itheima.reggie.mapper;

/**
 * @Projectname: reggie
 * @Filename: ShoppingCartMapper
 * @Author: Steven
 * @Data:2022-10-15 15:59
 */

import com.itheima.reggie.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 购物车数据层
 */
public interface ShoppingCartMapper {

    /**
     * 通过dishId或者setmealId查询购物车项是否存在
     */
    ShoppingCart findCartItem(ShoppingCart shoppingCart);

    /**
     * 添加购物车项
     * 注：方法名不能叫save()
     */
    @Insert("insert into shopping_cart values(null,#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{createTime})")
    void add(ShoppingCart shoppingCart);

    /**
     * 更新购物车项中nummber数量
     * nummber从业务层传递过来
     */
    @Update("update shopping_cart set number=#{number} where id=#{id}")
    void modifyNumber(ShoppingCart shoppingCart);

    /**
     * 购物车减一
     */
    @Delete("delete from shopping_cart where id=#{id}")
    void delete(Long id);

    /**
     * 根据用户id查看购物车
     */
    @Select("select * from shopping_cart where user_id=#{userId}")
    List<ShoppingCart> findCartByUserId(Long userId);

    /**
     * 清空购物车
     */
    @Delete("delete from shopping_cart where user_id=#{userId}")
    void deleteByUserId(Long userId);
}
