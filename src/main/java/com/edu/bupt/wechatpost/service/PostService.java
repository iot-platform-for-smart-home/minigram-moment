package com.edu.bupt.wechatpost.service;

import com.edu.bupt.wechatpost.model.Post;

import java.util.List;

public interface PostService {

    /**
     * 查询所有消息
     * @return
     */
    List<Post> findAll(Integer page);

    /**
     * 查找用户发布的消息
     * @param openId
     * @param page
     * @return
     */
    List<Post> findByOpenId(String openId, Integer page);

    /**
     * 模糊查找关键词相关的消息（昵称，时间，消息内容）
     * @param searchText
     * @param page
     * @return
     */
    List<Post> findPost(String searchText, Integer page);

    /**
     * 发布消息并保存到数据库
     * @param myPost
     */
    void addPost(Post myPost);

    /**
     * 删除消息
     * @param openId
     * @param postId
     */
    void deletePost(String openId, Integer postId);

    /**
     * 更新消息
     * @param myPost
     */
    void updatePost(Post myPost);

    void updateFavoriteNum(String nickName, int pId, int num);
}
