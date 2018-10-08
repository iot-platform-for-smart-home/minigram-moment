package com.edu.bupt.wechatpost.dao;

import com.edu.bupt.wechatpost.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    int deleteByPrimaryKey(@Param("pId") Integer pId, @Param("openId") String openId);

    int insert(Post record);

    int insertSelective(Post record);

    List<Post> selectByPrimaryKeySelective(String searchText);

    List<Post> findAll(Integer page);

    List<Post> findAllByOpenId(@Param("openId") String openId);

    int updateByPrimaryKeySelective(Post record);

    int updateByPrimaryKey(Post record);

    void updateFavoriteNum(String nickName, int pId, int num);
}