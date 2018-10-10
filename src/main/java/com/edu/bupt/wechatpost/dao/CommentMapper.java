package com.edu.bupt.wechatpost.dao;

import com.edu.bupt.wechatpost.model.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(@Param("pId") Integer pId, @Param("cId") Integer cId);

    int insert(Comment comment);

    int insertSelective(Comment comment);

    Comment selectByPrimaryKey(@Param("cId") Integer cId, @Param("pId") Integer pId);

    List<Comment> findAllByPostId(@Param("pId") Integer pId);
}