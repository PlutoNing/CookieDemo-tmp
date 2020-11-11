package com.example.blockchain.service.serviceimpl;

import com.example.blockchain.DTO.UserDao;
import com.example.blockchain.Entity.User;
import com.example.blockchain.service.LoginService;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public int login(User user, HttpSession session) throws SQLException {
        int temp;
        temp = new UserDao().login(user,session);
        return temp;
    }

    @Override
    public boolean register(User user) throws SQLException {
        if (new UserDao().register(user)==true) {
            return true;
        }
        else return false;
    }
}
