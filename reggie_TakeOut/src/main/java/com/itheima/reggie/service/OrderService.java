package com.itheima.reggie.service;

import com.itheima.reggie.dto.OrderDto;
import com.itheima.reggie.dto.OrdersDto;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.entity.Page;

/**
 * @Projectname: store
 * @Filename: OrderService
 * @Author: Steven
 * @Data:2022-10-16 19:42
 */
public interface OrderService {
    /**
     * 下单
     * @param orders 订单的信息(地址id，支付方式，备注)
     */
    void submit(Orders orders);

    /**
     * 查询某个用户的订单信息
     */
    Page<OrderDto> findOrdersByUserId(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 分页查询订单
     */
    Page<Orders> findByPage(Integer pageNum, Integer pageSize, String orderId, String beginTime, String endTime);
}
