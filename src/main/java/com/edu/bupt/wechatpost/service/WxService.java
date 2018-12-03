package com.edu.bupt.wechatpost.service;

import com.alibaba.fastjson.JSONObject;

public interface WxService {
    String getOpenId(JSONObject message);
    int follow(JSONObject message);
    String GET(String url) throws Exception;
    void registe(String unionid, String oa_openid);
    void addUser(String unionid, String oa_openid);
    void get_and_insert_users(String access_token);
}
