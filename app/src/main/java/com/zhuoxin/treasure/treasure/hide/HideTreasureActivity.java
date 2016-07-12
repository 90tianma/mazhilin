package com.zhuoxin.treasure.treasure.hide;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.mapapi.model.LatLng;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zhuoxin.treasure.R;
import com.zhuoxin.treasure.utils.ActivityUtils;

import butterknife.ButterKnife;

public class HideTreasureActivity extends MvpActivity<HideTreasuerView,HideTreasurePresenter> implements HideTreasuerView {
    private ActivityUtils activityUtils;
    /*Intent传递数据的键的名称*/
    private final static String EXTRA_KEY_TITLE="key_title";
    private final static String EXTRA_KEY_LOCATION="key_location";
    private final static String EXTRA_KEY_LAT_LON="key_lat_lon";
    private final static String EXTRA_KEY_HEIGTH="key_heigth";
    public static void open(Context context, String title, String location, String lat_len, String height){
        Intent intent=new Intent(context,HideTreasureActivity.class);
        intent.putExtra(EXTRA_KEY_TITLE,title);
        intent.putExtra(EXTRA_KEY_LOCATION,location);
        intent.putExtra(EXTRA_KEY_LAT_LON,lat_len);
        intent.putExtra(EXTRA_KEY_HEIGTH,height);
        context.startActivity(intent);
    }
    @NonNull
    @Override
    public HideTreasurePresenter createPresenter() {
        return new HideTreasurePresenter();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_treasure);
        activityUtils=new ActivityUtils(this);
        ButterKnife.bind(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hide_treasure,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_send:
                Intent intent=getIntent();
                Intent preIntent = getIntent();
                LatLng latLng = preIntent.getParcelableExtra(EXTRA_KEY_LAT_LON);
                /*设置宝藏的信息*/














/*
                HideTreasure hideTreasure = new HideTreasure();
                hideTreasure.setTreasuerHeigth(preIntent.getDoubleExtra(EXTRA_KEY_HEIGTH, 0));
                hideTreasure.setLatitude(latLng.latitude);
                hideTreasure.setLongitude(latLng.longitude);
                hideTreasure.setLocation(preIntent.getStringExtra(EXTRA_KEY_LOCATION));
                hideTreasure.setTokenId(UserPrefs.getInstance().getTokenid());
                hideTreasure.setTitle(preIntent.getStringExtra(EXTRA_KEY_TITLE));
                hideTreasure.setDescription(etDescription.getText().toString());
                getPresenter().hide(hideTreasure);*/
                break;
        }
        return true;
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hidProgress() {

    }

    @Override
    public void navigateToHome() {

    }

    @Override
    public void clearEditText() {

    }
}
