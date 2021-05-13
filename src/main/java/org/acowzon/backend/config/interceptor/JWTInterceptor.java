package org.acowzon.backend.config.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acowzon.backend.ctrl.DefaultWebResponse;
import org.acowzon.backend.utils.JWTUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        DefaultWebResponse msg; // 要返回给前端的信息
        String token = request.getHeader("token");
        try {
            JWTUtil.verify(token);  // 如果token验证失败就会抛出异常
            return true;
        } catch (SignatureVerificationException e){ // 签名异常
            msg = DefaultWebResponse.Builder.fail("signature error");
        } catch (TokenExpiredException e){  // token过期
            msg = DefaultWebResponse.Builder.fail("token expired");
        } catch (AlgorithmMismatchException e){ // 加密算法不一致
            msg = DefaultWebResponse.Builder.fail("algorithm mismatch");
        } catch (Exception e){  // token无效
            msg = DefaultWebResponse.Builder.fail("token invalid");
        }
        String json = new ObjectMapper().writeValueAsString(msg);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(json);
        return false;
    }
}