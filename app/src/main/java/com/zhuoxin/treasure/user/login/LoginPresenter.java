package com.zhuoxin.treasure.user.login;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.zhuoxin.treasure.net.NetClient;
import com.zhuoxin.treasure.user.User;
import com.zhuoxin.treasure.user.UserPref;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by user on 2016/6/20.
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {
    private LoginView loginView;
    private Gson gson;
    private Call<ResponseBody> loginCall;
    private final String Url="http://admin.syfeicuiedu.com/Handler/UserHandler.ashx?action=login";
    public LoginPresenter(){
        gson=new GsonBuilder().setLenient().create();//非严格模式
    }
    public void login(User user){
//        Log.i("tag",user.getName());
        /*启动异步任务*/
//        new LoginTask().execute(user);

        /* 利用 retrofit 调用Api接口实现登录*/
        RequestBody body=RequestBody.create(MediaType.parse("text/*"),gson.toJson(user));
//        Log.i("tag",body.toString());
        loginCall= NetClient.getInstance().getUserApi().login(body);
        loginCall.enqueue(cback);
        getView().showProgress();
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (loginCall!=null){
            loginCall.cancel();
        }
    }

    /*回调*/
    private final Callback<ResponseBody> cback=new Callback<ResponseBody>() {
        /*请求成功*/
        @Override
        public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
            getView().hidProgress();
            if (response.isSuccessful()){
                ResponseBody body=response.body();
                try {
                    String strBody=body.string();
//                    Log.i("tag",strBody);
                    LoginResult result=gson.fromJson(strBody,LoginResult.class);
                    if (result.getErrCode()==1){
                        //保存手机令牌
                        UserPref.getInstance().setTookenId(result.getTokenId());
                        //保存头像地址
                        UserPref.getInstance().setPhoto(NetClient.BASE_URL+result.getIconUrl());
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
        }
    };

}
