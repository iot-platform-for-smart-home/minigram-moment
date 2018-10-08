package com.edu.bupt.wechatpost.dao;

import com.edu.bupt.wechatpost.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    int deleteByPrimaryKey(@Param("openId") String openId, @Param("pId") Integer pId, @Param("cId") Integer cId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(@Param("cId") Integer cId, @Param("pId") Integer pId);

    List<Comment> findAllByPostId(@Param("openId")String openId, @Param("pId") Integer pId);

    List<Comment> findAllByOpenId(@Param("openId") String openId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
}