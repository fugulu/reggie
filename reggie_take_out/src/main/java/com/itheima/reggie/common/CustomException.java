package com.itheima.reggie.common;

/**
 * 用户自定义异常
 */
public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
