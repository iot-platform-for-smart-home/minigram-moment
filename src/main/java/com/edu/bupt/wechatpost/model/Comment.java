package com.edu.bupt.wechatpost.model;

public class Comment {
    private Integer cId;

    private Integer pId;

    private String nickName;

    private String cContent;

    public Comment(Integer cId, Integer pId, String nickName, String cContent) {
        this.cId = cId;
        this.pId = pId;
        this.nickName = nickName;
        this.cContent = cContent;
    }

    public Comment(Integer pId, String nickName, String cContent) {
        this.pId = pId;
        this.nickName = nickName;
        this.cContent = cContent;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent == null ? null : cContent.trim();
    }
}