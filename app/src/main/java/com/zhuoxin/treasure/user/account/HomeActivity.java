package com.zhuoxin.treasure.user.account;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhuoxin.treasure.R;
import com.zhuoxin.treasure.treasure.list.TreasureListFragment;
import com.zhuoxin.treasure.user.Constant;
import com.zhuoxin.treasure.user.UserPref;
import com.zhuoxin.treasure.utils.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    @Bind(R.id.home_toolbar)
    Toolbar toolbar;
    @Bind(R.id.home_nav)
    NavigationView navigationView;
    @Bind(R.id.home_drawLayout)
    DrawerLayout drawerLayout;
    private ActivityUtils activityUtils;
    private ImageView icon;
    private FragmentManager fragmentManager;
    private TreasureListFragment listFragment;
    private final BroadcastReceiver receiver=new BroadcastReceiver()  {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //注册一个本地广播
        IntentFilter filter=new IntentFilter(Constant.ACTION_ENTER_HOME);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,filter);
        activityUtils=new ActivityUtils(this);
        fragmentManager = getSupportFragmentManager();/*初始化FragmentManager*/
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("寻宝");

        /*为导航设置监听事件*/
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close
        );
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        //找到导航的头部
        icon= (ImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_icon);
        icon.setOnClickListener(listener);
    }
    @Override
    protected void onStart() {
        super.onStart();
        String url= UserPref.getInstance().getPhotoUrl();
        ImageLoader.getInstance().displayImage(url,icon);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.action_toggle);
        if (listFragment!=null && listFragment.isAdded() ){
            item.setIcon(R.drawable.ic_map_back);
        }else {
            item.setIcon(R.drawable.ic_view_list);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
           case  R.id.action_toggle:
               showListFragment();
               invalidateOptionsMenu();
               break;
        }
        return super.onOptionsItemSelected(item);
    }

private void showListFragment(){

    if (listFragment!=null && listFragment.isAdded()){
        activityUtils.showToast("显示地图");
        fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction().remove(listFragment).commit();
    }else{
        activityUtils.showToast("显示列表");
        listFragment=new TreasureListFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.list_fragment,listFragment)
                .addToBackStack(null)
                .commit();
    }
}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver!=null){//解除注册广播
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        }
    }
    /*导航的监听事件*/
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_hide:
                activityUtils.showToast(R.string.hide);
                break;
            case R.id.menu_item_myList:
                activityUtils.showToast(R.string.mylist);
                break;
            case R.id.menu_item_help:
                activityUtils.showToast(R.string.help);
                break;
            case R.id.menu_item_exit:
                activityUtils.showToast(R.string.exit);
                break;
        }
        return false;
    }
    /*头像点击事件*/
    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            activityUtils.startActivity(AccountActivity.class);
        }
    };

}
