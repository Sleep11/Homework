package com.bwie.interceptor;


import com.bwie.utils.JWTUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bobo棒
 * @date 2026年01月16日 13:35
 */


public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        System.out.println("拦截器拦截到请求:" + requestURI);
        // 放行 OPTIONS 预检请求
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

       //从请求头获取token
       String token= request.getHeader("token");
        response.setContentType("application/json;charset=utf-8");
        //判断token是否为空
       if(ObjectUtils.isEmpty(token)){
           //401 代表没有权限
           response.setStatus(401);
           response.getWriter().write("请先登录");

           return false;
       }
       //判断token是否有效
        if(!JWTUtils.valid(token)){
            //401 代表没有权限
            response.setStatus(401);
            response.getWriter().write("token错误或失效,请重新登录");

            return false;
        }
        //放行
        return true;
    }
}
