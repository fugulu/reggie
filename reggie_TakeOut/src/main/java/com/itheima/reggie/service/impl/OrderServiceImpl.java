package com.itheima.reggie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.reggie.dto.OrderDto;
import com.itheima.reggie.dto.OrdersDto;
import com.itheima.reggie.entity.*;
import com.itheima.reggie.mapper.*;
import com.itheima.reggie.service.OrderService;
import com.itheima.reggie.util.UUIDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Projectname: store
 * @Filename: OrderServiceImpl
 * @Author: Steven
 * @Data:2022-10-16 19:43
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;    //订单

    @Autowired
    private OrderDetailMapper orderDetaiMapper;  //订单明细

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;  //购物车

    @Autowired
    private UserMapper userMapper;  //用户

    @Autowired
    private AddressBookMapper addressBookMapper;    //地址

    /**
     * 下单
     *
     * @param orders 订单的信息(地址id，支付方式，备注)
     */
    @Override
    public void submit(Orders orders) {
        //1. 通过用户id，查询当前用户的购物车数据
        Long userId = orders.getUserId();
        List<ShoppingCart> carts = shoppingCartMapper.findCartByUserId(userId);

        //2. 根据当前登录用户id, 查询用户数据 (需要创建持久层方法)
        User user = userMapper.findById(userId);

        //3. 根据订单地址ID, 查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookMapper.getById(addressBookId);

        //4. 通过工具类生成uuid的订单id (在资料中提供了工具类)
        long orderId = UUIDUtils.getUUIDInOrderId();

        //5. 声明变量记录总金额，供后面使用
        BigDecimal totalAmount = BigDecimal.ZERO;

        //6. 声明变量为0，用于统计共有多少件商品
        int totalNumber = 0;

        //7. 创建一个订单项集合
        List<OrderDetail> orderDetails = new ArrayList<>();

        //8. 遍历所有的购物项，一个购物项就是一个订单明细
        for (ShoppingCart cart : carts) {
            //8.1. 创建订单明细对象
            OrderDetail detail = new OrderDetail();
            //8.2  购物车与订单明细表中相同的属性可以直接复制
            BeanUtils.copyProperties(cart, detail);
            //8.3. 设置订单项目的主键，使用uuid工具类生成
            detail.setId(UUIDUtils.getUUIDInOrderId().longValue());
            //8.4. 订单项所属的订单的id(上面生成的订单id)
            detail.setOrderId(orderId);
            //8.5  累加总数量
            totalNumber += detail.getNumber();
            //8.6. 计算当前购物项的总价：数量乘以单价
            BigDecimal price = detail.getAmount().multiply(BigDecimal.valueOf(detail.getNumber()));
            //8.7. 再累加到上面定义的总价变量中
            totalAmount = totalAmount.add(price);
            //8.8. 添加到购物车项集合中
            orderDetails.add(detail);
        }
        //9. 组装订单数据, 使用方法的参数orders
        //9.1. 设置订单号
        orders.setId(orderId);
        //9.2. 设置商品的数量
        orders.setNumber(String.valueOf(totalNumber));
        //9.3. 设置状态码为1
        orders.setStatus(1);
        //9.4. 设置下单时间为现在
        orders.setOrderTime(LocalDateTime.now());
        //9.5. 设置支付时间为现在时间加30分钟
        orders.setCheckoutTime(LocalDateTime.now().plusMinutes(30));
        //9.6. 设置总金额
        orders.setAmount(totalAmount);
        //9.7. 设置登录用户名
        orders.setUserName(user.getName());
        //9.8. 设置收货电话
        orders.setPhone(addressBook.getPhone());
        //9.9. 设置收货地址
        orders.setAddress(addressBook.getDetail());
        //9.10. 收货人consignee
        orders.setConsignee(addressBook.getConsignee());
        //10. 调用orderMapper插入订单 (编写新的持久层方法)
        orderMapper.add(orders);
        //11. 调用orderDetailMapper批量插入订单项 (编写新的持久层方法)
        orderDetaiMapper.addBatch(orderDetails);
        //12. 删除当前用户的购物车列表数据
        shoppingCartMapper.deleteByUserId(userId);
    }

    /**
     * 查询某个用户的订单信息
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     */
    @Override
    public Page<OrderDto> findOrdersByUserId(Long userId, Integer pageNum, Integer pageSize) {
        //1.  查询当前用户的订单信息
        PageHelper.startPage(pageNum, pageSize);
        List<Orders> orders = orderMapper.findOrdersByUserId(userId);
        PageInfo<Orders> pageInfo = new PageInfo<>(orders);

        //2.  重新封装每个订单的订单项
        List<OrderDto> orderDtos = new ArrayList<>();
        orders.forEach(order -> {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order, orderDto);

            //2.1  查询当前订单对应所有的订单项
            List<OrderDetail> details = orderDetaiMapper.findOrderDetail(order.getId());
            orderDto.setOrderDetails(details);
            orderDtos.add(orderDto);
        });
        //3.  要封装的总页数，前端用到了这个参数
        return new Page<>(orderDtos, pageInfo.getTotal(), pageSize, pageNum, pageInfo.getPages());
    }

    /**
     * 分页查询订单
     * @param pageNum
     * @param pageSize
     * @param orderId
     * @param beginTime
     * @param endTime
     */
    @Override
    public Page<Orders> findByPage(Integer pageNum, Integer pageSize, String orderId, String beginTime, String endTime) {
        List<Orders> orders = orderMapper.list(orderId, beginTime, endTime);
        return new Page<>(orders);
    }
}
