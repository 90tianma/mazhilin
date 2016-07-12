package com.zhuoxin.treasure.treasure.hide;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.zhuoxin.treasure.net.NetClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2016/6/23.
 */
public class HideTreasurePresenter extends MvpNullObjectBasePresenter<HideTreasuerView> {
   private Call<HideTreasuerResult> call;
    public void hide(HideTreasure hideTreasure){/*直接传递宝藏请求实体代替RequestBody*/
        if (call!=null) call.cancel();
        call= NetClient.getInstance().getTreasureApi().hide(hideTreasure);
        call.enqueue(callBack);
        getView().showProgress();
    }

    private Callback<HideTreasuerResult> callBack=new Callback<HideTreasuerResult>() {
        @Override
        public void onResponse(Call<HideTreasuerResult> call, Response<HideTreasuerResult> response) {
            getView().hidProgress();
            if (response!=null && response.isSuccessful()){
                HideTreasuerResult result=response.body();
                if (result==null){
                    getView().showMessage("nuKnown error");
                    return;
                }
                if (result.getCode()==1){
                    getView().navigateToHome();
                }else {
                    getView().showMessage(result.getMsg());
                }
            }
        }

        @Override
        public void onFailure(Call<HideTreasuerResult> call, Throwable t) {
            getView().hidProgress();
            getView().showMessage(t.getMessage());
        }
    };

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call!=null){
            call.cancel();
        }
    }
}
