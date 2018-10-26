package com.edu.bupt.wechatpost.controller;

import com.alibaba.fastjson.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    private final static String BASEURL = "http://47.105.120.203:30080/api/v1/account/";

    private static OkHttpClient client = new OkHttpClient();

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestBody JSONObject message) throws Exception{
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "createUser")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    @ResponseBody
    public String userLogin(@RequestBody JSONObject message) throws Exception{
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "userLogin")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result);
        }
        return result;
    }
}
