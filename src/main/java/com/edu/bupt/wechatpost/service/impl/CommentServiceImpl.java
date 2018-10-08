package com.edu.bupt.wechatpost.service.impl;

import com.edu.bupt.wechatpost.dao.CommentMapper;
import com.edu.bupt.wechatpost.model.Comment;
import com.edu.bupt.wechatpost.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findByPostId(String openId, Integer postId, Integer page) {
        List<Comment> comments = new ArrayList<>(commentMapper.findAllByPostId(openId, postId));
        int left = page * 9;
        int right = (page+1)*9 > comments.size() ? comments.size() : (page+1)*9;
        List<Comment> myComments = new ArrayList<>(comments.subList(left, right));
        return myComments;
    }

//    @Override
//    public List<Comment> findByOpenId(String openId, Integer page) {
//        List<Comment> comments = new ArrayList<>(commentMapper.findAllByOpenId(openId));
//        int left = page * 9;
//        int right = (page+1)*9 > comments.size() ? comments.size() : (page+1)*9;
//        List<Comment> myComments = new ArrayList<>(comments.subList(left, right));
//        return myComments;
//    }

    @Override
    public void addComment(Comment myComment) {
        commentMapper.insert(myComment);
    }

    @Override
    public void deleteComment(String openId, Integer postId, Integer commentId) {
        commentMapper.deleteByPrimaryKey(openId, commentId, postId);
    }

    @Override
    public void updateComment(Comment myComment) {
        commentMapper.updateByPrimaryKeySelective(myComment);
    }
}
