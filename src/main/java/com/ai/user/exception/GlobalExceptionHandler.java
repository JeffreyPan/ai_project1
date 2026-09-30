package com.ai.user.exception;

import com.ai.user.common.Response;
import com.ai.user.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

/**
 * 全局异常处理，统一返回包装
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Response<Void> handleBusiness(BusinessException e) {
        return Response.error(e.getCode(), e.getMessage());
    }

    /**
     * @RequestBody 参数校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<Void> handleValid(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + "：" + fe.getDefaultMessage())
                .collect(Collectors.joining("；"));
        return Response.error(ResultCode.BAD_REQUEST.getCode(), message);
    }

    /**
     * 表单/Query 参数校验失败
     */
    @ExceptionHandler(BindException.class)
    public Response<Void> handleBind(BindException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + "：" + fe.getDefaultMessage())
                .collect(Collectors.joining("；"));
        return Response.error(ResultCode.BAD_REQUEST.getCode(), message);
    }

    /**
     * 参数缺失 / 类型不匹配
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public Response<Void> handleBadRequest(Exception e) {
        return Response.error(ResultCode.BAD_REQUEST.getCode(), "请求参数格式不正确");
    }

    /**
     * 兜底异常
     */
    @ExceptionHandler(Exception.class)
    public Response<Void> handleException(Exception e) {
        log.error("system error", e);
        return Response.error(ResultCode.INTERNAL_ERROR);
    }
}