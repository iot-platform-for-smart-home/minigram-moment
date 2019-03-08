package com.edu.bupt.wechatpost.controller;

import com.alibaba.fastjson.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/controller")
public class Controller {
    private final String ACCOUNT_URL = "http://47.105.120.203:30080/api/v1/account/";
    private final String RULER_URL = "http://47.105.120.203:30080/api/v1/smartruler/";
    private static String DEVICEACCESS_URL = "http://47.105.120.203:30080/api/v1/deviceaccess/";
    private static OkHttpClient client = new OkHttpClient();


    @RequestMapping(value = "/unassign", method = RequestMethod.POST)
    @ResponseBody
    public Integer unassignGateway(@RequestBody JSONObject message) {
        String customerId = message.getString("customerid");
        String gatewayId = message.getString("gateid");
        String gatewayName = message.getString("gatewayName");

        // 删除客户绑定 account
        final MediaType JSON = MediaType.parse("application/json,text/plain; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(ACCOUNT_URL + "unBindedALLGate")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println("\n[unbind gateway] succeed in connecting to account.\n"
                        + response.body().string());
            }
        } catch (IOException e){
            e.printStackTrace();
            return 0;
        }

        // 删除网关上设置的规则 Gantch_ruler
        request = new Request.Builder()
                .put(null)
                .url(RULER_URL + "rule/transform/" + gatewayId)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println("[unbind gateway] succeed in connecting to ruler\n"
                        + response.body().string());
            }
        } catch (IOException e){
            e.printStackTrace();
            return 0;
        }

        // 删除网关上的所有设备 deviceaccess
        request = new Request.Builder()
                .get()
                .url(DEVICEACCESS_URL + "unassign/" + customerId + "?gateway_name=" + gatewayName)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println("[unbind gateway] succeed in connecting to deviceaccess\n"
                        + response.body().string());
                return 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
