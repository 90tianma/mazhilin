package com.zhuoxin.treasure.treasure.hide;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by user on 2016/6/23.
 */
public interface HideTreasuerView extends MvpView {
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
