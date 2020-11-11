package com.example.blockchain.service;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public interface WeChatAuthService {
    public String insertintodatabase(String openid,String sessionId,String state) throws SQLException;

    public int checkdatabse(String state) throws SQLException;

    public String getopenid(String state) throws SQLException;

    public String getAuthorizationUrl(String type,String state) throws UnsupportedEncodingException;

    public int deletestate(String state) throws SQLException;
}
