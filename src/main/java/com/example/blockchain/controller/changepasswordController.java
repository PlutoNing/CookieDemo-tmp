package com.example.blockchain.controller;

import com.example.blockchain.Entity.User;
import com.example.blockchain.service.serviceimpl.changepasswordServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class changepasswordController {

    @RequestMapping(value = {"/changepassword"}, method = RequestMethod.GET)
    public String changepasswordGET(HttpServletRequest request,ModelMap model){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        String password = user.getPassword();
        if (password == null){
            model.addAttribute("username",username);
            model.addAttribute("error","当前还未设置密码，请设置");
            return "changepassword1";
        }
        model.addAttribute("username", username);
        return "changepassword";
    }

    @RequestMapping(value = {"/changepassword"},method = RequestMethod.POST)
    public String changepasswordPOST(String password, String password1, String password2, ModelMap model, HttpServletRequest request) throws SQLException {
        changepasswordServiceImpl changeService= new changepasswordServiceImpl();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        System.out.println("password1 is"+password1);
        System.out.println("password2 is"+password2);
        model.addAttribute("username", username);

        if(!(password1.equals(password2))){
            model.addAttribute("error","第一次输入的密码和第二次不同，请重新输入！");

            return "changepassword.html";
        }else {
            if(changeService.checkpassword(password,username)== -1){
                changeService.changepassword(password1,username,session);
                model.addAttribute("error","密码修改成功");
                return "changepassword.html";
            }
            else {
                model.addAttribute("error","原密码错误请重新输入！");
                return "changepassword.html";
            }
        }
    }

    @RequestMapping(value = {"/changepassword1"},method = RequestMethod.POST)
    public String changepasswordPOST1(String password1, String password2, ModelMap model, HttpServletRequest request) throws SQLException {
        changepasswordServiceImpl changeService= new changepasswordServiceImpl();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        System.out.println("password1 is"+password1);
        System.out.println("password2 is"+password2);
        model.addAttribute("username", username);

        if(!(password1.equals(password2))){
            model.addAttribute("error","第一次输入的密码和第二次不同，请重新输入！");
            return "changepassword1.html";
        }
        else {
            changeService.changepassword(password1,username,session);
            model.addAttribute("error","密码设置成功");
            return "changepassword.html";
            }


    }
}
