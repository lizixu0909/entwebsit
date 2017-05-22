package com.sipuang.entwebsit.domain.exception;

import com.sipuang.entwebsit.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理
 */
@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     * 业务上的错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(CustomServiceException.class)
    public AjaxResult handleServiceException(CustomServiceException e) {
        return AjaxResult.SERVICEERROR(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e) {
        BindingResult result = e.getBindingResult();
        StringBuffer sb = new StringBuffer();
        for (ObjectError error : result.getAllErrors()) {
            String field = error.getCode();
            String code = error.getDefaultMessage();
            String message = String.format("%s:%s", field, code);
            sb.append(message);
        }
        return AjaxResult.SERVICEERROR(sb.toString());
    }

    /**
     * 参数错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public AjaxResult handleBadRequestException(Exception e) {
        LOGGER.error("400错误：{}", e);
        return AjaxResult.ERROR(AjaxResult.ResultStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        LOGGER.error("不支持当前请求方法[request_method_not_supported]: ", e);
        return AjaxResult.ERROR(AjaxResult.ResultStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 500 - Internal Server Error
     */
    @ExceptionHandler(value = Exception.class)
    public AjaxResult handleException(Exception e) {
        LOGGER.error("服务器运行异常:{} ", e);
        return AjaxResult.ERROR(AjaxResult.ResultStatus.INTERNAL_SERVER_ERROR);
    }
}
