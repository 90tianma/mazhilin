package com.zhuoxin.treasure.user.register;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2016/6/20.
 */
public class RegisterResult {
    @SerializedName("errcode")
    private int errCode;
    @SerializedName("errmsg")
    private String errMsg;
    @SerializedName("tokenid")
    private int tokenId;
    public RegisterResult(int errCode,String errMsg){
        this.errCode=errCode;
        this.errMsg=errMsg;
    }

    public int getTokenId() {
        return tokenId;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public int getErrCode() {
        return errCode;
    }
}
