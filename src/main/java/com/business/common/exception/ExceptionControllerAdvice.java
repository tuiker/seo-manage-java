package com.business.common.exception;

import com.business.common.enums.ResultCode;
import com.business.common.response.ResultVO;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author: GaoLu
 * @Date: 2023-10-18 15:16
 * @Description: 全局异常处理类
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * @Description 接口参数校验
     * @Author GaoLu
     * @Date 2023/10/18
     **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        return new ResultVO<>(ResultCode.VALIDATE_FAILED, objectError.getDefaultMessage());
    }

    /**
     * @Description 全局异常
     * @Author GaoLu
     * @Date 2023/10/18
     **/
    @ExceptionHandler(APIException.class)
    public ResultVO<String> APIExceptionHandler(APIException e) {
        return new ResultVO<>(ResultCode.FAILED, e.getMsg());
    }
}
