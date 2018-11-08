package com.edu.bupt.wechatpost.controller;

import com.edu.bupt.wechatpost.service.DeviceTokenRelationService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ZigBeeController {
    private static String URLPREDIX = "http://ip:port/api/v1/";
    private static OkHttpClient client = new OkHttpClient();
    private static final Logger logger = LoggerFactory.getLogger(ZigBeeController.class);

    @Autowired
    private DeviceTokenRelationService deviceTokenRelationService;

    @RequestMapping(value = "/device/deleteDevice/{uuid}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteDevice(@PathVariable("uuid") String uuid)throws IOException {
        logger.info("Tring to delete device(s)...");
        // 查找IEEE地址
        String ieee = deviceTokenRelationService.findIEEEByUuid(uuid);
        System.out.println("IEEE:"+ ieee);
        // 查找其余设备的uuid
        List<String> uuidList = deviceTokenRelationService.findUuidByIEEE(ieee);
        System.out.println("uuid:" + uuidList);
        // 调用deviceaccess接口删除cassandra数据库记录
        String urlPrefix = URLPREDIX;
        urlPrefix = urlPrefix.replace("ip","47.105.120.203")
                .replace("port","30080");
        for(int i = 0; i < uuidList.size(); i++){
            // 删除mysql中设备关系表中的设备记录
            Request request = new Request.Builder()
                    .url(urlPrefix + "deviceaccess/device/" + uuidList.get(i))
                    .delete()
                    .build();
            try {
                client.newCall(request).execute();
            }catch (IOException e){
                logger.warn("Error occurs in deleting records in cassandra!");
            }
        }
        logger.info("Succeed in deleting records in cassandra! Now for Mysql...");
        urlPrefix = URLPREDIX;
        urlPrefix = urlPrefix.replace("ip","47.104.8.164")
                .replace("port","8800");
        Request request = new Request.Builder()
                .url(urlPrefix + "device/deleteDevice/" + uuid)
                .delete()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                logger.info("Succeed in deleting records in Mysql!");
                return "success";
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        logger.warn("Error occurs in deleting records in Mysql!");
        return "fail";
    }

    @RequestMapping(value = "/device/addNewDevice/{gateway_name}",method = RequestMethod.GET)
    @ResponseBody
    public String addNewDevice(@PathVariable("gateway_name")String gateway_name)throws Exception{
        logger.info("permit devices join gateway...");
        String urlPrefix = URLPREDIX;
        urlPrefix = urlPrefix.replace("ip","47.104.8.164")
                .replace("port","8800");
        Request request = new Request.Builder()
                .url(urlPrefix + "device/addNewDevice/" + gateway_name)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                logger.info("Call permitDeviceJoinGateway success!");
                return "success";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        logger.warn("Call permitDeviceJoinGateway fail!");
        return "fail";
    }
}
