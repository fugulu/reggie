package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.OrderDto;
import com.itheima.reggie.dto.OrdersDto;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.entity.Page;
import com.itheima.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Projectname: store
 * @Filename: OrderController
 * @Author: Steven
 * @Data:2022-10-16 20:13
 */

@RestController
@RequestMapping("order")
@Slf4j
public class OrderController extends BaseController{

    @Autowired
    private OrderService orderService;

    /**
     * 提交订单
     */
    @PostMapping("submit")
    public R<String> submit(@RequestBody Orders orders){
        //1.  获取当前登录的用户的id
        Long userId = getLoginIdFromSession();
        //2.  将userId封装到orders实体类中
        orders.setUserId(userId);
        //3.  调用业务层下单
        orderService.submit(orders);
        //4.  返回下单成功
        return R.success("下单成功");
    }

    /**
     * 查询当前用户的订单信息
     */
    @GetMapping("userPage")
    public R<Page<OrderDto>> userPage(Integer page, Integer pageSize) {
        //1.得到登陆者
        Long userId = getLoginIdFromSession();
        //2.调用业务层查询
        Page<OrderDto> orders = orderService.findOrdersByUserId(userId, page, pageSize);
        return R.success(orders);
    }

    /**
     * 分页查询订单
     */
    @GetMapping("page")
    public R<Page<Orders>> page(@RequestParam("page") Integer pageNum, Integer pageSize, String orderId, String beginTime,
                                String endTime){
        Page<Orders> page = orderService.findByPage(pageNum, pageSize, orderId, beginTime, endTime);
        return R.success(page);
    }
}
