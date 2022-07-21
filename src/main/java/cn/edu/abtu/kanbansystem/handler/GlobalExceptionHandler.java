package cn.edu.abtu.kanbansystem.handler;

import cn.edu.abtu.kanbansystem.bean.constant.HttpCode;
import cn.edu.abtu.kanbansystem.bean.constant.HttpMsg;
import cn.edu.abtu.kanbansystem.bean.model.JsonResult;
import cn.edu.abtu.kanbansystem.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 *
 * @author Richard
 * @date 2022/1/29 12:35
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = BaseException.class)
    public JsonResult<Object> baseExceptionHandler(BaseException e) {
        e.printStackTrace();
        return new JsonResult<>(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * 密码错误
     */
    @ExceptionHandler(value = BadCredentialsException.class)
    public JsonResult<Object> badCredentialExceptionHandler(BadCredentialsException e) {
        e.printStackTrace();
        return new JsonResult<>(HttpCode.PASSWORD_WRONG, HttpMsg.PASSWORD_WRONG);
    }


    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    public JsonResult<Object> exceptionHandler(Exception e) {
        e.printStackTrace();
        return new JsonResult<>(HttpCode.FAILURE, e.getMessage());
    }

}
