package com.zhuoxin.treasure.treasure;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhuoxin.treasure.R;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by user on 2016/6/22.
 */
public class TreasureView extends RelativeLayout implements View.OnClickListener {
    @Bind(R.id.tv_treasureTitle)
    TextView title;
    @Bind(R.id.tv_treasureLocation) TextView location;
    @Bind(R.id.tv_distance) TextView tvDistance;
    private Treasure treasure;
    public TreasureView(Context context) {
        this(context,null);
    }

    public TreasureView(Context context, AttributeSet attrs) {
        this(context, attrs,0 );
    }

    public TreasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /*初始化*/
    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_treasure,this,true);
        ButterKnife.bind(this);/*绑定视图*/
        setOnClickListener(this);/*设置监听*/
    }

    @Override
    public void onClick(View view) {

    }
    /*将传入的宝藏自动的显示到控件上*/
    public void bindTreasure(Treasure treasure){
        this.treasure=treasure;
        title.setText(treasure.getTitle());
        location.setText(treasure.getLocation());
        double distance=treasure.distanceToMyLocation();//宝藏和当前位置的距离
        DecimalFormat format=new DecimalFormat("#0.0");//精确到十分位
        String strDistance=format.format((distance/1000)+"Km");
        tvDistance.setText(strDistance);
    }
}
