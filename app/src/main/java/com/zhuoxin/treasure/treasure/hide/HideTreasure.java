package com.zhuoxin.treasure.treasure.hide;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2016/6/23.
 */
public class HideTreasure {
    @SerializedName("TreasureName")/*名称*/
    private String treasuerTitle;
    @SerializedName("ShortContent")/*描述*/
    private String treasureDesc;
    @SerializedName("POI")/*位置*/
    private  String treasuerPosition;
    @SerializedName("Height")/*海拔*/
    private  String treasuerHeigth;
    @SerializedName("Tokenid")/*用户令牌*/
    private int treasuerTokenId;
    @SerializedName("Xline")/*经度*/
    private double treasuerLon;
    @SerializedName("Yline")/*纬度*/
    private double treasuerLat;

    public void setTreasuerHeigth(String treasuerHeigth) {
        this.treasuerHeigth = treasuerHeigth;
    }

    public void setTreasuerLon(double treasuerLon) {
        this.treasuerLon = treasuerLon;
    }

    public void setTreasuerLat(double treasuerLat) {
        this.treasuerLat = treasuerLat;
    }

    public void setTreasuerTitle(String treasuerTitle) {
        this.treasuerTitle = treasuerTitle;
    }

    public void setTreasuerPosition(String treasuerPosition) {
        this.treasuerPosition = treasuerPosition;
    }

    public void setTreasuerTokenId(int treasuerTokenId) {
        this.treasuerTokenId = treasuerTokenId;
    }

    public void setTreasureDesc(String treasureDesc) {
        this.treasureDesc = treasureDesc;
    }

    public String getTreasuerTitle() {
        return treasuerTitle;
    }

    public String getTreasureDesc() {
        return treasureDesc;
    }

    public String getTreasuerHeigth() {
        return treasuerHeigth;
    }

    public double getTreasuerLon() {
        return treasuerLon;
    }

    public double getTreasuerLat() {
        return treasuerLat;
    }

    public String getTreasuerPosition() {
        return treasuerPosition;
    }

    public int getTreasuerTokenId() {
        return treasuerTokenId;
    }

    @SerializedName("Size")/*宝藏大小*/
    private final int treasuerSize=1;
    @SerializedName("Levels")/*宝藏级别*/
    private final int treasuerLevels=2;
    @SerializedName("Bimage1")
    private  final  String treasuerBimage1 ="";
    @SerializedName("Bimage2")
    private  final  String treasuerBimage2 ="";
    @SerializedName("Bimage3")
    private  final  String treasuerBimage3 ="";
    @SerializedName("Bimage4")
    private  final  String treasuerBimage4 ="";
    @SerializedName("Bimage5")
    private  final  String treasuerBimage5 ="";
    @SerializedName("Bimage6")
    private  final  String treasuerBimage6 ="";
    @SerializedName("Bimage7")
    private  final  String treasuerBimage7 ="";
    @SerializedName("Bimage8")
    private  final  String treasuerBimage8 ="";
    @SerializedName("Bimage9")
    private  final  String treasuerBimage9 ="";

}
