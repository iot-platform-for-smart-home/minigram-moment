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
    public List<Comment> findByPostId(Integer postId, Integer page) {
        List<Comment> comments = commentMapper.findAllByPostId(postId);
//        if (comments.size() == 0 ) return comments;
        int left = page * 9;
        int right = (page+1)*9 > comments.size() ? comments.size() : (page+1)*9;
        return comments.subList(left, right);
    }



    @Override
    public void addComment(Comment myComment) {
        commentMapper.insert(myComment);
    }

    @Override
    public Integer deleteComment(Integer postId, Integer commentId) {
        return commentMapper.deleteByPrimaryKey(commentId, postId);
    }

//    @Override
//    public void updateComment(Comment myComment) {
//        commentMapper.updateByPrimaryKeySelective(myComment);
//    }
}
