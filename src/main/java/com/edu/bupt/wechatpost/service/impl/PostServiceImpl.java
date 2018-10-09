package com.edu.bupt.wechatpost.service.impl;

import com.edu.bupt.wechatpost.dao.PostMapper;
import com.edu.bupt.wechatpost.model.Post;
import com.edu.bupt.wechatpost.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Override
    public List<Post> findAll(String openId, Integer page){
        List<Post> posts = postMapper.findAll(openId);
        int left = page * 9;
        int right = (page+1)*9 < posts.size() ? (page+1)*9 : posts.size();
        return posts.subList(left, right);
    }

    @Override
    public Post findAPost(String openId, Integer pId) {
        return postMapper.selectByPrimaryKey(openId, pId);
    }

    @Override
    public List<Post> findPost(String searchText, Integer page) {
        List<Post> posts = postMapper.selectByKeySelective(searchText);
        int left = page * 9;
        int right = (page+1)*9 < posts.size() ? (page+1)*9 : posts.size();
        List<Post> myPosts = new ArrayList<>(posts.subList(left, right));
        return myPosts;
    }

    @Override
    public void addPost(Post myPost){
        postMapper.insert(myPost);
    }

    @Override
    public Integer deletePost(String openId, Integer postId) {
        return postMapper.deleteByPrimaryKey(postId, openId);
    }

    @Override
    public Integer updatePost(Post myPost) {
        return postMapper.updateByPrimaryKeySelective(myPost);
    }

    @Override
    public Integer updateFavoriteNum(String nickName, Integer pId, Integer num) { return postMapper.updateFavoriteNum(nickName, pId, num); }
}
