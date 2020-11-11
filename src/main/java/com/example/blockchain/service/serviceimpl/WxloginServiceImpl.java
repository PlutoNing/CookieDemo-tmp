package com.example.blockchain.service.serviceimpl;

import com.example.blockchain.Entity.User;
import com.example.blockchain.service.WxloginService;
import com.example.blockchain.util.DbUtil;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.blockchain.DTO.UserDao.getRandomString2;


public class WxloginServiceImpl implements WxloginService {
    @Override
    public int haslogined(String openid) throws SQLException {
        DbUtil dbUtil = new DbUtil();
        String sql = "Select * from user_oauth_weixin where idp_username = '" + openid + "' ";
        ResultSet resultSet = dbUtil.resultSet(sql);
        int temp = 0;
        if (resultSet.next()){
                temp = -1;
            }

        return temp;
    }

    @Override
    public User newuser(String openid) throws SQLException {
        String userid = getRandomString2(8);
        String random = getRandomString2(6);
        String username= "wx_"+random;
        String enable = "1";
        char group = '0';
        User user = new User();
        user.setUsername(username);
        user.setUserid(userid);
        DbUtil dbUtil = new DbUtil();
        String sql = "insert into user_oauth_weixin (`userid`,`username`,`idp_username`) values ('"+userid+"','"+ username + "','"+ openid +"');";
        Boolean execure = new DbUtil().execure(sql);
        String sql1 = "insert into user_info (`userid`,`username`,`enable`,`group`) values ('"+userid+"','"+ username + "','"+enable+"','"+group+"');";
        Boolean execure1 = new DbUtil().execure(sql1);
        return user;
    }

    @Override
    public User olduser(String openid) throws SQLException {
        DbUtil dbUtil = new DbUtil();
        String sql = "Select username from user_oauth_weixin where idp_username = '" + openid + "' ";
        ResultSet resultSet = dbUtil.resultSet(sql);
        String username= "";
        if (resultSet.next()){
             username= resultSet.getString("username");
            };
        User user = new User();
        String password= haspassword(username,openid);

        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    @Override
    public String haspassword(String username, String openid) throws SQLException {
        DbUtil dbUtil = new DbUtil();
        String sql1 = "Select password from user_info where username = '" + username + "' ";
        ResultSet resultSet = dbUtil.resultSet(sql1);
        String password = "";
        if (resultSet.next()){
            password= resultSet.getString("password");
        };
        return password;
    }

}
