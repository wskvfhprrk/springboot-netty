package com.hejz.studay.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice(basePackages = "com.hejz.studay.controller")
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    ObjectMapper objectMapper;
    @ExceptionHandler(value = PropertyValueException.class)
    public Result handleVaildException(PropertyValueException e) {
        log.error("数据校验出现问题{}，异常类型：{}", e.getMessage(), e.getClass());
        return Result.error(404,e.getPropertyName()+"不能为空值！");
    }
}
