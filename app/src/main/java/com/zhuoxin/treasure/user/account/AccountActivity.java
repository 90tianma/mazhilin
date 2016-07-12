package com.zhuoxin.treasure.user.account;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhuoxin.treasure.R;
import com.zhuoxin.treasure.user.UserPref;
import com.zhuoxin.treasure.utils.ActivityUtils;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountActivity extends MvpActivity<AccountView,AccountPresenter> implements  AccountView {
    @Bind(R.id.user_toolbar)Toolbar toolbar;
    @Bind(R.id.user_icon)
    ImageView ivIcon;
    @Bind(R.id.user_acount)
    TextView tvAcount;
    @Bind(R.id.user_psw) TextView tvPsw;
    @Bind(R.id.user_update) TextView tvUpdate;
    @Bind(R.id.user_exit) TextView tvExit;
   private IconSelectWindow iconSelectWindow;
    private ActivityUtils activityUtils;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        activityUtils=new ActivityUtils(this);
        ButterKnife.bind(this);
        String url= UserPref.getInstance().getPhotoUrl();
        if (url!=null){
            ImageLoader.getInstance().displayImage(url,ivIcon);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @NonNull
    @Override
    public AccountPresenter createPresenter() {
        return new AccountPresenter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @OnClick({R.id.user_acount,R.id.user_exit,R.id.user_psw,R.id.user_icon,R.id.user_update})
    /*点击事件*/
    public void alllOnClick(View view){
        switch (view.getId()){
            case R.id.user_icon:
                //弹出PopupWindow
                if (iconSelectWindow==null){//创建一个PopupWindow
                    iconSelectWindow=new IconSelectWindow(this,listener);
                }
                if(iconSelectWindow.isShowing()){
                    iconSelectWindow.dismiss();//隐藏弹窗
                    return;
                }
                iconSelectWindow.show();//显示弹窗
                break;
            case R.id.user_acount:
                break;
            case R.id.user_psw:
                break;
            case R.id.user_update:
                break;
            case R.id.user_exit:
                break;
        }
    }
    CropHandler cropHandler=new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
            activityUtils.showToast("剪切成功");
            //上传图片
            File file=new File(uri.getPath());
            getPresenter().uploadImage(file);
        }

        @Override
        public void onCropCancel() {
            activityUtils.showToast("取消剪切");
        }

        @Override
        public void onCropFailed(String message) {
            activityUtils.showToast("剪切失败");
        }
        //设计剪切的范围
        @Override
        public CropParams getCropParams() {
            CropParams cropParams=new CropParams();
            cropParams.aspectX=200;
            cropParams.aspectY=200;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            return AccountActivity.this;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 帮助我们去处理结果(剪切完的图像)
        CropHelper.handleResult(cropHandler, requestCode, resultCode, data);
    }

    private final IconSelectWindow.Listener listener = new IconSelectWindow.Listener() {
        @Override
        public void toGallery() {
            // 按下相册时
            // 打开相册 - 到相册选择图像 - 对图像进行剪切 - 将图传回来 - 将图像上传到服务器
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
            startActivityForResult(intent, CropHelper.REQUEST_CROP);
        }
        @Override
        public void toCamera() {
            // 按下相机时
            // 打开相机 - 拍照 - 打开相册 - 到相册选择图像 - 对图像进行剪切 - 将图传回来 - 将图像上传到服务器
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
            startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
        }
    };

    @Override
    public void showProgress() {
        progressDialog=ProgressDialog.show(this,"","正在上传中……");
    }

    @Override
    public void hidProgress() {
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);

    }

    @Override
    public void updateIcon(String url) {
        /*更新头像*/
        ImageLoader.getInstance().displayImage(url,ivIcon);
    }

}
