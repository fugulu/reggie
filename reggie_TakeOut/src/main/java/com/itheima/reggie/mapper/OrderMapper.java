package com.itheima.reggie.mapper;

/**
 * @Projectname: store
 * @Filename: OrderMapper
 * @Author: Steven
 * @Data:2022-10-16 18:36
 */

import com.itheima.reggie.entity.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 订单数据层
 */
public interface OrderMapper {
    /**
     * 添加一个订单信息
     * 订单号由我们生成，id号也要添加
     * 同时方法名不写成save
     */
    @Insert("insert into orders values(#{id},#{number},#{status},#{userId},#{addressBookId},#{orderTime},#{checkoutTime},#{payMethod},#{amount},#{remark},#{phone},#{address},#{userName},#{consignee})")
    void add(Orders orders);

    /**
     * 表连接查询订单和订单数
     */
    @Select("select * from orders where user_id=#{userId} order by order_time desc")
    List<Orders> findOrdersByUserId(Long userId);

    /**
     * 查询订单信息
     */
    List<Orders> list(@Param("orderId") String orderId, @Param("beginTime") String beginTime, @Param("endTime") String endTime);
}
