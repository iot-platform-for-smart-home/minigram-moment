package com.edu.bupt.wechatpost.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu.bupt.wechatpost.model.LikeRelation;

public interface WxService {
    String getOpenId(JSONObject message);
    int follow(JSONObject message);
    String GET(String url) throws Exception;
    void registe(String unionid, String oa_openid);
    void addUser(String unionid, String oa_openid);
    void get_and_insert_users(String access_token);
    Boolean isLike(String openid, Integer p_id);
    Integer addLikeRelation(LikeRelation relation);
    Integer favorite(Integer pId, Integer num);
    void unfavorite(String openid, Integer p_id);
    JSONArray getTips(String openid);
    JSONArray getUnreadTips(String openid);
    void readTips(String openid);

}
