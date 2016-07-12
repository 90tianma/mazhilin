package com.zhuoxin.treasure.treasure.map;

import com.baidu.mapapi.map.*;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.zhuoxin.treasure.net.NetClient;
import com.zhuoxin.treasure.treasure.Area;
import com.zhuoxin.treasure.treasure.Treasure;
import com.zhuoxin.treasure.treasure.TreasureRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2016/6/21.
 */
public class MapPresenter extends MvpNullObjectBasePresenter<MapView> {
    private Call<List<Treasure>> call;
    private Area area;

    /**
     * 根据指定区域获取宝藏
     */
    public void getTreasure(Area area) {
        // 如果当前这个区域已获取过
        if (TreasureRepo.getInstance().isCached(area)) {
            return;
        }
        this.area = area;
        if (call != null) call.cancel();
        // 构造出获取宝藏的Call模型
        call = NetClient.getInstance().getTreasureApi().getTreasureInArea(area);
        // 加入请求队列
        call.enqueue(callback);
    }

    private Callback<List<Treasure>> callback = new Callback<List<Treasure>>() {
        @Override public void onResponse(Call<List<Treasure>> call, Response<List<Treasure>> response) {
            if (response != null && response.isSuccessful()) {
                // 得到响应体数据
                List<Treasure> datas = response.body();
                if (datas == null) {
                    getView().showMessage("unknown error");
                    return;
                }
                // 加入缓存仓库(区域)
                TreasureRepo.getInstance().cache(area);
                // 加入缓存仓库(宝藏)
                TreasureRepo.getInstance().addTreasure(datas);
                // 通知视图
                getView().setData(datas);
            }
        }

        @Override public void onFailure(Call<List<Treasure>> call, Throwable t) {
            getView().showMessage(t.getMessage());
        }
    };

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) {
            call.cancel();
        }
    }

}
