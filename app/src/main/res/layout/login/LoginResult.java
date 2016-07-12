package zhuoxin.com.myapp.user.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2016/6/13.
 * 登录请求结果类
 */
public class LoginResult {
    @SerializedName("headpic")
    private String iconUrl;

    @SerializedName("tokenid")
    private int tokenId;

    @SerializedName("errcode")
    private int errCode;

    @SerializedName("errmsg")
    private String errMsg;

    public String getIconUrl() {
        return iconUrl;
    }

    public int getTokenId() {
        return tokenId;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
