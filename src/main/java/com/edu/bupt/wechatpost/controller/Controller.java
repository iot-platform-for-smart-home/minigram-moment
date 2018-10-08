package com.edu.bupt.wechatpost.controller;

import com.edu.bupt.wechatpost.model.Comment;
import com.edu.bupt.wechatpost.model.Post;
import com.edu.bupt.wechatpost.service.CommentService;
import com.edu.bupt.wechatpost.service.PostService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wechatPost")
public class Controller {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;


    @RequestMapping(value = "/findAllPosts", method = RequestMethod.GET)
    @ResponseBody
    public List<Post> findAllPost(int page) {
        List<Post> results = postService.findAll(page);
        return results;
    }

    @RequestMapping(value = "/findAllPostsByOpenId", method = RequestMethod.GET)
    @ResponseBody
    public List<Post> findAllPostByOpenId(String openId, int page) {
        List<Post> results = postService.findByOpenId(openId,page);
        return results;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public List<Post> findPosts(String searchText, int page) {
        List<Post> results = postService.findPost(searchText, page);
        return results;
    }

    @RequestMapping(value = "/addPost", method = RequestMethod.POST)
    @ResponseBody
    public void addPost(String openId, String nickName, String pAvator, String pContent, String image, String location) {
//        JsonObject msg = new JsonParser().parse(msgInfo).getAsJsonObject();
        Post myPost = new Post();
        myPost.setOpenId(openId);
        myPost.setNickName(nickName);
        myPost.setpAvatar(pAvator);
        myPost.setTimeStamp(new Date());
        myPost.setpContent(pContent);
        myPost.setImage(image);
        myPost.setLocation(location);
        myPost.setFavoritenum(0);
        postService.addPost(myPost);
    }

    @RequestMapping(value = "/deletePost", method = RequestMethod.POST)
    @ResponseBody
    public void deletePost(String openId, int pId) {
        postService.deletePost(openId,pId);
    }

    @RequestMapping(value = "/updatePost", method = RequestMethod.POST)
    @ResponseBody
    public void updatePost(String openId, int pId, String nickName, String pAvator, String pContent, String image, String location,int favoriteNum) {
//        JsonObject msg = new JsonParser().parse(msgInfo).getAsJsonObject();
        Post myPost = new Post();
        myPost.setOpenId(openId);
        myPost.setpId(pId);
        myPost.setNickName(nickName);
        myPost.setpAvatar(pAvator);
        myPost.setTimeStamp(new Date());
        myPost.setpContent(pContent);
        myPost.setImage(image);
        myPost.setLocation(location);
        myPost.setFavoritenum(favoriteNum);

        postService.updatePost(myPost);
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.POST)
    @ResponseBody
    public void updateFavoriteNum(String nickName, int pId, int num) {
        postService.updateFavoriteNum(nickName, pId, num);
    }

    @RequestMapping(value = "/findComment", method = RequestMethod.GET)
    @ResponseBody
    public List<Comment> findAllCommentByPostId(String openId, int pId, int page) {
//        JsonObject msg = new JsonParser().parse(msgInfo).getAsJsonObject();
//        String openId = msg.get("openId").getAsString();
//        Integer postId = msg.get("pId").getAsInt();
//        Integer page = msg.get("page").getAsInt();
        List<Comment> myComment = commentService.findByPostId(openId, pId, page);
        return myComment;
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    @ResponseBody
    public void addComment(String openId, int pId, int cId, String cAvator, String cContent){
//        JsonObject msg = new JsonParser().parse(msgInfo).getAsJsonObject();
        Comment comment = new Comment();
        comment.setOpenId(openId);
        comment.setpId(pId);
        comment.setcId(cId);
        comment.setcAvatar(cAvator);
        comment.setcContent(cContent);
        commentService.addComment(comment);
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
    @ResponseBody
    public void deleteComment(String openId, int pId, int cId) {
//        JsonObject msg = new JsonParser().parse(msgInfo).getAsJsonObject();
//        String openId = msg.get("openId").getAsString();
//        Integer pId = msg.get("pId").getAsInt();
//        Integer cId = msg.get("cId").getAsInt();
        commentService.deleteComment(openId,pId,cId);
    }

    @RequestMapping(value = "/updateComment", method = RequestMethod.POST)
    @ResponseBody
    public void updateComment(String openId, int pId, String cAvator, String cContent) {
//        JsonObject msg = new JsonParser().parse(msgInfo).getAsJsonObject();
        Comment comment = new Comment();
        comment.setOpenId(openId);
        comment.setpId(pId);
        comment.setcAvatar(cAvator);
        comment.setcContent(cContent);
        commentService.updateComment(comment);
    }


}
