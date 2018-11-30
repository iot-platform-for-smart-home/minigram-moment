package com.edu.bupt.wechatpost.service;

import com.alibaba.fastjson.JSONObject;
import com.edu.bupt.wechatpost.model.MP2OA;

public interface WxService {
    String getOpenId(JSONObject message);
    int follow(JSONObject message);
    String GET(String url) throws Exception;
}
