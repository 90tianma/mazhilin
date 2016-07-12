package com.zhuoxin.treasure.treasure.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zhuoxin.treasure.treasure.Treasure;
import com.zhuoxin.treasure.treasure.TreasureView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by user on 2016/6/23.
 */
public class TreasuerAdapter extends RecyclerView.Adapter<TreasuerAdapter.MyViewHolder> {
    ArrayList<Treasure> data=new ArrayList<Treasure>();
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(new TreasureView(parent.getContext()));
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Treasure treasure=data.get(position);
        holder.treasureView.bindTreasure(treasure);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    static final class  MyViewHolder extends  RecyclerView.ViewHolder{
        final  TreasureView treasureView;
        public MyViewHolder(TreasureView itemView) {
            super(itemView);
            treasureView= itemView;
        }
    }
    /*将集合中的每一项都添加到数据源中，然后通知数据远发生变化*/
    public final  void addItems(Collection<Treasure> items){
        if (items!=null){
            data.addAll(items);
            notifyItemChanged(0,data.size());
        }
    }
    /*删除item时从数据源中也删除*/
    @SuppressWarnings("unused")
    public final void deleteItems(Collection<Treasure> items){
        if (items!=null){
            data.remove(items);
            notifyDataSetChanged();
        }
    }
}
