package com.edu.bupt.wechatpost.service;

import com.edu.bupt.wechatpost.model.Comment;
import com.edu.bupt.wechatpost.model.Post;

import java.util.List;

public interface PostCommentService {
    List<Post> findAllPosts(String openId, Integer page);
    List<Post> searchPosts(String searchText, Integer page);
    Integer publishPost(Post post);
    Integer deletePost(Integer pId);
    Integer favorite(Integer pId, Integer num);
    Integer addComment(Comment comment);
    Integer deleteComment(Integer cId);
}
