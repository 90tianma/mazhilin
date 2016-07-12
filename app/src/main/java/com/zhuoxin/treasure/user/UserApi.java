package com.zhuoxin.treasure.user;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

/**
 *登录Api接口
 */
public interface UserApi {
    /*登录*/
    @POST("Handler/UserHandler.ashx?action=login")
   Call<ResponseBody> login(@Body RequestBody body);
    /*注册*/
    @POST("/Handler/UserHandler.ashx?action=register")
    Call<ResponseBody> register(@Body RequestBody body);
    /*多部分上传*/
    @Multipart
    @POST("/Handler/UserLoadPicHandler1.ashx")
   Call<ResponseBody> upload(@Body MultipartBody.Part part);
    /*更新头像*/
    @POST("/Handler/UserHandler.ashx?action=update")
    Call<ResponseBody> update(@Body RequestBody body);

}