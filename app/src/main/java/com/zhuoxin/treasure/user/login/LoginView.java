package com.zhuoxin.treasure.user.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by user on 2016/6/20.
 */
public interface LoginView extends MvpView {
    /*显示提示*/
    public void showMessage(String msg);
    /*显示进度*/
    public void showProgress();
    /*隐藏进度*/
    public void hidProgress();
    /*跳转到首页*/
    public void navigateToHome();
    /*清空输入框*/
    public void clearEditText();
}
