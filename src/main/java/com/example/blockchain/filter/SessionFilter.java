package com.example.blockchain.filter;


import com.example.blockchain.Entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SessionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //不过滤的uri
        String[] notFilter = new String[]{"/userinfo","/index.html","/login","/registerSubmit","/logout","/js","/images",
                "/wxlogin","/wechat","/xcxlogin","/xcxregister","/xcxuserinfo","/xcxwxlogin","/send"};

        //请求的uri
        String uri = request.getRequestURI();
        System.out.println("filter>>>uri>>>"+uri);

        //是否过滤
        boolean doFilter = true;
        for(String s : notFilter){
            if(uri.indexOf(s) != -1){
                //uri中包含不过滤uri，则不进行过滤
                doFilter = false;
                break;
            }
        }

        if(doFilter){
            System.out.println("doFilter>>>");
            //过滤操作
            //从session中获取登陆者实体
            Object obj = request.getSession().getAttribute("user");

            if(obj==null){
                    System.out.println("doFilter>>>obj is null");
                    System.out.println("doFilter>>>http request");
                    response.sendRedirect("login");
                    //跳转到登录页面
                    return ;
                }
            else{
                User user = (User) obj;
                System.out.println("doFilter>>>username>>"+user.getUsername());
                // 如果session中存在登录者实体，则继续
                filterChain.doFilter(request, response);
            }
        }else{
            System.out.println("no Filter>>>");
            //不执行过滤操作
            filterChain.doFilter(request, response);
        }
    }



}
