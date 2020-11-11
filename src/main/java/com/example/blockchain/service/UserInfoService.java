package com.example.blockchain.service;

import com.example.blockchain.Entity.User;

import java.sql.SQLException;

public interface UserInfoService {
    public User getUserInfo(String username) throws SQLException;

    public int setUserInfo(String username_old,String username ,String email,String phone) throws SQLException;
}
