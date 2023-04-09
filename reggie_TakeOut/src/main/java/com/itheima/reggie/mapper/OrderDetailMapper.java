package com.itheima.reggie.mapper;

/**
 * @Projectname: store
 * @Filename: OrderDetaiMapper
 * @Author: Steven
 * @Data:2022-10-16 18:39
 */

import com.itheima.reggie.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 订单明细数据层
 */
public interface OrderDetailMapper {

    /**
     * 添加多条订单明细数据
     */
    int addBatch(@Param("orderDetails")List<OrderDetail> orderDetails);

    /**
     * 查询某个订单的订单项
     */
    @Select("select * from order_detail where order_id=#{orderId}")
    List<OrderDetail> findOrderDetail(Long orderId);
}
