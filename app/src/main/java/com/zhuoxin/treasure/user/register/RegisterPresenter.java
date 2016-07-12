package com.zhuoxin.treasure.user.register;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.zhuoxin.treasure.net.NetClient;
import com.zhuoxin.treasure.user.User;
import com.zhuoxin.treasure.user.UserPref;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by user on 2016/6/20.
 */
public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView> {
    private String Url="http://admin.syfeicuiedu.com/Handler/UserHandler.ashx?action=register";
    private Gson gson;
    private Handler handler;
    private retrofit2.Call registerCall;
    /*构造方法*/
    public RegisterPresenter(){
        gson=new GsonBuilder().setLenient().create();
        handler=new Handler(Looper.getMainLooper());
    }
    /*注册*/
    public void register(User user){
        /*AsyncTask+OkHttp同步实现*/
//        new RegisterTask().execute();

 /*  第二论修改     *//*Handler+OkHttp异步实现*//*
        //构建一个请求体
        RequestBody requestBody= RequestBody.create(MediaType.parse("text.*"),gson.toJson(user));
        //构建一个请求
        Request request=new Request.Builder().url(Url).post(requestBody).build();
        //构建一个呼叫
        Call call= NetClient.getInstance().getClient().newCall(request);
        //异步执行呼叫
        call.enqueue(callback);*/

        /*第三轮  使用Retrofit实现注册*/
        if (registerCall!=null) registerCall.cancel();
        RequestBody body=RequestBody.create(MediaType.parse("text/*"),gson.toJson(user));
        registerCall= NetClient.getInstance().getUserApi().register(body);
        registerCall.enqueue(cBack);
        getView().showProgress();

    }
    /*retrofit请求结果的回调*/
    private retrofit2.Callback<ResponseBody> cBack=new retrofit2.Callback<ResponseBody>() {
        /*请求成功*/
        @Override
        public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
            getView().hidProgress();
            if (response!=null && response.isSuccessful()){
                try {
                    String body=response.body().string();
                    RegisterResult result=gson.fromJson(body,RegisterResult.class);
                    if (result.getErrCode()==1){
                        UserPref.getInstance().setTookenId(result.getTokenId());
                        getView().navigateToHome();
                        return;
                    }
                    getView().showMessage(result.getErrMsg());
                } catch (IOException e) {
                    onFailure(call,e);
                }
            }
        }
        /*请求失败*/
        @Override
        public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
            getView().hidProgress();
            getView().showMessage(t.getMessage());
            getView().clearEditText();

        }
    };

}
