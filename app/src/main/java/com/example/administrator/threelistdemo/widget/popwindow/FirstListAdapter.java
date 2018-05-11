package com.example.administrator.threelistdemo.widget.popwindow;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.threelistdemo.R;
import com.example.administrator.threelistdemo.model.PlayDataBean;
import com.example.administrator.threelistdemo.model.RoleDataBean;

import java.util.List;

public class FirstListAdapter extends RecyclerView.Adapter<FirstListAdapter.ViewHolder> {
    private List<PlayDataBean.Play_list> play_lists;
    private List<RoleDataBean.Time_list> time_lists;


    public static final int STATE_PLAY = 0;
    public static final int STATE_TIME = 1;
    private int state = STATE_PLAY;

    public FirstListAdapter(List<PlayDataBean.Play_list> list1, List<RoleDataBean.Time_list> list2) {
        this.play_lists = list1;
        this.time_lists = list2;
    }


    public void setState(int state) {
        this.state = state;
        notifyDataSetChanged();
    }

    public void clearData() {
        for (int i = 0; i < play_lists.size(); i++) {
            play_lists.get(i).setSelect(false);
        }
        for (int i = 0; i < time_lists.size(); i++) {
            for (int i1 = 0; i1 < time_lists.get(i).getRole_list().size(); i1++) {
                time_lists.get(i).getRole_list().get(i1).setSelect(false);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public FirstListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_base_use, parent, false);
        FirstListAdapter.ViewHolder viewHolder = new FirstListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FirstListAdapter.ViewHolder holder, final int position) {
        if (state == STATE_PLAY) {
            if (play_lists.get(position).isSelect()) {
                holder.mText.setBackgroundColor(Color.GREEN);
            } else {
                holder.mText.setBackgroundColor(Color.WHITE);
            }
            holder.mText.setText(play_lists.get(position).getName());
            final int mPosition = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.clickPlay(view, play_lists,position);
                    }
                }
            });
        } else {
            if (time_lists.get(position).isSelect()) {
                holder.mText.setBackgroundColor(Color.GREEN);
            } else {
                holder.mText.setBackgroundColor(Color.WHITE);
            }
            holder.mText.setText(time_lists.get(position).getTime());
            final int mPosition = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.clickTime(view, time_lists, position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (state == STATE_PLAY) {
            return play_lists.size();
        } else {
            return time_lists.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mText;

        ViewHolder(final View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.item_tx);
        }

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void clickPlay(View view, List<PlayDataBean.Play_list> play_lists, int position);

        void clickTime(View view, List<RoleDataBean.Time_list> time_lists, int position);
    }
}
