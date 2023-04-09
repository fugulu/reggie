package com.itheima.reggie.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class R<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    /**
     * 操作成功调用的静态方法
     * @param object 返回的数据
     * @param <T> 指定类型
     * @return 结果对象
     */
    public static <T> R<T> success(T object) {
        R<T> r = new R<>();
        r.data = object;
        r.code = 1;
        return r;
    }

    /**
     * 操作失败调用的静态方法
     * @param msg 失败的信息
     * @param <T> 数据类型
     * @return 结果对象
     */
    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    /**
     * 添加数据到结果对象中
     * @param key 键
     * @param value 值
     * @return 结果对象
     */
    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
