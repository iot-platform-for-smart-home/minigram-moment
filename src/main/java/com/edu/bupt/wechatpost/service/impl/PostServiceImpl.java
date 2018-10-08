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
    public List<Post> findAll(Integer page){
        List<Post> posts = postMapper.findAll(page);
        int left = page * 9;
        int right = (page+1)*9 < posts.size() ? (page+1)*9 : posts.size();
        return posts.subList(left, right);
    }

    @Override
    public List<Post> findByOpenId(String openId, Integer page) {
        List<Post> posts = new ArrayList<>(postMapper.findAllByOpenId(openId));
        int left = page * 9;
        int right = (page+1)*9 < posts.size() ? (page+1)*9 : posts.size();
        return posts.subList(left, right);
    }

    @Override
    public List<Post> findPost(String searchText, Integer page) {
        List<Post> posts = new ArrayList<>(postMapper.selectByPrimaryKeySelective(searchText));
        int left = page * 9;
        int right = (page+1)*9 < posts.size() ? (page+1)*9 : posts.size();
        List<Post> myPosts = new ArrayList<>(posts.subList(left, right));
        return myPosts;
    }

    @Override
    public void addPost(Post myPost) {
        postMapper.insert(myPost);
    }

    @Override
    public void deletePost(String openId, Integer postId) {
        postMapper.deleteByPrimaryKey(postId, openId);
    }

    @Override
    public void updatePost(Post myPost) {
        postMapper.updateByPrimaryKeySelective(myPost);
    }

    @Override
    public void updateFavoriteNum(String nickName, int pId, int num) { postMapper.updateFavoriteNum(nickName, pId, num); }
}
