package com.example.blockchain.service;

import com.example.blockchain.Entity.User;

import java.sql.SQLException;

public interface qqloginService {
    public int haslogined(String openid) throws SQLException;

    public User newuser(String openid) throws SQLException;

    public User olduser(String openid) throws SQLException;

    public String  haspassword(String username,String openid) throws SQLException;
}
