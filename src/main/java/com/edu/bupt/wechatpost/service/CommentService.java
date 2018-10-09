package com.edu.bupt.wechatpost.service;

import com.edu.bupt.wechatpost.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findByPostId(Integer postId, Integer page);;

    void addComment(Comment myComment);

    Integer deleteComment(Integer postId, Integer commentId);

//    void updateComment(Comment myComment);
}
