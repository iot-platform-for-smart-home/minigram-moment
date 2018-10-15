package com.edu.bupt.wechatpost.controller;

import com.alibaba.fastjson.JSONObject;
import com.edu.bupt.wechatpost.model.Comment;
import com.edu.bupt.wechatpost.model.Post;
import com.edu.bupt.wechatpost.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wechatPost")
public class Controller {

//    @Autowired
//    private CommentService commentService;
//
//    @Autowired
//    private PostService postService;
    @Autowired
    private PostCommentService postCommentService;


//    @RequestMapping(value = "/findAllPosts", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Post> findAllPost(String openId , Integer page) {
//        List<Post> results = postService.findAll(openId, page);
//        return results;
//    }

    @RequestMapping(value = "/findAllPosts", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findAllPost(@RequestParam(value="openId", required = false) String openId , Integer page) throws Exception{
        JSONObject result = new JSONObject();
        try {
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

//    @RequestMapping(value = "/findAPost", method = RequestMethod.GET)
//    @ResponseBody
//    public Post findAPost(String openId, Integer pId){
//        Post result = postCommentService.findAPost(openId, pId);
//        return result;
//    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject findPosts(String searchText, Integer page) throws Exception{
        JSONObject result = new JSONObject();
        try {
            List<Post> posts = postCommentService.searchPosts(searchText, page);
            if (posts.size() != 0){
                result.put("data", posts);
            } else{
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

    @RequestMapping(value = "/addPost", method = RequestMethod.POST)
    @ResponseBody
        public Integer addPost(String openId, String nickName, String avatar, String content, String image, String location) throws Exception {
        try {
            java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
            String s = format.format(new Date());
            Post myPost = new Post(openId, avatar, nickName, s, content,image, location, 0);
            postCommentService.publishPost(myPost);
            return 1;
        } catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @RequestMapping(value = "/deletePost", method = RequestMethod.POST)
    @ResponseBody
    public Integer deletePost(Integer pId){
        return postCommentService.deletePost(pId);
    }

//    @RequestMapping(value = "/updatePost", method = RequestMethod.POST)
//    @ResponseBody
//    public Integer updatePost(String openId, Integer pId, String nickName, String avatar, String content, String image, String location){
//        java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
//        String s = format.format(new Date());
//        Post myPost = new Post(pId, openId, avatar, nickName, s, content, image, location, 0);
//        return  postService.updatePost(myPost);
//    }

    @RequestMapping(value = "/favorite", method = RequestMethod.POST)
    @ResponseBody
    public Integer updateFavoriteNum(Integer pId, Integer num){
        return postCommentService.favorite(pId, num);
    }

//    @RequestMapping(value = "/findComment", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Comment> findAllCommentByPostId(Integer pId, Integer page) throws Exception{
//        try {
//            List<Comment> myComment = commentService.findByPostId(pId, page);
//            return myComment;
//        } catch (Exception e) {
//            e.printStackTrace();;
//            return null;
//        }
//    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    @ResponseBody
    public Integer addComment(Integer pId, String nickName, String cContent)throws Exception{
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
    public Integer deleteComment(Integer cId) {
        return postCommentService.deleteComment(cId);
    }
}