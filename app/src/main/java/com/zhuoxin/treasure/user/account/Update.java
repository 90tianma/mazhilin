package com.zhuoxin.treasure.user.account;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2016/6/16.
 */
public class Update {
    /*
    * "Tokenid":3,"
"Password":"654321",
"HeadPic": */
    @SerializedName("Tokenid")
    private int tokentId;
    @SerializedName("HeadPic")
    private String photoUrl;

    public void setTokentId(int tokentId) {
        this.tokentId = tokentId;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
