package com.edu.bupt.wechatpost.controller;

import com.alibaba.fastjson.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/servicemanagement")
public class ServiceManagement {
    private final static String BASEURL = "http://47.105.120.203:30080/api/v1/servicemanagement/";

    private final static OkHttpClient client = new OkHttpClient();

    @RequestMapping(value = "/abilityGroup", method = RequestMethod.POST)
    @ResponseBody
    public String addAbilityGroup(@RequestBody JSONObject message) throws IOException{
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "abilityGroup")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().toString();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/abilityGroup", method = RequestMethod.GET)
    @ResponseBody
    public String getAllAbilityGroups()throws IOException {
        Request request = new Request.Builder()
                .url(BASEURL + "abilityGroup")
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().toString();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/abilityGroup", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteAllAbilityGroups(Integer modelId)throws IOException {
        Request request = new Request.Builder()
                .url(BASEURL + "abilityGroup?modelId=" + modelId)
                .delete()
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().toString();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/abilityGroup/manufacturers", method = RequestMethod.GET)
    @ResponseBody
    public String getManufacturers(String keyword)throws IOException {
        String url = BASEURL + "abilityGroup/manufacturers";
        if(keyword != null) {
            url = url + "?Keyword=" +  keyword;
        }
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().toString();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/abilityGroup/deviceTypes", method = RequestMethod.GET)
    @ResponseBody
    public String getDeviceTypes(String manufacturerId,
                                 @RequestParam(value = "keyword",required = false) String keyword)
            throws IOException {
        String url = BASEURL + "abilityGroup/deviceTypes?manufacturerId=" +  manufacturerId;
        if(keyword != null){
            url = url + "&keyword=" + keyword;
        }
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().toString();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/abilityGroup/models", method = RequestMethod.GET)
    @ResponseBody
    public String getModels(String manufacturerId,
                            @RequestParam(value = "keyword",required = false) String keyword,
                            Integer deviceTypeId)
            throws IOException {
        String url = BASEURL +
                "abilityGroup/deviceTypes?manufacturerId=" +  manufacturerId +
                "&deviceTypeId=" + deviceTypeId ;
        if(keyword != null){
            url = url + "&keyword=" + keyword;
        }
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().toString();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/ability", method = RequestMethod.POST)
    @ResponseBody
    public String saveAbility(@RequestBody JSONObject message)throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL+"ability")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().toString();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/ability/{abilityId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteAbility(@PathVariable("abilityId")Integer abilityId)throws IOException {
        Request request = new Request.Builder()
                .url(BASEURL + "ability/" + abilityId)
                .delete()
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().toString();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/ability/{modelId}", method = RequestMethod.GET)
    @ResponseBody
    public String findAbilitiesByModelId(@PathVariable("modelId")Integer modelId)throws IOException {
        Request request = new Request.Builder()
                .url(BASEURL + "ability/" + modelId)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().toString();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/ability/{manufacturerName}/{deviceTypeName}/{modelName}", method = RequestMethod.GET)
    @ResponseBody
    public String findAbilitiesByModelId(@PathVariable("manufacturerName")String manufacturerName,
                                         @PathVariable("deviceTypeName")String deviceTypeName,
                                         @PathVariable("modelName")String modelName)throws IOException {
        Request request = new Request.Builder()
                .url(BASEURL + manufacturerName + "/" + deviceTypeName + "/" + modelName)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().toString();
            System.out.println(result);
        }
        return result;
    }

}
