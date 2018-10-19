package com.edu.bupt.wechatpost.controller;

import com.alibaba.fastjson.JSONObject;
import com.edu.bupt.wechatpost.model.Comment;
import com.edu.bupt.wechatpost.model.Post;
import com.edu.bupt.wechatpost.service.DataService;
import com.edu.bupt.wechatpost.service.PostCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

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
}