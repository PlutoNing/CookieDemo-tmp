package com.example.blockchain.service;

import com.example.blockchain.Entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public interface changepasswordService {
     int checkpassword(String password, String username) throws SQLException;

    int checkinput(String password1, String password2) throws SQLException;
    int changepassword(String password, String username, HttpSession session)  throws SQLException;
}
