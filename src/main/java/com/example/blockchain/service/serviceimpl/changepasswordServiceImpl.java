package com.example.blockchain.service.serviceimpl;


import com.example.blockchain.Entity.User;
import com.example.blockchain.service.changepasswordService;
import com.example.blockchain.util.DbUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class changepasswordServiceImpl implements changepasswordService {

    @Override
    public int checkinput(String password1, String password2) throws SQLException {
        return 0;
    }

    @Override
    public int checkpassword(String password_old,String username) throws SQLException {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        DbUtil dbUtil = new DbUtil();

        String pwd = "";
        String sql = "Select password from user_info where username = '" + username + "' ";
        ResultSet resultSet = dbUtil.resultSet(sql);
        int temp = 0;
        if (resultSet.next()){
            pwd = resultSet.getString("password");
            boolean tag = bcryptPasswordEncoder.matches(password_old,pwd);
            if(tag==true) {
                temp = -1;
            }
        }
        return temp;
    }

    @Override
    public int changepassword(String password,String username,HttpSession session) throws SQLException {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        DbUtil dbUtil = new DbUtil();
        User user = new User();
        String hashRegisterPassword = bcryptPasswordEncoder.encode(password);
        String sql = "update user_info set password = '"+hashRegisterPassword +"' where username = '"+ username + "';";
        Boolean execure = new DbUtil().execure(sql);
        user.setUsername(username);
        user.setPassword(password);
        session.setAttribute("user",user);
        System.out.println("密码修改成功！");
        return 0;
    }
}
