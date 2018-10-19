package com.edu.bupt.wechatpost.controller;

import com.alibaba.fastjson.JSONObject;
import com.edu.bupt.wechatpost.model.Result;
import com.squareup.okhttp.*;
import com.squareup.okhttp.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/deviceaccess")
public class DeviceAccessController {
    private static final Logger logger = LoggerFactory.getLogger(DeviceAccessController.class);

    private static String BASEURL = "http://47.105.120.203:30080/api/v1/deviceaccess/";

    private static OkHttpClient client = new OkHttpClient();

    @RequestMapping(value = "/device/{deviceId}", method = RequestMethod.GET)
    @ResponseBody
    public String getDeviceById(@PathVariable("deviceId")String deviceId)throws IOException{
        Result res = new Result();
        Request request = new Request.Builder()
                    .get()
                    .url(BASEURL + "device/"+deviceId)
                    .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result);
        } else{
            res.setStatus("error");
            result = res.toString();
        }
        return result;
    }

    @RequestMapping(value = "/tenant/devices/{tenantId}", method = RequestMethod.GET)
    @ResponseBody
    public String getTenantdevices(@PathVariable("tenantId") Integer tenantId, Integer limit,
                                       @RequestParam(value = "textSearch", required = false)String textSearch,
                                       @RequestParam(value = "idOffset", required = false)String idOffset,
                                       @RequestParam(value = "textOffset",required = false)String textOffset)
            throws Exception{
        String url = "tenant/devices/" + tenantId + "?limit=" + limit;
        if (textSearch != null){
            url = url + "&textSearch=" + textSearch;
        }
        if (idOffset != null){
            url = url + "&idOffset=" + idOffset;
        }
        if (textOffset != null){
            url = url + "&textOffset=" + textOffset;
        }
            Request request = new Request.Builder()
                .get()
                .url(BASEURL + url)
                .build();
        Response response = client.newCall(request).execute();
        Result res = new Result();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/customerdevices/{tenantId}/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public String getCustomerdevices(@PathVariable("tenantId")Integer tenantId,
                                         @PathVariable("customerId") Integer Customer, Integer limit,
                                         @RequestParam(value = "textSearch", required = false)String textSearch,
                                         @RequestParam(value = "idOffset", required = false)String idOffset,
                                         @RequestParam(value = "textOffset",required = false)String textOffset)
        throws Exception{
        String url = "customerdevices/" + tenantId + "/" + Customer + "?limit=" + limit;
        if (textSearch != null){
            url = url + "&textSearch=" + textSearch;
        }
        if (idOffset != null){
            url = url + "&idOffset=" + idOffset;
        }
        if (textOffset != null){
            url = url + "&textOffset=" + textOffset;
        }
        Request request = new Request.Builder()
                .get()
                .url(BASEURL + url)
                .build();
        Response response = client.newCall(request).execute();
        Result res = new Result();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result);
            return result;
        }else{
            res.setStatus("error");
            return res.toString();
        }
    }

    @RequestMapping(value = "/allattributes/{deviceId}", method = RequestMethod.GET)
    @ResponseBody
    public String getAllAttributes(@PathVariable("deviceId") String deviceId) throws Exception{
        Request request = new Request.Builder()
                .get()
                .url(BASEURL + "allattributes/" + deviceId)
                .build();
        Response response = client.newCall(request).execute();
        Result res = new Result();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/data/alldata/{deviceId}", method = RequestMethod.GET)
    @ResponseBody
    public String getAllData(@PathVariable("deviceId") String deviceId, String key, String startTs, String endTs, Integer limit) throws  Exception{
        String url = "data/alldata/" + deviceId + "?key=" + key + "&startTs=" + startTs + "&endTs=" + endTs + "&limit=" + limit;
        Request request = new Request.Builder()
                .get()
                .url(BASEURL + url)
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/data/alllatestdata/{deviceId}", method = RequestMethod.GET)
    @ResponseBody
    public String getlatestData(String deviceId) throws Exception{
        Request request = new Request.Builder()
                .get()
                .url(BASEURL + "data/alllatestdata/" + deviceId)
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/group", method = RequestMethod.POST)
    @ResponseBody
    public String saveGroup(@org.springframework.web.bind.annotation.RequestBody JSONObject Group) throws Exception{
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, Group.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "group")
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

    @RequestMapping(value = "/group", method = RequestMethod.DELETE)  // 405 方法错误
    @ResponseBody
    public String deleteDevice(String groupId)throws Exception{
        Request request = new Request.Builder()
                .url(BASEURL + "group?groupId=" + groupId)
                .delete()
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/group/devices/{groupId}", method = RequestMethod.GET)
    @ResponseBody
    public String getCustomerdevices(@PathVariable("groupId")Integer groupId, Integer limit,
                                         @RequestParam(value = "textSearch", required = false)String textSearch,
                                         @RequestParam(value = "idOffset", required = false)String idOffset,
                                         @RequestParam(value = "textOffset",required = false)String textOffset)
            throws Exception{
        String url = "group/devices/" + groupId + "?limit=" + limit;
        if (textSearch != null){
            url = url + "&textSearch=" + textSearch;
        }
        if (idOffset != null){
            url = url + "&idOffset=" + idOffset;
        }
        if (textOffset != null){
            url = url + "&textOffset=" + textOffset;
        }
        Request request = new Request.Builder()
                .url(BASEURL + url)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/assign/group/{groupId}/{deviceId}", method = RequestMethod.GET)
    @ResponseBody
    public String assignDeviceToGroup(@PathVariable("deviceId") String deviceId, @PathVariable("groupId") String GroupId) throws Exception{
        Request request = new Request.Builder()
                .url(BASEURL + "assign/group/" + GroupId + "/"+ deviceId)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/unassign/group/{groupId}/{deviceId}", method = RequestMethod.GET)  //405
    @ResponseBody
    public String cancelAssignDeviceToGroup(@PathVariable("groupId") String GroupId, @PathVariable("deviceId") String deviceId) throws Exception{
        Request request = new Request.Builder()
                .url(BASEURL + "unassign/group/"+ GroupId + "/"+ deviceId)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/groups/customer/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public String getCustomergroups(@PathVariable("customerId") Integer customerId, Integer limit,
                                      @RequestParam(value = "textSearch", required = false)String textSearch,
                                      @RequestParam(value = "idOffset", required = false)String idOffset,
                                      @RequestParam(value = "textOffset",required = false)String textOffset)
        throws Exception{
        String url = "groups/customer/" + customerId + "?limit=" + limit;
        if (textSearch != null){
            url = url + "&textSearch=" + textSearch;
        }
        if (idOffset != null){
            url = url + "&idOffset=" + idOffset;
        }
        if (textOffset != null){
            url = url + "&textOffset=" + textOffset;
        }
        Request request = new Request.Builder()
                .url(BASEURL + url)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/rpc/{deviceId}/{requestId}", method = RequestMethod.POST)//400
    @ResponseBody
    public String controlDevice(@org.springframework.web.bind.annotation.RequestBody JSONObject message,
                                    @PathVariable("deviceId")String deviceId,
                                    @PathVariable("requestId")Integer requestId)
            throws Exception{
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "rpc/"+ deviceId + "/" + requestId)
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

    @RequestMapping(value = "controlDevice", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String websocket()throws Exception{
        String result = new String();
        return result;
    }
}
