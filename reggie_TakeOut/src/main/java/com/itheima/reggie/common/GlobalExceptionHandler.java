package com.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理重复添加异常
     * SQLIntegrityConstraintViolationException是MySQLIntegrityConstraintViolationException父类
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> handleDuplicateEntry(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        //判断错误信息
        if (message.startsWith("Duplicate entry")) {
            //记录日志
            log.error("出现重复添加的异常：{}", ex.getMessage());
            //返回结果
            return R.error("记录已经存在");
        }
        //记录日志
        log.error(ex.getMessage());
        //返回其它的错误信息
        return R.error(message);
    }

    /**
     * 捕获用户自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public R<String> handleCustomException(CustomException ex) {
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }
}
