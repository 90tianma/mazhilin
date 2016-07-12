package com.zhuoxin.treasure.treasure.list;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuoxin.treasure.R;
import com.zhuoxin.treasure.treasure.TreasureRepo;

/**
 * RecyclerView
 *
 * 1. 用来代替ListView,GridView..的新视图控件
 * 2. 5.0后推出的, V7
 * 3. HolderView封装
 * 4. RecyclerView实现效果,设置你想使用的布局方式    ^
 * 5. 动画                                       ^
 * 6. 对指定位置的刷新
 */
public class TreasureListFragment extends Fragment {
    private  RecyclerView recyclerView;
    private TreasuerAdapter adapter;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = new RecyclerView(container.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setBackgroundResource(R.drawable.scale_bg);//设置背景
        // TreasureRepo.getInstance().getTreasures();
        return recyclerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*创建适配器*/
        adapter=new TreasuerAdapter();
        /*recyclerView控件添加适配器*/
        recyclerView.setAdapter(adapter);
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               /*添加数据源*/
               adapter.addItems(TreasureRepo.getInstance().getTreasures());
           }
       },50);
    }
}
