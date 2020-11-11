package com.example.blockchain.controller;


import com.example.blockchain.Entity.User;
import com.example.blockchain.service.WeChatAuthService;
import com.example.blockchain.service.WxloginService;
import com.example.blockchain.service.serviceimpl.LoginServiceImpl;
import com.example.blockchain.service.serviceimpl.UserInfoServiceImpl;
import com.example.blockchain.service.serviceimpl.WeChatAuthServiceImpl;
import com.example.blockchain.service.serviceimpl.WxloginServiceImpl;
import com.mysql.cj.protocol.x.Notice;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;
/*
import org.json.JSONObject;
*/
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;

@Controller
public class loginController {
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    public static final String TOKEN = "okjfdlsf_lsdfjdslkfj_token";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = {"/login"},method = RequestMethod.GET)
    public String login(String username, String password, HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null){
            Cookie cookie = new Cookie("JSESSIONID",session.getId());
            cookie.setMaxAge(5*365*24*60*60);
            cookie.setPath("/");
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return "redirect:/";
        }
        return "login.html";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String loginPost(String username, String password, HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) throws SQLException {
        LoginServiceImpl loginService = new LoginServiceImpl();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if (loginService.login(user,session) == -1){
            session.setAttribute("username",username);
            session.setAttribute("user",user);
            Cookie cookie = new Cookie("JSESSIONID",session.getId());
            cookie.setMaxAge(5*365*24*60*60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            model.addAttribute("username",username);
            return "redirect:/";
        }
        else{
            System.out.println("111");
            model.addAttribute("error",1);
            return "login.html";
        }
    }



    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie :cookies){ //遍历所有Cookie
            cookie.setMaxAge(0); //Cookie并不能根本意义上删除，只需要这样设置为0即可
            cookie.setPath("/"); //很关键，设置成跟写入cookies一样的，全路径共享Cookie
            response.addCookie(cookie); //重新响应
        }
        return "redirect:login.html";
    }

    @RequestMapping("")
    public String index(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        model.addAttribute("username", username);

        String a1 =request.getScheme();
        String a2 = request.getServerName();
        int a3= request.getServerPort();
        String a4 = request.getContextPath();
        String url="http"+"://"+a2+":"+80+a4;
        model.addAttribute("url",url);
        return "index";
    }


}
