package com.edu.bupt.wechatpost.dao;

import com.edu.bupt.wechatpost.model.MP2OA;
import org.apache.ibatis.annotations.Param;

public interface MP2OAMapper {
    String selectOAByMP(@Param("mini_openid")String mini_openid);
    MP2OA selectByUnionid(@Param("unionid")String unionid);
    void insertSelective(MP2OA user);
    void updateMiniOpenid(@Param("unionid")String unionid, @Param("mini_openid") String mini_openid);
    void deleteByUnionid(@Param("unionid")String unionid);
}
