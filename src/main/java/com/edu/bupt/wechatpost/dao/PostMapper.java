package com.edu.bupt.wechatpost.dao;

import com.edu.bupt.wechatpost.model.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostMapper {
    Integer deleteByPrimaryKey(@Param("pId") Integer pId, @Param("openId") String openId);

    Integer insert(Post record);

    Integer insertSelective(Post record);

    List<Post> selectByKeySelective(@Param("searchText") String searchText);

    Post selectByPrimaryKey(@Param("openId") String openId, @Param("pId") Integer pId);

    List<Post> findAll(@Param(value="openId") String openId);


    Integer updateByPrimaryKeySelective(Post record);

    Integer updateByPrimaryKey(Post record);

    Integer updateFavoriteNum(@Param("nickName") String nickName, @Param("pId") int pId, @Param("num") int num);
}