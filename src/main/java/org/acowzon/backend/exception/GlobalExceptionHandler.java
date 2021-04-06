package org.acowzon.backend.exception;

import org.acowzon.backend.ctrl.DefaultWebResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务相关异常处理器
     * @param req Servlet请求
     * @param businessException 业务相关异常
     * @return 将异常封装后的响应
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public DefaultWebResponse businessExceptionHandler(HttpServletRequest req, BusinessException businessException) {
        return DefaultWebResponse.Builder.fail(businessException.getMessage());
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
        return DefaultWebResponse.Builder.fail("系统内部错误，请联系管理员");
    }
}
