package com.itheima.reggie.util;

import java.util.UUID;

public class UUIDUtils {
    /**
     * 生成字符串类型的uuid
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成整数类型的订单id
     * @return
     */
    public static Integer getUUIDInOrderId() {
        Integer orderId = UUID.randomUUID().toString().hashCode();
        orderId = orderId < 0 ? -orderId : orderId; //String.hashCode() 值会为空
        return orderId;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++)
            System.out.println(UUIDUtils.getUUIDInOrderId());
    }
}