package com.zhuoxin.treasure.treasure;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
/** 获取宝藏列表数据时，的实体类**/
public class Treasure {

    @SerializedName("tdid")
    private int id;

    @SerializedName("tdtname")
    private String title;

    /**纬度*/
    @SerializedName("htyline")
    private double latitude;

    /**经度*/
    @SerializedName("htxline")
    private double longitude;

    /**海拔*/
    @SerializedName("htheight")
    private double altitude;

    @SerializedName("htpoi")
    private String location;

    /**寻找难度*/
    @SerializedName("htlevels")
    private int level;

    /**尺寸*/
    @SerializedName("htsize")
    private int size;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public String getLocation() {
        return location;
    }

    public int getLevel() {
        return level;
    }

    public int getSize() {
        return size;
    }

    public double distanceToMyLocation() {
        return 0;
    }
}
