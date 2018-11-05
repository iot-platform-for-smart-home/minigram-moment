package com.edu.bupt.wechatpost.controller;

import com.alibaba.fastjson.JSONObject;
import com.edu.bupt.wechatpost.model.Result;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/deviceaccess")
public class DeviceAccessController {

    private static String BASEURL = "http://47.105.120.203:30080/api/v1/deviceaccess/";

    private static OkHttpClient client = new OkHttpClient();

    @RequestMapping(value = "/assignAll/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public Integer assignGateway2User(@PathVariable("customerId")Integer customerId, @RequestParam("gateway_user") String gateway_user)throws IOException{
        System.out.print("扫码分配设备：");
        Request request = new Request.Builder()
                .get()
                .url(BASEURL + "assignAll/"+customerId+"?gateway_user="+gateway_user)
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result+"\n");
            return 1;
        } else {
            return 0;
        }
    }

    @RequestMapping(value = "/unassign/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public Integer unassignGateway2User(@PathVariable("customerId")Integer customerId, @RequestParam("gateway_name") String gateway_name)throws IOException{
        System.out.println("\n解绑网关设备...");
        Request request = new Request.Builder()
                .get()
                .url(BASEURL + "unassign/"+customerId+"?gateway_name="+gateway_name)
                .build();
        Response response = client.newCall(request).execute();
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result+"\n");
            return 1;
        } else {
            return 0;
        }
    }

    @RequestMapping(value = "/device", method = RequestMethod.POST)
    @ResponseBody
    public String createDevice(@org.springframework.web.bind.annotation.RequestBody JSONObject message) throws Exception{
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, message.toJSONString());
        Request request = new Request.Builder()
                .url(BASEURL + "device")
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
        String result = new String();
        if(response.isSuccessful()){
            result = response.body().string();
            System.out.println(result);
        }
        return result;
    }

    @RequestMapping(value = "/data/alldata/{deviceId}", method = RequestMethod.GET)
    @ResponseBody
    public String getAllData(@PathVariable("deviceId") String deviceId, String key, String startTs,
                             String endTs, Integer limit, Integer interval, String aggregation) throws  Exception{
        String url = "data/alldata/" + deviceId + "?key=" + key + "&startTs=" + startTs + "&endTs="
                + endTs + "&limit=" + limit + "&interval=" + interval + "&aggregation=" + aggregation;
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

    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteDevice(@PathVariable("groupId") String groupId)throws Exception{
        Request request = new Request.Builder()
                .url(BASEURL + "group/" + groupId)
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
    public String getCustomerdevices(@PathVariable("groupId")String groupId, Integer limit,
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
        if(result == ""){
            result = "[]";
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

    @RequestMapping(value = "/unassign/group/{groupId}/{deviceId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String cancelAssignDeviceToGroup(@PathVariable("groupId") String GroupId, @PathVariable("deviceId") String deviceId) throws Exception{
        Request request = new Request.Builder()
                .url(BASEURL + "unassign/group/"+ GroupId + "/"+ deviceId)
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

    @RequestMapping(value = "/rpc/{deviceId}/{requestId}", method = RequestMethod.POST)
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
            System.out.println("result=" + result);
        }
        if(result ==""){
            Result resultJson = new Result();
            resultJson.setStatus("success");
            return resultJson.toString();
        }
        return result;
    }

    @RequestMapping(value = "/parentdevices/{parentdeviceId}", method = RequestMethod.GET)
    @ResponseBody
    public String getDevicesByParentDeviceId(@PathVariable("parentdeviceId") String parentdeviceId, Integer limit,
                                    @RequestParam(value = "textSearch", required = false)String textSearch,
                                    @RequestParam(value = "idOffset", required = false)String idOffset,
                                    @RequestParam(value = "textOffset",required = false)String textOffset)
            throws Exception{
        String url = "parentdevices/" + parentdeviceId + "?limit=" + limit;
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


}
