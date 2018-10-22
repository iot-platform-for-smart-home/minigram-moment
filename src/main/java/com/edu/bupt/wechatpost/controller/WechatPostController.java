package com.edu.bupt.wechatpost.controller;

import com.alibaba.fastjson.JSONObject;
import com.edu.bupt.wechatpost.model.Comment;
import com.edu.bupt.wechatpost.model.Post;
import com.edu.bupt.wechatpost.service.DataService;
import com.edu.bupt.wechatpost.service.PostCommentService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/wechatPost")
public class WechatPostController {

    @Autowired
    private PostCommentService postCommentService;

    @Autowired
    private DataService dataService;

    private final static Logger logger = LoggerFactory.getLogger(WechatPostController.class);


    @RequestMapping(value = "/findAllPosts", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findAllPost(@RequestBody JSONObject message) throws Exception{
        logger.info("查询消息...");
        JSONObject result = new JSONObject();
        try {
            String openId = message.getString("openId");
            Integer page = message.getInteger("page");
            List<Post> posts = postCommentService.findAllPosts(openId, page);
            if (posts.size() != 0){
                result.put("data", posts);
            } else {
                result.put("data",0);
            }
            return result;
        } catch (Exception e){
            e.printStackTrace();
            result.put("errorMsg",e.getMessage());
            result.put("data",0);
            return result;
        }
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findPosts(@RequestBody JSONObject message) throws Exception{
        logger.info("搜索消息...");
        JSONObject result = new JSONObject();
        try {
            String searchText = message.getString("searchText");
            Integer page = message.getInteger("page");
            List<Post> posts = postCommentService.searchPosts(searchText, page);
            if (posts.size() != 0){
                result.put("data", posts);
            } else{
                result.put("data",0);
            }
            logger.info("搜索成功");
            return result;
        } catch (Exception e){
            e.printStackTrace();
            result.put("errorMsg",e.getMessage());
            result.put("data",0);
            logger.info("搜索失败");
            return result;
        }
    }

    @RequestMapping(value = "/addPost", method = RequestMethod.POST)
    @ResponseBody
        public Integer addPost(@RequestParam(value = "image", required = false) MultipartFile image,String openId, String nickName,
                               String avatar, String content, String location) throws Exception {
        try {
            logger.info("发布消息...");
            String staticImageUrl = dataService.uploadImage(image);
            java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
            String s = format.format(new Date());
            Post myPost = new Post(openId, avatar, nickName, s, content, staticImageUrl, location, 0);
            logger.info("发布成功\n" + myPost.toString());
            postCommentService.publishPost(myPost);
            return 1;
        } catch(Exception e){
            logger.info("发布失败\n"+ e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    @RequestMapping(value = "/download",method=RequestMethod.GET)
    @ResponseBody
    public void downloadImage(String imageName, HttpServletRequest request, HttpServletResponse response) throws Exception{
        try{
            dataService.downloadImage(imageName, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/deletePost", method = RequestMethod.POST)
    @ResponseBody
    public Integer deletePost(@RequestBody JSONObject message){
        logger.info("删除消息...");
        Integer pId = message.getInteger("pId");
        return postCommentService.deletePost(pId);
    }


    @RequestMapping(value = "/favorite", method = RequestMethod.POST)
    @ResponseBody
    public Integer updateFavoriteNum(@RequestBody JSONObject message){
        Integer pId = message.getInteger("pId");
        Integer num = message.getInteger("num");
        return postCommentService.favorite(pId, num);
    }


    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    @ResponseBody
    public Integer addComment(@RequestBody JSONObject message)throws Exception{
        try{
            Integer pId = message.getInteger("pId");
            String nickName = message.getString("nickName");
            String cContent = message.getString("cContent");
            Comment comment = new Comment(pId, nickName, cContent);
            postCommentService.addComment(comment);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteComment(@RequestBody JSONObject message) {
        Integer cId = message.getInteger("cId");
        return postCommentService.deleteComment(cId);
    }

    @RequestMapping(value = "/getOpenId", method = RequestMethod.POST)
    @ResponseBody
    public String getOpenId(@RequestBody JSONObject message)throws IOException {
        final String appid = "wx5ef2de2111a9a82a";
        final String secret = "e3658cc08939eb93cf45238c536ec1e6";
        final String JSCODE = message.getString("JSCODE");
//        OkHttpClient client = new OkHttpClient();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type='authorization_code'"
                .replace("APPID",appid)
                .replace("SECRET",secret)
                .replace("JSCODE",JSCODE);
        String  returnvalue=GET(url);
        System.out.println(url);//打印发起请求的url
        System.out.println(returnvalue);//打印调用GET方法返回值
        //定义一个json对象。
        JSONObject convertvalue=new JSONObject();
        //将得到的字符串转换为json
        convertvalue=(JSONObject) JSONObject.parse(returnvalue);
        System.out.println("return openid is ："+(String)convertvalue.get("openid")); //打印得到的openid
        System.out.println("return sessionkey is ："+(String)convertvalue.get("session_key"));//打印得到的sessionkey，
        //把openid和sessionkey分别赋值给openid和sessionkey
        String openid=(String) convertvalue.get("openid");
        String sessionkey=(String) convertvalue.get("session_key");//定义两个变量存储得到的openid和session_key.

        return openid;//返回openid

//        Request request = new Request.Builder()
//                .url(url)
//                .get()
//                .build();
//        Response response = client.newCall(request).execute();
//        if(response.isSuccessful()){
//            String result = response.body().string();
//            JSONObject jsonObject = JSONObject.parseObject(result);
//            Integer errcode = jsonObject.getInteger("errcode");
//            if(errcode == 0){
//                return jsonObject.getString("openid");
//            }
//            return jsonObject.getString("errMsg");
//        }
//        return "Bad Request!";
    }

    public static String GET(String url) {
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
            // 异常记录
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
                // 异常记录
            }
        }
        return result;
    }

}