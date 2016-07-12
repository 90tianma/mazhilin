package zhuoxin.com.myapp.user.login;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import zhuoxin.com.myapp.net.NetClient;
import zhuoxin.com.myapp.user.User;
import zhuoxin.com.myapp.user.UserPref;

/**
 * Created by user on 2016/6/13.
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<zhuoxin.com.myapp.user.login.LoginView>/* MvpBasePresenter<LoginView>*/ {
    private zhuoxin.com.myapp.user.login.LoginView loginView;
    private Gson gson;
    private retrofit2.Call loginCall;
    private final String Url="http://admin.syfeicuiedu.com/Handler/UserHandler.ashx?action=login";
    public LoginPresenter(){
      gson=new GsonBuilder().setLenient().create();//非严格模式
    }
    public void login(User user){
        Log.i("tag",user.getName());
        /*启动异步任务*/
//        new LoginTask().execute(user);

        /* 利用 retrofit 调用Api接口实现登录*/
        RequestBody body=RequestBody.create(MediaType.parse("text.*"),gson.toJson(user));
        Log.i("tag",body.toString());
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
        public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
            getView().hidProgress();
            if (response.isSuccessful()){
                ResponseBody body=response.body();
                try {
                    String strBody=body.string();
                    Log.i("tag",strBody);
                    zhuoxin.com.myapp.user.login.LoginResult result=gson.fromJson(strBody, zhuoxin.com.myapp.user.login.LoginResult.class);
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


    /**OkHttp同步执行+AsyncTask实现登录*/
   private final class LoginTask extends AsyncTask<User,String, zhuoxin.com.myapp.user.login.LoginResult>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getView().showProgress();
        }

        @Override
        protected zhuoxin.com.myapp.user.login.LoginResult doInBackground(User... params) {
          User user=params[0];
            //构建请求体
            RequestBody requestBody=RequestBody.create(MediaType.parse("text.*"),gson.toJson(user));
           //构建请求
            Request request=new Request.Builder().url(Url).post(requestBody).build();
          //构建一次呼叫请求
            Call call= NetClient.getInstance().getClient().newCall(request);
            try {
                //执行呼叫请求 得到一次响应
                Response response=call.execute();
                //对响应作出判断
                if (response.isSuccessful()){
                    //取出响应体
                   ResponseBody responseBody= response.body();
                    String body=responseBody.string();
                    //将字符串转换成LoginResult实体类
                    zhuoxin.com.myapp.user.login.LoginResult result=gson.fromJson(body, zhuoxin.com.myapp.user.login.LoginResult.class);
                    return result;
                }

            } catch (IOException e) {
                e.printStackTrace();
                publishProgress(e.getMessage());/*发送进度*/
            }

            /*//第一轮 模拟登陆
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {

                e.printStackTrace();
        }*/

           /*//第二轮
            OkHttpClient okHttpClient=new OkHttpClient();
            //设置请求体
            Request request= new Request.Builder().url("www.baidu.com").build();
            try {
                //执行一次请求，获得一个响应
               Response response= okHttpClient.newCall(request).execute();
                if (response.isSuccessful()){
                   ResponseBody body= response.body();//响应体
                    String result=body.string();
//                    System.out.print("获得数据："+result);
                }
            } catch (IOException e) {
                publishProgress("登录异常");//出现异常，处罚onProgressUpdate
                e.printStackTrace();
            }
*/
            return null;
        }
        /*调用 publishProgress方法执行 更新进度*/
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            getView().hidProgress();
            getView().showMessage(values[0]);
        }
        /*对结果进行处理*/
        @Override
        protected void onPostExecute(zhuoxin.com.myapp.user.login.LoginResult result) {
            super.onPostExecute(result);
            getView().hidProgress();
            //根据响应做出判断
            if (result!=null) switch (result.getErrCode()) {
                case 1:
                    getView().showMessage("登录成功");
                    getView().navigateToHome();//登录成功，跳转到首页
                    break;
                default:
                    getView().showMessage(result.getErrMsg());//登录失败，显示响应的错误信息“”
                    break;
            }
        }


    }

}