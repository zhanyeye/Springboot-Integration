package com.example.springbootexamples.interceptor;

import com.example.springbootexamples.component.EncryptorComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private EncryptorComponent encryptorComponent;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //创建拦截器组件，判断header中token是否合法，将反序列化出的用户信息，加载到当前request
        //Boolean preHandle() : controller方法执行前回调。登录验证等
        Optional.ofNullable(request.getHeader("Authorization"))
                .ifPresentOrElse(token -> {
                  var map = encryptorComponent.decrypt(token);
                  request.setAttribute("uid", map.get("uid"));
                  request.setAttribute("aid", map.get("aid"));
                }, () ->{
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "未登录!");
                });
        return true;
    }
}
