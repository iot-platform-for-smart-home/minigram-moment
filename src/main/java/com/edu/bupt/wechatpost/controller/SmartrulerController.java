package com.edu.bupt.wechatpost.controller;

import com.alibaba.fastjson.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/smartruler")
public class SmartrulerController {
    private final String BASEURL = "http://47.105.120.203:30080/api/v1/smartruler/";
    private static OkHttpClient client = new OkHttpClient();

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String createRule(@RequestBody JSONObject message){
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "add")
                .post(body)
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/remove/{ruleId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteRule(@PathVariable("ruleId")String ruleId){
        Request request = new Request.Builder()
                .url(BASEURL + "remove/" + ruleId)
                .delete()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/rule/{ruleId}", method = RequestMethod.GET)
    @ResponseBody
    public String getRuleById(@PathVariable("ruleId")String ruleId){
        Request request = new Request.Builder()
                .url(BASEURL + "rule/" + ruleId)
                .get()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/{ruleId}/activate", method = RequestMethod.POST)
    @ResponseBody
    public String activateRuleById(@PathVariable("ruleId")String ruleId){
        Request request = new Request.Builder()
                .url(BASEURL + ruleId + "/activate")
                .post(null)
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/{ruleId}/suspend", method = RequestMethod.POST)
    @ResponseBody
    public String suspendRuleById(@PathVariable("ruleId")String ruleId){
        Request request = new Request.Builder()
                .url(BASEURL + ruleId + "/suspend")
                .post(null)
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/rules", method = RequestMethod.GET)
    @ResponseBody
    public String getRuleById(){
        Request request = new Request.Builder()
                .url(BASEURL + "rules")
                .get()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/plugin/all", method = RequestMethod.GET)
    @ResponseBody
    public String getPlugins(){
        Request request = new Request.Builder()
                .url(BASEURL + "plugin/all")
                .get()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/ruleByCustomer/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public String getRulesByCustomerId(@PathVariable("customerId")Integer customerId){
        Request request = new Request.Builder()
                .url(BASEURL + "ruleByCustomer/"+ customerId)
                .get()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/ruleByCustomer/{customerId}/{textSearch}", method = RequestMethod.GET)
    @ResponseBody
    public String getRulesByTenantIdAndText(@PathVariable("customerId")Integer customerId,
                                            @PathVariable("textSearch")String textSearch){
        Request request = new Request.Builder()
                .url(BASEURL + "ruleByCustomer/"+ customerId + "/" + textSearch)
                .get()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }

    @RequestMapping(value = "/alarmRule/suspend/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public String suspendAlarmRule(@PathVariable("customerId")Integer customerId){
        Request request = new Request.Builder()
                .url(BASEURL + "alarmRule/suspend/" + customerId )
                .get()
                .build();
        String result = new String();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                System.out.println(result);
            }
        } catch(Exception e){
            e.printStackTrace();
            result = "error";
        } finally {
            return result;
        }
    }
}
