package com.example.blockchain.service.serviceimpl;

import com.example.blockchain.Entity.User;
import com.example.blockchain.service.UserInfoService;
import com.example.blockchain.util.DbUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoServiceImpl implements UserInfoService {
    @Override
    public User getUserInfo(String username) throws SQLException {
        DbUtil dbUtil = new DbUtil();
        String sql = "Select * from user_info where username = '" + username + "' ";
        ResultSet resultSet = dbUtil.resultSet(sql);
        String phone ="";
        String email ="";
        String enable ="";
        String group ="";
        String password="";
        if (resultSet.next()){
            phone = resultSet.getString("phone");
            email = resultSet.getString("email");
            enable = resultSet.getString("enable");
            group = resultSet.getString("group");
            password = resultSet.getString("password");

        }
        User user = new User();
        user.setPhone(phone);
        user.setEmail(email);
        user.setEnable(enable);
        user.setGroup(group);
        user.setPassword(password);
        return user;
    }

    @Override
    public int setUserInfo(String username_old,String username, String email, String phone) throws SQLException {
        DbUtil dbUtil = new DbUtil();
        if(!username_old.equals(username)) {
            String sql = "Select * from user_info where username = '" + username + "';";
            ResultSet resultSet = new DbUtil().resultSet(sql);
            if (resultSet.next()) {
                return 1;
            }
        }
        String sql1 = "Update user_info set username ='"+username+"', phone ='"+phone+"', email = '"+email+"' where username = '" + username_old + "' ";
        Boolean execure = new DbUtil().execure(sql1);
        return 0;
    }


}
