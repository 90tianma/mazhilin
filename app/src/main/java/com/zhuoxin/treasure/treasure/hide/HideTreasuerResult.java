package com.zhuoxin.treasure.treasure.hide;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2016/6/23.
 */
public class HideTreasuerResult {
    @SerializedName("errcode")
    private int code;/*响应代码*/
    @SerializedName("errmsg")
    private  String msg;/*响应消息*/
    @SerializedName("ID")
    private int id;//响应ID
    @SerializedName("POI")
    private String position;//宝藏位置
    @SerializedName("Size")
    private int size;//宝藏大小
    @SerializedName("Levels")
    private int levels;//宝藏难度
    @SerializedName("ShortContent")
    private String desc;//宝藏描述
    @SerializedName("Xline")
    private double lon;//宝藏经度
    @SerializedName("Yline")
    private double lat;//宝藏纬度
    @SerializedName("Height")
    private int height;//宝藏海拔
    @SerializedName("TreasureID")
    private int treasuerID;//宝藏ID
    @SerializedName("TreasureName")
    private String title;//宝藏名称
    @SerializedName("Bimage1")
    private  final  String Bimage1 ="";
    @SerializedName("Bimage2")
    private  final  String Bimage2 ="";
    @SerializedName("Bimage3")
    private  final  String Bimage3 ="";
    @SerializedName("Bimage4")
    private  final  String Bimage4 ="";
    @SerializedName("Bimage5")
    private  final  String Bimage5 ="";
    @SerializedName("Bimage6")
    private  final  String Bimage6 ="";
    @SerializedName("Bimage7")
    private  final  String Bimage7 ="";
    @SerializedName("Bimage8")
    private  final  String Bimage8 ="";
    @SerializedName("Bimage9")
    private  final  String Bimage9 ="";
/*缩略图*/
    @SerializedName("Simage1")
    private  final  String simage1 ="";
    @SerializedName("Simage2")
    private  final  String simage2 ="";
    @SerializedName("Simage3")
    private  final  String simage3 ="";
    @SerializedName("Simage4")
    private  final  String simage4 ="";
    @SerializedName("Simage5")
    private  final  String simage5 ="";
    @SerializedName("Simage6")
    private  final  String simage6 ="";
    @SerializedName("Simage7")
    private  final  String simage7 ="";
    @SerializedName("Simage8")
    private  final  String simage8 ="";
    @SerializedName("Simage9")
    private  final  String simage9 ="";
    @SerializedName("City")
    private  String city;/*城市名称*/
    @SerializedName("CreateTime")
    private String hideTime;/*藏宝时间*/
    @SerializedName("Tokenid")
    private int tokenId;/*用户口令*/
    @SerializedName("isState")
    private boolean isState;//是否找到

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int getCode() {
        return code;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    public int getLevels() {
        return levels;
    }

    public int getSize() {
        return size;
    }

    public int getTreasuerID() {
        return treasuerID;
    }

    public String getDesc() {
        return desc;
    }

    public String getMsg() {
        return msg;
    }

    public String getBimage1() {
        return Bimage1;
    }

    public String getPosition() {
        return position;
    }

    public int getTokenId() {
        return tokenId;
    }

    public String getCity() {
        return city;
    }

    public String getHideTime() {
        return hideTime;
    }

    public String getSimage1() {
        return simage1;
    }

    public String getTitle() {
        return title;
    }
}
