package com.example.blockchain.service;



import com.example.blockchain.Entity.User;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public interface LoginService {
    int login(User user, HttpSession session) throws SQLException;
    boolean register(User user) throws SQLException;
}
