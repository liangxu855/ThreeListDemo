package com.example.administrator.threelistdemo.widget.popwindow;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.threelistdemo.R;
import com.example.administrator.threelistdemo.model.PlayDataBean;
import com.example.administrator.threelistdemo.model.RoleDataBean;
import com.example.administrator.threelistdemo.model.SelectTimeAndRole;

import java.util.ArrayList;
import java.util.List;

public class SelectPopWindow extends PopupWindow implements View.OnClickListener {

    private SelectCategory selectCategory;
    private PlayDataBean playDataBean;
    private RoleDataBean roleDataBean;
    private TextView play;
    private TextView role;
    private View contentView;
    private RecyclerView firstList;
    private RecyclerView secondList;

    private SecondListAdapter secondListAdapter;
    private List<String> selectPlay = new ArrayList<>(); //选择的剧目
    private List<SelectTimeAndRole> selectTimeAndRoleList = new ArrayList<>(); //选择的时间 角色
    private final FirstListAdapter firstListAdapter;

    public SelectPopWindow(Activity activity, PlayDataBean playDataBean, final RoleDataBean roleDataBean, final SelectCategory selectCategory) {
        this.playDataBean = playDataBean;
        this.roleDataBean = roleDataBean;
        this.selectCategory = selectCategory;
        contentView = LayoutInflater.from(activity).inflate(R.layout.layout_quyu_choose_view, null);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小

        this.setContentView(contentView);
        this.setWidth(dm.widthPixels);
        this.setHeight(dm.heightPixels * 7 / 10);

        /* 设置背景显示 */
        setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.pop_bg));

        /* 设置触摸外面时消失 */
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(true); /*设置点击menu以外其他地方以及返回键退出 */
        contentView.setFocusableInTouchMode(true);/* 1.解决再次点击MENU键无反应问题*/

        play = contentView.findViewById(R.id.play);
        play.setOnClickListener(this);
        role = contentView.findViewById(R.id.role);
        role.setOnClickListener(this);
        contentView.findViewById(R.id.delete).setOnClickListener(this);
        contentView.findViewById(R.id.confirm).setOnClickListener(this);

        firstList = contentView.findViewById(R.id.firstList);
        firstList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        firstListAdapter = new FirstListAdapter(playDataBean.getPlay_list(), roleDataBean.getTime_list());

        firstListAdapter.setOnItemClickListener(new FirstListAdapter.OnItemClickListener() {
            @Override
            public void clickPlay(View view, PlayDataBean.Play_list play_list) {
                for (int i = 0; i < selectPlay.size(); i++) {
                    if (TextUtils.equals(selectPlay.get(i), play_list.getName())) {
                        return;
                    }
                }
                play_list.setSelect(true);
                selectPlay.add(play_list.getName());
                view.setBackgroundColor(Color.GREEN);
                Log.e("添加剧目", play_list.getName());
                firstListAdapter.notifyDataSetChanged();
            }

            @Override
            public void clickTime(View view, final RoleDataBean.Time_list time_list) {
                Log.e("添加角色", "d点击了" + time_list.getTime());
                secondListAdapter = new SecondListAdapter(time_list.getRole_list());

                secondList.setAdapter(secondListAdapter);
                secondListAdapter.setOnItemClickListener(new SecondListAdapter.OnItemClickListener() {
                    @Override
                    public void click(View view, RoleDataBean.Role_list roleList) {
                        for (int i = 0; i < selectTimeAndRoleList.size(); i++) {
                            if (selectTimeAndRoleList.get(i).getTime().equals(time_list.getTime()) && selectTimeAndRoleList.get(i).getRole().equals(roleList.getRole()) && selectTimeAndRoleList.get(i).getState().equals(roleList.getState())) {
                                return;
                            }
                        }
                        roleList.setSelect(true);
                        SelectTimeAndRole selectTimeAndRole = new SelectTimeAndRole(time_list.getTime(), roleList.getRole(), roleList.getState());
                        selectTimeAndRoleList.add(selectTimeAndRole);
                        view.setBackgroundColor(Color.GREEN);
                        Log.e("添加角色", selectTimeAndRole.toString());
                        secondListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        firstList.setAdapter(firstListAdapter);
        secondList = contentView.findViewById(R.id.secondList);
        secondList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        play.setClickable(false);
        role.setClickable(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play:  //剧目
                selectPlay();
                break;
            case R.id.role:  //角色
                selectRole();
                break;
            case R.id.delete:  //清空
                delete();
                break;
            case R.id.confirm: //确定
                confirm();
                break;
        }
    }

    private void confirm() {
        selectCategory.selectCategory(selectPlay,selectTimeAndRoleList);
    }

    private void delete() {
        selectPlay.clear();
        selectTimeAndRoleList.clear();
        firstListAdapter.clearData();
        secondListAdapter.ClearData();
    }

    private void selectRole() {
        role.setBackgroundColor(Color.WHITE);
        role.setClickable(false);
        play.setBackgroundColor(contentView.getContext().getResources().getColor(R.color.gray_text));
        play.setClickable(true);
        contentView.findViewById(R.id.second_parent).setVisibility(View.VISIBLE);
        firstListAdapter.setState(FirstListAdapter.STATE_TIME);


    }

    private void selectPlay() {
        play.setBackgroundColor(Color.WHITE);
        play.setClickable(false);
        role.setBackgroundColor(contentView.getContext().getResources().getColor(R.color.gray_text));
        role.setClickable(true);
        contentView.findViewById(R.id.second_parent).setVisibility(View.INVISIBLE);
        firstListAdapter.setState(FirstListAdapter.STATE_PLAY);
    }

    /**
     * 选择成功回调
     *
     * @author apple
     */
    public interface SelectCategory {
        /**
         * 把选中的下标通过方法回调回来
         *
         * @param selectPlay   选择的剧目
         * @param selectTimeAndRoles 选择的时间角色
         */
        public void selectCategory(List<String> selectPlay,List<SelectTimeAndRole> selectTimeAndRoles);
    }


    static class FirstListAdapter extends RecyclerView.Adapter<FirstListAdapter.ViewHolder> {
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
        public void clearData(){
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
        public void onBindViewHolder(final FirstListAdapter.ViewHolder holder, int position) {
            if (state == STATE_PLAY) {
                if (play_lists.get(position).isSelect()){
                    holder.mText.setBackgroundColor(Color.GREEN);
                }else{
                    holder.mText.setBackgroundColor(Color.WHITE);
                }
                holder.mText.setText(play_lists.get(position).getName());
                final int mPosition = position;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemClickListener != null) {
                            onItemClickListener.clickPlay(view, play_lists.get(mPosition));
                        }
                    }
                });
            } else {
                holder.mText.setText(time_lists.get(position).getTime());
                holder.mText.setBackgroundColor(Color.WHITE);
                final int mPosition = position;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemClickListener != null) {
                            onItemClickListener.clickTime(view, time_lists.get(mPosition));
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
            void clickPlay(View view, PlayDataBean.Play_list play_list);

            void clickTime(View view, RoleDataBean.Time_list time_list);
        }
    }

    static class SecondListAdapter extends RecyclerView.Adapter<SecondListAdapter.ViewHolder> {
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
        public void onBindViewHolder(SecondListAdapter.ViewHolder holder, int position) {
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
                        onItemClickListener.click(view, list.get(mPosition));
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
            void click(View view, RoleDataBean.Role_list roleList);
        }
    }
}
