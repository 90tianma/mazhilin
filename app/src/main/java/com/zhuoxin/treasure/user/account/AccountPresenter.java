package com.zhuoxin.treasure.user.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.zhuoxin.treasure.net.NetClient;
import com.zhuoxin.treasure.user.UserPref;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2016/6/16.
 */
public class AccountPresenter extends MvpNullObjectBasePresenter<AccountView>{
    private Gson gson;
    private Call call;
    private String photoUrl;
    public AccountPresenter(){
        gson=new GsonBuilder().setLenient().create();
    }
/*上传头像*/
    public void uploadImage(File file){
        if (call!=null){
            call.cancel();
        }
        /*请求体*/
        RequestBody body=RequestBody.create(MediaType.parse("image/png"),file);
        MultipartBody.Part part=MultipartBody.Part.createFormData("image","icon.png",body);
        call= NetClient.getInstance().getUserApi().upload(part);
        call.enqueue(uploadCallBack);
        getView().showProgress();
    }
/*回调上传头像的结果*/
    Callback<ResponseBody> uploadCallBack=new Callback<ResponseBody>() {

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            getView().hidProgress();
            if (response!=null && response.isSuccessful()){
                try {
                    String body=response.body().string();
                    UploadResult result= gson.fromJson(body,UploadResult.class);
                    getView().showMessage(result.getMsg());
                    if (result==null){
                        getView().showMessage("nuknnwn error");
                        return;
                    }
                    if (result.getUrlCount()!=1){
                        getView().showMessage(result.getMsg());
                        return;
                    }
                        photoUrl= result.getSmallImgUrl();
                        /*更新服务器*/
                        Update update=new Update();
                        update.setTokentId(UserPref.getInstance().getTookenId());
                        String url=result.getImaUrl().substring(result.getImaUrl().lastIndexOf("/"),result.getImaUrl().length());
                        update.setPhotoUrl(url);
                        MediaType type=MediaType.parse("text/json");
                        RequestBody requestBody=RequestBody.create(type,gson.toJson(update));
                        call=NetClient.getInstance().getUserApi().update(requestBody);
                        call.enqueue(updateCallBack);

                } catch (IOException e) {
                    onFailure(call,e);
                }
            }
        }
        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            getView().hidProgress();
            getView().showMessage(t.getMessage());
        }
    };
/*回调更新结果*/
    Callback<ResponseBody> updateCallBack=new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if(response!=null && response.isSuccessful()){
                try {
                    String body=response.body().string();
                    UpdateResult result=gson.fromJson(body,UpdateResult.class);
                    if (result==null){
                        getView().showMessage("nuKnown error!");
                        return;
                    }
                    if (result.getCode()!=1){
                        getView().showMessage(result.getMsg());
                        return;
                    }
                     //上传成功
                        UserPref.getInstance().setPhoto(NetClient.BASE_URL+photoUrl);
                        getView().updateIcon(NetClient.BASE_URL+photoUrl);
                    getView().showMessage(result.getMsg());
                } catch (IOException e) {
                   onFailure(call,e);
                }
            }
        }
        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            getView().showMessage(t.getMessage());
        }
    };
}
