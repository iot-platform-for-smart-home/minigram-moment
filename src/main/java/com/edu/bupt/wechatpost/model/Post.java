package com.edu.bupt.wechatpost.model;

import java.util.Date;

public class Post {
    private Integer pId;

    private String openId;

    private String pAvatar;

    private String nickName;

    private Date timeStamp;

    private String pContent;

    private String image;

    private String location;

    private Integer favoriteNum;

    public Post(Integer pId, String openId, String pAvatar, String nickName, Date timeStamp, String pContent, String image, String location, Integer favoriteNum) {
        this.pId = pId;
        this.openId = openId;
        this.pAvatar = pAvatar;
        this.nickName = nickName;
        this.timeStamp = timeStamp;
        this.pContent = pContent;
        this.image = image;
        this.location = location;
        this.favoriteNum = favoriteNum;
    }

    public Post(String openId, String pAvatar, String nickName, Date timeStamp, String pContent, String image, String location, Integer favoriteNum) {
        this.openId = openId;
        this.pAvatar = pAvatar;
        this.nickName = nickName;
        this.timeStamp = timeStamp;
        this.pContent = pContent;
        this.image = image;
        this.location = location;
        this.favoriteNum = favoriteNum;
    }

    public Post() {
        super();
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getpAvatar() {
        return pAvatar;
    }

    public void setpAvatar(String pAvatar) {
        this.pAvatar = pAvatar == null ? null : pAvatar.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getpContent() {
        return pContent;
    }

    public void setpContent(String pContent) {
        this.pContent = pContent == null ? null : pContent.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Integer getFavoritenum() {
        return favoriteNum;
    }

    public void setFavoritenum(Integer favoriteNum) {
        this.favoriteNum = favoriteNum;
    }
}