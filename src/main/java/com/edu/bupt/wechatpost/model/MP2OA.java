package com.edu.bupt.wechatpost.model;

public class MP2OA {
    private String unionid;
    private String mini_openid;
    private String oa_openid;

    public MP2OA(String unionid, String mini_openid, String oa_openid){
        this.unionid = unionid;
        this.mini_openid = mini_openid;
        this.oa_openid = oa_openid;
    }

    @Override
    public String toString() {
        return "MP2OA{" +
                "unionid='" + unionid + '\'' +
                ", mini_openid='" + mini_openid + '\'' +
                ", oa_openid='" + oa_openid + '\'' +
                '}';
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getMini_openid() {
        return mini_openid;
    }

    public void setMini_openid(String mini_openid) {
        this.mini_openid = mini_openid;
    }

    public String getOa_openid() {
        return oa_openid;
    }

    public void setOa_openid(String oa_openid) {
        this.oa_openid = oa_openid;
    }
}
