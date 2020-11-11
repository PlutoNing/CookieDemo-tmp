package com.example.blockchain.DTO;


import com.example.blockchain.Entity.User;
import com.example.blockchain.util.DbUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;


public class UserDao {

    public int login(User user, HttpSession session) throws SQLException {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        DbUtil dbUtil = new DbUtil();
        String username = user.getUsername();
        String password = user.getPassword();
        String sql = "Select * from user_info where username = '" + username + "' ";
        ResultSet resultSet = dbUtil.resultSet(sql);
        int temp = 0;
        if (resultSet.next()){
            String pwd = resultSet.getString("password");
            boolean tag = bcryptPasswordEncoder.matches(password,pwd);
            if(tag==true) {
                temp = -1;
            }
        }

        return temp;
    }

    public static String getRandomString2(int length){
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(3);
            long result=0;
            switch(number){
                case 0:
                    result=Math.round(Math.random()*25+65);
                    sb.append(String.valueOf((char)result));
                    break;
                case 1:
                    result=Math.round(Math.random()*25+97);
                    sb.append(String.valueOf((char)result));
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }


        }
        return sb.toString();
    }

    public Boolean register(User user) throws SQLException {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String username = user.getUsername();
        String password = user.getPassword();
        String phone = user.getPhone();
        String userid = getRandomString2(8);
        String email = user.getEmail();
        String hashRegisterPassword = bcryptPasswordEncoder.encode(password);
        char enable = '1';
        char group = '0';

        String sql = "Select * from user_info where username = '"+ username + "';";
        ResultSet resultSet = new DbUtil().resultSet(sql);
        if(resultSet.next()){
            return false;
        }
        String sql1 = "insert into user_info (`userid`,`username`,`password`,`enable`,`phone`,`group`,`email`) values ('"+userid+"','"+ username + "','"+ hashRegisterPassword +"','"+enable+"','"+phone+"','"+group+"','"+email+"');";
        Boolean execure = new DbUtil().execure(sql1);
        return true;
    }
}


