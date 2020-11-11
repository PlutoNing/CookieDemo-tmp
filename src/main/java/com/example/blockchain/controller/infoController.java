package com.example.blockchain.controller;

import com.example.blockchain.Entity.User;
import com.example.blockchain.service.UserInfoService;
import com.example.blockchain.service.serviceimpl.UserInfoServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class infoController {
    @RequestMapping(value = {"/userinfo"},method = RequestMethod.GET)
    public String getuserinfo(HttpSession session, ModelMap model,HttpServletResponse response) throws SQLException {
        UserInfoServiceImpl userinfo = new UserInfoServiceImpl();
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        user = userinfo.getUserInfo(username);
        String phone = user.getPhone();
        String email = user.getEmail();
        String enable = user.getEnable();
        String group = user.getGroup();
        model.addAttribute("username",username);
        model.addAttribute("email",email);
        model.addAttribute("phone",phone);
        if(enable.equals("1")){
            model.addAttribute("enable","正常");
        }
        if(group.equals("0")){
            model.addAttribute("group","普通用户");
        }
        response.addHeader("p3p","CP=CAO PSA OUR");
        return "userinfo.html";
    }

    @RequestMapping(value = {"/setuserinfo"},method = RequestMethod.GET)
    public String setuserinfoget(HttpSession session, ModelMap model) throws SQLException{
        UserInfoServiceImpl userinfo = new UserInfoServiceImpl();
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        user = userinfo.getUserInfo(username);
        String phone = user.getPhone();
        String email = user.getEmail();
        String enable = user.getEnable();
        String group = user.getGroup();
        model.addAttribute("username",username);
        model.addAttribute("email",email);
        model.addAttribute("phone",phone);
        if(enable.equals("1")){
            model.addAttribute("enable","正常");
        }
        if(group.equals("0")){
            model.addAttribute("group","普通用户");
        }
        return "userinfo1.html";
    }

    @RequestMapping(value = {"/setuserinfo"},method = RequestMethod.POST)
    public String setuserinfopost(HttpSession session,String username,String email,String phone,ModelMap model) throws SQLException{
        User user = (User) session.getAttribute("user");
        UserInfoServiceImpl userinfo = new UserInfoServiceImpl();
        String username_old = user.getUsername();
        user = userinfo.getUserInfo(username_old);
        String phone_old = user.getPhone();
        String email_old = user.getEmail();
        String enable = user.getEnable();
        String group = user.getGroup();
        String password = user.getPassword();

        int temp =userinfo.setUserInfo(username_old,username,email,phone);
        if(temp == 1){ //修改失败，用户名重复
            model.addAttribute("error","修改失败，用户名已存在");
            model.addAttribute("username",username_old);
            model.addAttribute("email",email_old);
            model.addAttribute("phone",phone_old);
            if(enable.equals("1")){
                model.addAttribute("enable","正常");
            }
            if(group.equals("0")){
                model.addAttribute("group","普通用户");
            }
            return "userinfo1.html";
        }else { //修改成功
            model.addAttribute("error", "修改成功");
            model.addAttribute("username",username);
            model.addAttribute("email",email);
            model.addAttribute("phone",phone);
            if(enable.equals("1")){
                model.addAttribute("enable","正常");
            }
            if(group.equals("0")){
                model.addAttribute("group","普通用户");
            }
            user.setUsername(username);
            user.setPassword(password);
            session.setAttribute("user",user);
            return "userinfo1.html";
        }
    }

    @RequestMapping(value = {"/xcxuserinfo"},method = RequestMethod.GET)
    public String xcxgetuserinfo(HttpSession session, HttpServletRequest request, HttpServletResponse response,ModelMap model) throws SQLException, IOException {
        UserInfoServiceImpl userinfo = new UserInfoServiceImpl();
        User user = new User();
        String username = request.getParameter("username");
        System.out.println("username is "+username);
        user = userinfo.getUserInfo(username);
        String phone = user.getPhone();
        String email = user.getEmail();
        String enable = user.getEnable();
        String group = user.getGroup();
        Map<String,String> map=new HashMap<String,String>();


        map.put("enable",enable);
        map.put("group",group);
        map.put("username",username);
        map.put("phone",phone);
        map.put("email",email);
        JSONObject mapObject=JSONObject.fromObject(map);

        response.getWriter().write(mapObject.toString());
        return null;
    }


}
