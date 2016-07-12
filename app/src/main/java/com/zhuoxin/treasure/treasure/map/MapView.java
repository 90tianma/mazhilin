package com.zhuoxin.treasure.treasure.map;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.zhuoxin.treasure.treasure.Treasure;

import java.util.List;

/**
 * Created by user on 2016/6/21.
 */
public interface MapView extends MvpView{
    void showMessage(String msg);
    void setData(List<Treasure> data);
}
