package com.edu.bupt.wechatpost.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu.bupt.wechatpost.model.Comment;
import com.edu.bupt.wechatpost.model.Post;
import com.edu.bupt.wechatpost.service.DataService;
import com.edu.bupt.wechatpost.service.PostCommentService;
import com.edu.bupt.wechatpost.service.WxService;
import com.edu.bupt.wechatpost.service.impl.WxServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wechatPost")
public class WechatPostController {

    @Autowired
    private PostCommentService postCommentService;

    @Autowired
    private DataService dataService;

    @Autowired
    private WxService wxService;

    private final static Logger logger = LoggerFactory.getLogger(WechatPostController.class);


    @RequestMapping(value = "/findAllPosts", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findAllPost(@RequestBody JSONObject message){
        logger.info("查询消息...");
        JSONObject result = new JSONObject();
        String openId = message.getString("openId");
        Integer page = message.getInteger("page");
        try {
            List<Post> posts = postCommentService.findAllPosts(openId, page);
            if (posts.size() != 0){
                result.put("data", posts);
            } else {
                result.put("data",0);
            }
            logger.info("查询成功！");
            return result;
        } catch (Exception e){
            e.printStackTrace();
            result.put("errorMsg",e.getMessage());
            result.put("data",0);
            logger.info("查询失败");
            return result;
        }
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findPosts(@RequestBody JSONObject message){
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

    @RequestMapping(value = "/addPostJson", method = RequestMethod.POST)
    @ResponseBody
    public Integer addPost(@RequestBody JSONObject message){
        logger.info("发布消息...");
        String openId = message.getString("openId");
        String nickName = message.getString("nickName");
        String avatar = message.getString("avatar");
        String content = message.getString("content");
        String location = message.getString("location");
        String images = message.getString("images");
        java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        String s = format.format(new Date());
        if(openId != null){
            try {
                Post myPost = new Post(openId, avatar, nickName, s, content, images, location, 0);
                postCommentService.publishPost(myPost);
                logger.info("发布成功\n" + myPost.toString());
                return 1;
            } catch(Exception e){
                logger.info("发布失败\n"+ e.getMessage());
                e.printStackTrace();
            }
        }else{
            logger.info("openId 为空");
        }
        return 0;
    }

    @RequestMapping(value = "/addPost", method = RequestMethod.POST)
    @ResponseBody
        public Integer addPost(@RequestParam(value = "image", required = false) MultipartFile image,
                               @RequestParam(value = "openId") String openId,
                               @RequestParam(value = "nickName") String nickName,
                               @RequestParam(value = "avatar") String avatar,
                               @RequestParam(value = "content") String content,
                               @RequestParam(value = "location") String location
                               ) throws Exception {
        logger.info("发布消息...");
        java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        String s = format.format(new Date());
        if(openId != null){
            try {
                String staticImageUrl = dataService.uploadImage(image);
                if(!staticImageUrl.equals("")){
                    Post myPost = new Post(openId, avatar, nickName, s, content, staticImageUrl, location, 0);
                    postCommentService.publishPost(myPost);
                    logger.info("发布成功\n" + myPost.toString());
                    return 1;
                }
            } catch(Exception e){
                logger.info("发布失败\n"+ e.getMessage());
                e.printStackTrace();
        }
        }else{
            logger.info("openId 为空");
        }
        return 0;
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam("image") MultipartFile image){
        String result = new String();
        if(image != null){
            try {
                result = dataService.uploadImage(image);
            } catch(IOException e){
                logger.info(e.getMessage());
            }
        }
        return result;
    }

    @RequestMapping(value = "/download",method=RequestMethod.GET)
    @ResponseBody
    public void downloadImage(String imageName, HttpServletRequest request, HttpServletResponse response){
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
    public Integer addComment(@RequestBody JSONObject message){
        Integer pId = message.getInteger("pId");
        String nickName = message.getString("nickName");
        String cContent = message.getString("cContent");
        try{
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
    public String getOpenId(@RequestBody JSONObject message){
        return wxService.getOpenId(message);
    }

    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    @ResponseBody
    public int judgeFollow(@RequestBody JSONObject message){
        return wxService.follow(message);
    }

    @RequestMapping(value = "/registe", method = RequestMethod.POST)
    @ResponseBody
    public void registe(@RequestParam(value = "unionid",required = false)String unionid,
                        @RequestParam(value = "openid", required = false)String oa_openid){
        if(!"".equals(unionid)){
            logger.info("receive message from wechat official account, unionid="+unionid+"\topenid="+oa_openid);
            wxService.registe(unionid, oa_openid);
        }
    }

      /* 将该接口放在了wechatPlugin里  */
//    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
//    @ResponseBody
//    public void getAllUsers(String access_token){
//        wxService.get_and_insert_users(access_token);
//    }


}