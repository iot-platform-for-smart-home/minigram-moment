package com.edu.bupt.wechatpost.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.edu.bupt.wechatpost.dao.MP2OAMapper;
import com.edu.bupt.wechatpost.model.MP2OA;
import com.edu.bupt.wechatpost.service.WxService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class WxServiceImpl implements WxService {
    @Autowired
    MP2OAMapper mp2oaMapper;

    @Override
    public String getOpenId(JSONObject message) {
        final String JSCODE = message.getString("JSCODE");
        final String appid = "wx9e12afc5dec75b6f";
        final String secret = "d0d7b3d2ab48530710a4828003dd1c05";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type='authorization_code'"
                .replace("APPID",appid)
                .replace("SECRET",secret)
                .replace("JSCODE",JSCODE);
        try {
            String returnvalue = GET(url);
            System.out.println(url); // 打印发起请求的url
            System.out.println(returnvalue); // 打印调用GET方法返回值
            JSONObject convertvalue = (JSONObject) JSONObject.parse(returnvalue);   // 将得到的字符串转换为json
            String openid = (String) convertvalue.get("openid");
            String unionid = (String) convertvalue.get("unionid");
            if (!"".equals(unionid)) {
                MP2OA user = new MP2OA(unionid, openid, null);
                mp2oaMapper.insertSelective(user);
            }
            return convertvalue.toJSONString();
        } catch (IOException e){
            e.printStackTrace();
            return "error!";
        }
    }

    @Override
    public int follow(JSONObject message) {
        String unionid = message.getString("unionid");
        String openid = message.getString("openid");
        if(! "".equals(unionid)){
            MP2OA user = mp2oaMapper.selectByUnionid(unionid);
            if (user != null) {
                if (user.getOa_openid() != null){  // 用户已关注微信公众号
                    return 2; // followed
                } else{
                    if (user.getMini_openid() == null){
                        mp2oaMapper.updateMiniOpenid(unionid, openid);  // 小程序openid为空，修改记录
                    }
                    return 1; // unregiste
                }
            } else {
                mp2oaMapper.insertSelective(user); // 未发现该用户，插入新纪录
                return 0;  // empty
            }
        }
        return -1; // unionid is null  unionid为空，检查配置
    }

    @Override
    public String GET(String url) throws IOException {
        String result = "";
        BufferedReader in = null;
        InputStream is = null;
        InputStreamReader isr = null;
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.connect();
            Map<String, List<String>> map = conn.getHeaderFields();
            is = conn.getInputStream();
            isr = new InputStreamReader(is);
            in = new BufferedReader(isr);
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace(); // 异常记录
            }
        }
        return result;
    }

}
