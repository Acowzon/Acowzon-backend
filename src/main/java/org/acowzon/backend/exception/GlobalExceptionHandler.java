package org.acowzon.backend.exception;

import org.acowzon.backend.ctrl.DefaultWebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 业务相关异常处理器
     * @param req Servlet请求
     * @param businessException 业务相关异常
     * @return 将异常封装后的响应
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public DefaultWebResponse businessExceptionHandler(HttpServletRequest req, BusinessException businessException) {
        logger.error("business exception",businessException);
        return DefaultWebResponse.Builder.fail(businessException.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public DefaultWebResponse httpMessageNotReadableExceptionHandler(HttpServletRequest req, HttpMessageNotReadableException exception) {
        logger.error("httpMessageNotReadableException",exception);
        exception.printStackTrace();
        return DefaultWebResponse.Builder.fail("参数解析异常");
    }

    /**
     * 运行时异常处理器
     * @param req Servlet请求
     * @param runtimeException 运行时异常
     * @return 将异常封装后的响应
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public DefaultWebResponse runTimeExceptionHandler(HttpServletRequest req,RuntimeException runtimeException) {
        runtimeException.printStackTrace();
        return DefaultWebResponse.Builder.fail("系统内部错误，请联系管理员");
    }
}
