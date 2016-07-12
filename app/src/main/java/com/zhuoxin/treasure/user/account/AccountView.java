package com.zhuoxin.treasure.user.account;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by user on 2016/6/16.
 */
public interface AccountView extends MvpView{
   /*显示进度*/
    void showProgress();
    /*隐藏进度*/
    void hidProgress();
    /*显示提示*/
    void showMessage(String msg);
    /*更新头像*/
    void updateIcon(String url);
}
