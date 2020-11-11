package com.example.blockchain.service.serviceimpl;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.blockchain.util.DbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSONObject;
import com.example.blockchain.service.WeChatAuthService;

@Service
public class WeChatAuthServiceImpl implements WeChatAuthService {

    private static final String AUTHORIZATION_URL =
            "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";

    // 获取用户 openid 和access——toke 的 URL
    private static final String ACCESSTOKE_OPENID_URL =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    private static final String REFRESH_TOKEN_URL =
            "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";

    private static final String USER_INFO_URL =
            "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    private static final String APP_ID="wxa7484a788e89a7fe";
    private static final String APP_SECRET="fb3ce765dfd943eb7e1edd06dee05a8d";
    private static final String SCOPE = "snsapi_userinfo";//  snsapi_userinfo  snsapi_base

    private String pcCallbackUrl = "https://www.face101.xyz/login/auth"; //pc回调域名

    private String mobileCallbackUrl = "https://www.face101.xyz/login/auth"; //mobile回调域名


    @Override
    public String insertintodatabase(String openid, String sessionId,String state) throws SQLException {
        DbUtil dbUtil = new DbUtil();
        String sql = "insert into user_oauth_test (`openid`,`sessionId`,`state`) values ('"+openid+"','"+ sessionId + "','"+state+"');";
        Boolean execure = new DbUtil().execure(sql);
        return null;
    }

    @Override
    public int checkdatabse(String state) throws SQLException {
        DbUtil dbUtil =new DbUtil();
        String sql = "Select * from user_oauth_test where state = '" + state + "' ";
        ResultSet resultSet = dbUtil.resultSet(sql);
        int temp = 0;
        if (resultSet.next()){
            temp = -1;
        }
        return temp;

    }

    @Override
    public String getopenid(String state) throws SQLException {
        DbUtil dbUtil =new DbUtil();
        String sql = "Select * from user_oauth_test where state = '" + state + "' ";
        ResultSet resultSet = dbUtil.resultSet(sql);
        String openid ="";
        if (resultSet.next()){
            openid = resultSet.getString("openid");
        }
        return openid;
    }

    @Override
    public String getAuthorizationUrl(String type, String state) throws UnsupportedEncodingException {
        String callbackUrl = "";
        Object urlState = "";
        if("pc".equals(type)){//移动端 pc端回调方法不一样
            callbackUrl = URLEncoder.encode(pcCallbackUrl,"utf-8");
            urlState = state;
        }else if("mobile".equals(type)){
            callbackUrl = URLEncoder.encode(mobileCallbackUrl,"utf-8");
            urlState = System.currentTimeMillis();
        }
        String url = String.format(AUTHORIZATION_URL,APP_ID,callbackUrl,SCOPE,urlState);
        return url;
    }

    @Override
    public int deletestate(String state) throws SQLException {
        DbUtil dbUtil =new DbUtil();
        String sql = "delete from user_oauth_test where state = '" + state + "' ";
        ResultSet resultSet = dbUtil.resultSet(sql);
        Boolean execure = new DbUtil().execure(sql);
        return 0;
    }
}
