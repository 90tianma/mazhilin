package com.zhuoxin.treasure.user;

import android.app.Fragment;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.map.TextureMapView;
import com.zhuoxin.treasure.utils.ActivityUtils;

import java.io.IOException;

import okhttp3.MediaType;

/**
 * 首页播放视频的fragment    使用TrexTureView + MediaPlayer + fragment实现视频播放
 */
public class MainMP4Fragment  extends Fragment implements  TextureView.SurfaceTextureListener {
    private TextureView textureView;/*播放媒体的视图*/
    private MediaPlayer mediaPlayer;/*媒体播放器*/
    private ActivityUtils activityUtils;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        textureView=new TextureView(getActivity());
        activityUtils=new ActivityUtils(getActivity());
        return textureView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        textureView.setSurfaceTextureListener(this);/*添加监听*/
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        Surface surface=new Surface(surfaceTexture);/*播放器的载体*/
        try {
            AssetFileDescriptor afd=getActivity().getAssets().openFd("welcome.mp4");/*打开播放文件*/
            mediaPlayer=new MediaPlayer();/*初始化播放器*/
            mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mediaPlayer.setSurface(surface);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setLooping(true);/*设置可以循环播放*/
                    mediaPlayer.seekTo(0);/*跳到开始的位置*/
                    mediaPlayer.start();/*开始播放*/
                }
            });
        } catch (IOException e) {
            activityUtils.showToast("媒体播放失败");
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}
