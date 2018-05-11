package com.example.administrator.threelistdemo.widget.popwindow;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.threelistdemo.R;
import com.example.administrator.threelistdemo.model.RoleDataBean;

import java.util.List;

public class SecondListAdapter extends RecyclerView.Adapter<SecondListAdapter.ViewHolder> {
    private List<RoleDataBean.Role_list> list;

    public SecondListAdapter(List<RoleDataBean.Role_list> list) {
        this.list = list;
    }

    public void ClearData() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSelect(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public SecondListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_second, parent, false);
        SecondListAdapter.ViewHolder viewHolder = new SecondListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SecondListAdapter.ViewHolder holder, final int position) {
        if (list.get(position).isSelect()){
            holder.itemView.setBackgroundColor(Color.GREEN);
        }else{
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        holder.mText.setText(list.get(position).getRole());
        if (list.get(position).getState().equals("0")) {
            holder.mState.setText("角色");
        } else {
            holder.mState.setText("临时");
        }
        final int mPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.click(view,list,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mText;
        TextView mState;

        ViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.item_tx);
            mState = itemView.findViewById(R.id.item_state);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void click(View view, List<RoleDataBean.Role_list> roleLists,int position);
    }
}
