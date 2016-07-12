package com.zhuoxin.treasure.user.account;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2016/6/16.
 */
public class UploadResult {

    @SerializedName("errcode")
    private String msg;

    @SerializedName("urlcount")
    private int urlCount;

    @SerializedName("imgUrl")
    private String imaUrl;

    @SerializedName("smallImgUrl")
    private String smallImgUrl;

    public String getMsg() {
        return msg;
    }

    public int getUrlCount() {
        return urlCount;
    }

    public String getImaUrl() {
        return imaUrl;
    }

    public String getSmallImgUrl() {
        return smallImgUrl;
    }
}
