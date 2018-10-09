package com.edu.bupt.wechatpost.controller;

import com.edu.bupt.wechatpost.model.Comment;
import com.edu.bupt.wechatpost.model.Post;
import com.edu.bupt.wechatpost.service.CommentService;
import com.edu.bupt.wechatpost.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Post> findAllPost(String openId , Integer page) {
        List<Post> results = postService.findAll(openId, page);
        return results;
    }

    @RequestMapping(value = "/findAPost", method = RequestMethod.GET)
    @ResponseBody
    public Post findAPost(String openId, Integer pId){
        Post result = postService.findAPost(openId, pId);
        return result;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public List<Post> findPosts(String searchText, Integer page) {
        List<Post> results = postService.findPost(searchText, page);
        return results;
    }

    @RequestMapping(value = "/addPost", method = RequestMethod.POST)
    @ResponseBody
    public Integer addPost(String openId, String nickName, String pAvator, String pContent, String image, String location) throws Exception {
        Post myPost = new Post(openId, pAvator, nickName, new Date(), pContent,image, location, 0);
        try {
            postService.addPost(myPost);
            return 1;
        } catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @RequestMapping(value = "/deletePost", method = RequestMethod.POST)
    @ResponseBody
    public Integer deletePost(String openId, Integer pId){
        return postService.deletePost(openId,pId);
    }

    @RequestMapping(value = "/updatePost", method = RequestMethod.POST)
    @ResponseBody
    public Integer updatePost(String openId, Integer pId, String nickName, String pAvator, String pContent, String image, String location){
        Post myPost = new Post(pId, openId, pAvator, nickName, new Date(), pContent, image, location, 0);
        return  postService.updatePost(myPost);
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.POST)
    @ResponseBody
    public Integer updateFavoriteNum(String nickName, Integer pId, Integer num){
        return postService.updateFavoriteNum(nickName, pId, num);
    }

    @RequestMapping(value = "/findComment", method = RequestMethod.GET)
    @ResponseBody
    public List<Comment> findAllCommentByPostId(Integer pId, Integer page) {
        List<Comment> myComment = commentService.findByPostId(pId, page);
        return myComment;
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    @ResponseBody
    public Integer addComment(Integer pId, String nickName, String cContent)throws Exception{
        try{
            Comment comment = new Comment(pId, nickName, cContent);
            commentService.addComment(comment);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteComment(Integer pId, Integer cId) {
        return commentService.deleteComment(pId,cId);
    }
}