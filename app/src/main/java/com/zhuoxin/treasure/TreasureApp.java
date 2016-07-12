package com.zhuoxin.treasure;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhuoxin.treasure.user.UserPref;

/**
 * Created by user on 2016/6/20.
 */
public class TreasureApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // UserPrefs初始化
        UserPref.init(this);
        // ImageLoader初始化配置
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        //百度地图初始化
        SDKInitializer.initialize(getApplicationContext());
    }
}
