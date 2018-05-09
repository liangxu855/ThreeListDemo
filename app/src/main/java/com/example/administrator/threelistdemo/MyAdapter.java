package com.example.administrator.threelistdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter<T> extends RecyclerView.Adapter<MyAdapter.RecyclerHolder> {

    private Context mContext;
    ArrayList<T> dataList = new ArrayList<>();

    public MyAdapter(RecyclerView recyclerView) {
        this.mContext = recyclerView.getContext();
    }

    public void setData(List<T> dataList) {
        if (null != dataList) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.id_rv_item_layout, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        holder.textView.setText((CharSequence) dataList.get(position));
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView textView;
        private RecyclerHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_body);
        }
    }
}
