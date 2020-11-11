package com.example.blockchain.controller;


import com.example.blockchain.Entity.User;
import com.example.blockchain.service.serviceimpl.LoginServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class registerController {

    @RequestMapping(value="/registerSubmit", method = RequestMethod.POST)
    public String registerSubmit(String username, String password2,String phone, String email,Model model) throws SQLException {
        LoginServiceImpl loginService = new LoginServiceImpl();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password2);
        user.setPhone(phone);
        user.setEmail(email);
        if (phone == ""){
            model.addAttribute("error",5);
            return "login.html";
        }
        if(username == ""){
            model.addAttribute("error",3);
            return "login.html";
        }
        if(loginService.register(user)==true) {
            model.addAttribute("error",4);
            return "login.html";
        }
        else{
            model.addAttribute("error",2);
            return "login.html";
        }
    }


    @RequestMapping(value="/xcxregister", method = RequestMethod.GET)
    public String xcsregister(HttpServletRequest request, HttpServletResponse response,  Model model) throws SQLException, IOException {
        LoginServiceImpl loginService = new LoginServiceImpl();
        User user = new User();
        String username =request.getParameter("account");
        String password = request.getParameter("password");
        String phone = request.getParameter("phonenum");
        String email = request.getParameter("emailadd");
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setEmail(email);
        Map<String,String> map=new HashMap<String,String>();

        if(loginService.register(user)==true) {
            map.put("message", "ok");
            JSONObject mapObject=JSONObject.fromObject(map);
            System.out.println("mapObject:"+mapObject.toString());
            //从服务器传mapObject数据到小程序
            response.getWriter().write(mapObject.toString());
            return null;
        }
        else{
            map.put("message", "error");
            JSONObject mapObject=JSONObject.fromObject(map);
            System.out.println("mapObject:"+mapObject.toString());
            //从服务器传mapObject数据到小程序
            response.getWriter().write(mapObject.toString());
            return null;
        }
    }
}
