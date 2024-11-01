package com.example.usercenter.exception;

import com.example.usercenter.common.BaseResponse;
import com.example.usercenter.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.usercenter.common.ResultUtils.error;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("businessException"+e.getMessage(),e);
        return error(e.getCode(),e.getMessage(),e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException",e);
        return error(ErrorCode.SYSTEM_ERR,e.getMessage(),"");
    }
}
