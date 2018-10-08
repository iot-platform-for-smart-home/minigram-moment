package com.edu.bupt.wechatpost.model;

public class Comment {
    private Integer cId;

    private Integer pId;

    private String cAvatar;

    private String cContent;

    private String openId;

    public Comment(Integer cId, Integer pId, String cAvatar, String cContent, String openId) {
        this.cId = cId;
        this.pId = pId;
        this.cAvatar = cAvatar;
        this.cContent = cContent;
        this.openId = openId;
    }

    public Comment() {
        super();
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getcAvatar() {
        return cAvatar;
    }

    public void setcAvatar(String cAvatar) {
        this.cAvatar = cAvatar == null ? null : cAvatar.trim();
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent == null ? null : cContent.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }
}