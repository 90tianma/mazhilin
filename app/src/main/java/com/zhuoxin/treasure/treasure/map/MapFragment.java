package com.zhuoxin.treasure.treasure.map;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.zhuoxin.treasure.R;
import com.zhuoxin.treasure.treasure.TreasureView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 2016/6/20.
 */
public class MapFragment extends Fragment {
    @Bind(R.id.layout_bottom) FrameLayout bottomLayout; // 宝藏信息卡片layout(包含展示的和录入的,出现在屏幕下方位置)
    @Bind(R.id.treasureView)    TreasureView treasureView;// 显示宝藏信息的卡片
    @Bind(R.id.hide_treasure)    RelativeLayout hideTreasure;// 信息录入卡片layout(埋藏宝藏时)
    @Bind(R.id.et_treasureTitle)    EditText etTreasureTitle;// 信息录入编辑框(埋藏宝藏时)
    @Bind(R.id.centerLayout) RelativeLayout conterLayout; // 埋藏宝藏时的layout
    @Bind(R.id.btn_HideHere)    Button btnHideHere; // 埋藏宝藏时的按钮
    private MapView mapView;/*地图视图*/
    private BaiduMap baiduMap;/*百度地图*/
    @Bind(R.id.map_frame)
    FrameLayout mapFragment;
    @Bind(R.id.map_scale_in)
    ImageView scaleIn;
    @Bind(R.id.map_scale_out) ImageView scaleOut;
    @Bind(R.id.map_location)
    TextView mapLocation;
    @Bind(R.id.map_statelite) TextView statelite;
    @Bind(R.id.map_guide)    TextView mapGuide;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map,container,false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initLoadMap();

    }
    /*初始化定位*/
      /*定位*/
    private LocationClient locationClient;/*定位API*/
    private static LatLng myLocation;/*当前位置*/
    private boolean isFrist=true;

    private void initLocationClient(){
        //激活我的定位
        baiduMap.setMyLocationEnabled(true);
        locationClient=new LocationClient(getActivity().getApplicationContext());
        locationClient.registerLocationListener(locationListener);/*注册定位监听*/
        /*定位设置*/
        LocationClientOption option=new LocationClientOption();
        option.setOpenGps(true);//打开GPS
        option.setScanSpan(60000);/*扫描周期*/
        option.setCoorType("bd09ll");/*百度坐标类型*/
        locationClient.setLocOption(option);
        //开始定位
        locationClient.start();
        locationClient.requestLocation();
    }
    /*定位监听*/
    private BDLocationListener locationListener=new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation==null){
                locationClient.requestLocation();//定位不成功，从新定位
                return;
            }
            /*当前的经纬度*/
            double lon=bdLocation.getLongitude();//经度
            double lat=bdLocation.getLatitude();//纬度
            String city=bdLocation.getCity();
            myLocation=new LatLng(lat,lon);/*获取我的位置*/
            Log.i("tag",  bdLocation.getLocType()+"---------");
            MyLocationData myLocationData=new MyLocationData.Builder()
                    .accuracy(100f)//精度
                    .longitude(lon)//经度
                    .latitude(lat)//纬度
                    .build();
            /*设置我的位置*/
            baiduMap.setMyLocationData(myLocationData);
            if (isFrist){
                moveToMyLocation();
                isFrist=false;
            }
        }
    };
    private void initLoadMap(){
        /*地图状态的配置*/
        MapStatus status=new MapStatus.Builder()
                .zoom(10)/*缩放*/
                .overlook(-20)/*俯瞰角度*/
                .build();
        /*地图的设置*/
        BaiduMapOptions options=new BaiduMapOptions()
                .mapStatus(status)
                .zoomControlsEnabled(false);/*让百度地图自带的缩放功能无效*/
        /*地图视图*/
        mapView=new MapView(getActivity(),options);
        mapFragment.addView(mapView,0);/*将地图添加到最底层*/
        baiduMap=mapView.getMap();
        initLocationClient();/*已进入应用就自动定位到当前城市*/
    }

    /*定位： 将地图移动到我的位置*/
    @OnClick(R.id.map_location)
    public void moveToMyLocation(){
        MapStatus.Builder builder=new MapStatus.Builder();
        builder.target(myLocation);
        builder.rotate(0);//摆正地图
        builder.zoom(19);//缩放地图
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }
    @OnClick({R.id.map_scale_in,R.id.map_scale_out})
    public void scaleMap(View view){/*缩放地图*/
        MapStatusUpdate statusUpdate=null;
        switch (view.getId()){
            case R.id.map_scale_out:  /*缩小地图*/
                statusUpdate= MapStatusUpdateFactory.zoomOut();
                baiduMap.setMapStatus(statusUpdate);
                break;
            case R.id.map_scale_in:/*放大地图*/
                statusUpdate=MapStatusUpdateFactory.zoomIn();
                baiduMap.setMapStatus(statusUpdate);
                break;
        }
    }
    @OnClick(R.id.map_statelite)
    public void switchMapType(){/*更改地图类型 卫星-普通*/
        int type=baiduMap.getMapType();
        type= type==BaiduMap.MAP_TYPE_NORMAL?BaiduMap.MAP_TYPE_SATELLITE:BaiduMap.MAP_TYPE_NORMAL;
        String txt= statelite.getText().toString();
        txt= type==BaiduMap.MAP_TYPE_NORMAL?"卫星图":"平面图";
        statelite.setText(txt);
        baiduMap.setMapType(type);
    }
    @OnClick(R.id.map_guide)
    public void stateGuide(){/*地图指南的可见性*/
        boolean isCompass=baiduMap.getUiSettings().isCompassEnabled();
        baiduMap.getUiSettings().setCompassEnabled(!isCompass);
    }
    private static final int UI_MODE_NORMAL = 0; // 普通(Map查看模块)
    private static final int UI_MODE_SELECT = 1;
    private static final int UI_MODE_HIDE = 2;

    private int uiMode = UI_MODE_NORMAL;

    private void changeUiMode(int uiMode) {
        if (this.uiMode == uiMode) return;
        this.uiMode = uiMode;
        switch (uiMode) {
            case UI_MODE_NORMAL:
                bottomLayout.setVisibility(View.GONE);// 隐藏下方的宝藏信息layout
                conterLayout.setVisibility(View.GONE);// 隐藏中间位置藏宝layout
                break;
            case UI_MODE_SELECT:
                bottomLayout.setVisibility(View.VISIBLE);// 显示下方的宝藏信息layout
                treasureView.setVisibility(View.VISIBLE);// 显示宝藏信息卡片
                conterLayout.setVisibility(View.GONE); // 隐藏中间位置藏宝layout
                hideTreasure.setVisibility(View.GONE); // 隐藏宝藏录入信息卡片
                YoYo.with(Techniques.BounceInUp).duration(500).playOn(bottomLayout);
                break;
            case UI_MODE_HIDE:
                conterLayout.setVisibility(View.VISIBLE);// 显示中间位置藏宝layout
                bottomLayout.setVisibility(View.GONE);// 隐藏下方的宝藏信息layout
                // 按下藏宝时
                btnHideHere.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        bottomLayout.setVisibility(View.VISIBLE);// 显示下方的宝藏信息layout
                        hideTreasure.setVisibility(View.VISIBLE);// 显示宝藏录入信息卡片
                        treasureView.setVisibility(View.GONE);// 隐藏宝藏信息卡片
                        YoYo.with(Techniques.FlipInX).duration(500).playOn(bottomLayout);
                    }
                });
                break;
        }
    }
}
