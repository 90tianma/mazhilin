package com.zhuoxin.treasure.user;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 2016/6/16.
 */
public class UserPref {
  private final String PER_NAME="userInfo";
  private static final String KYE_PHOTO="keyPhoto";
  private static final String KEY_TOOKENID="keyTookenId";
  private  final SharedPreferences preferences;
  /*单例*/
  private static UserPref userPrefs;
    /*初始化*/
  public static void init(Context context){
      userPrefs=new UserPref(context);
  }
  private UserPref(Context context) {
      preferences =context.getApplicationContext().getSharedPreferences(PER_NAME,Context.MODE_PRIVATE);
  }
    public static UserPref getInstance(){
       return  userPrefs;
    }

    /*设置*/
    public void setPhoto(String photoUrl){
        preferences.edit().putString(KYE_PHOTO,photoUrl).apply();
    }
    public void setTookenId(int tookenId){
        preferences.edit().putInt(KEY_TOOKENID,tookenId).apply();
    }

    /*获取信息*/
    public String getPhotoUrl(){
      return  preferences.getString(KYE_PHOTO,null);
    }
    public int getTookenId(){
    return  preferences.getInt(KEY_TOOKENID,-1);
  }
}
