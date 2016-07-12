package com.zhuoxin.treasure.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2016/6/13.
 */
public class User {
    @SerializedName("UserName")
    private String name;
    @SerializedName("Password")
    private String psw;

    public User(String name,String psw){
        this.name=name;
        this.psw=psw;
    }
    public String getName() {
        return name;
    }
    public String getPsw() {
        return psw;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
