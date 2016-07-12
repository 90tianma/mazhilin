package com.zhuoxin.treasure.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhuoxin.treasure.treasure.TreasureApi;
import com.zhuoxin.treasure.user.UserApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 2016/6/13.
 */
public class NetClient {
    public static final String BASE_URL="http://admin.syfeicuiedu.com";
    private final OkHttpClient client;
    private final Retrofit retrofit;
    private static NetClient nClient;
    private TreasureApi treasureApi;

    private final Gson gson;
    private UserApi userApi;
    private NetClient(){
        client =new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();
        gson=new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://admin.syfeicuiedu.com")
                .client(client)
                /*添加gson转换器*/
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }
    public static NetClient getInstance(){
        if (nClient==null){
            nClient=new NetClient();
        }
        return nClient;
    }
    public TreasureApi getTreasureApi(){
        if (treasureApi==null){
            treasureApi=retrofit.create(TreasureApi.class);
        }
        return  treasureApi;
    }
    public OkHttpClient getClient() {
        return client;
    }
    public  UserApi getUserApi(){
        if (userApi==null){
            userApi=retrofit.create(UserApi.class);
        }
        return  userApi;
    }
}
