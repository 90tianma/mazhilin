package com.zhuoxin.treasure.user.account;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2016/6/16.
 */
public class UpdateResult {

    @SerializedName("errcode")
    private int code;
    @SerializedName("errmsg")
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
