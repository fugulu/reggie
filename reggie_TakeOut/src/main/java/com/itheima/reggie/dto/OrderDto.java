package com.itheima.reggie.dto;

import com.itheima.reggie.entity.OrderDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Projectname: store
 * @Filename: OrderDto
 * @Author: Steven
 * @Data:2022-10-16 22:07
 */

@Data
public class OrderDto {
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
