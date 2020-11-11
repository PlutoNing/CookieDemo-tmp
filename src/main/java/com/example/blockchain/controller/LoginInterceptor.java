package com.example.blockchain.controller;


import com.example.blockchain.Entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (uri.contains("/login") || uri.contains("/registerSubmit") || uri.contains("/images") || uri.contains("/wxLoginPage")|| uri.contains("/wxlogin") ||
                uri.contains("/wechat") || uri.contains("/js") || uri.contains("/css") ||
                uri.contains("/xcxlogin") || uri.contains("/xcxregister") || uri.contains("/userinfo") || uri.contains("/xcxwxlogin") || uri.contains("/send")){
            return true;
        }
        //获取session，有就是说明已经登录，没有就是拦截访问并跳转到登录页面
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null){
            System.out.println(".........");
            return true;
        }
        response.sendRedirect("login");

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
   }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
