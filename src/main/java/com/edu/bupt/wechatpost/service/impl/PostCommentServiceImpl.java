package com.edu.bupt.wechatpost.service.impl;

import com.edu.bupt.wechatpost.dao.PostCommentMapper;
import com.edu.bupt.wechatpost.model.Comment;
import com.edu.bupt.wechatpost.model.Post;
import com.edu.bupt.wechatpost.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostCommentServiceImpl implements PostCommentService {

    @Autowired
    private PostCommentMapper postCommentMapper;

    @Override
    public List<Post> findAllPosts(String openId, Integer page) {
        List<Post> posts = postCommentMapper.selectPostByOpenId(openId);
        int left = page * 9;
        int right = (page+1)*9 < posts.size() ? (page+1)*9 : posts.size();
        return posts.subList(left, right);
    }

    @Override
    public List<Post> searchPosts(String searchText, Integer page) {
        List<Post> posts = postCommentMapper.selectByKeySelective(searchText);
        int left = page * 9;
        int right = (page+1)*9 < posts.size() ? (page+1)*9 : posts.size();
        List<Post> myPosts = new ArrayList<>(posts.subList(left, right));
        return myPosts;
    }

    @Override
    public Integer publishPost(Post post) {
        return postCommentMapper.insertPostSelective(post);
    }

    @Override
    public Integer deletePost(Integer pId) {
        return postCommentMapper.deletePostByPostId(pId);
    }

    @Override
    public Integer favorite(Integer pId, Integer num) {
        return postCommentMapper.updateFavoriteNum(pId, num);
    }

    @Override
    public Integer addComment(Comment comment) {
        return postCommentMapper.insertComment(comment);
    }

    @Override
    public Integer deleteComment(Integer cId) {
        return postCommentMapper.deleteCommentByCommentId(cId);
    }
}
