package com.example.administrator.threelistdemo.widget.popwindow;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.threelistdemo.R;
import com.example.administrator.threelistdemo.model.PlayDataBean;
import com.example.administrator.threelistdemo.model.RoleDataBean;
import com.example.administrator.threelistdemo.model.SelectTimeAndRole;

import java.util.List;

public class SelectPopWindow extends PopupWindow implements View.OnClickListener {

    private SelectCategory selectCategory;
    private TextView play;
    private TextView role;
    private View contentView;
    private RecyclerView firstList;
    private RecyclerView secondList;

    private SecondListAdapter secondListAdapter;
    //    private List<String> selectPlay = new ArrayList<>(); //选择的剧目
//    private List<SelectTimeAndRole> selectTimeAndRoleList = new ArrayList<>(); //选择的时间 角色
    private String selectPlay = "";//选择的剧目
    private SelectTimeAndRole selectTimeAndRole = new SelectTimeAndRole();//选择的时间 角色
    private final FirstListAdapter firstListAdapter;

    public SelectPopWindow(Activity activity, PlayDataBean playDataBean, RoleDataBean roleDataBean, SelectCategory selectCategory) {

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
            public void clickPlay(View view, List<PlayDataBean.Play_list> play_lists, int position) {
                for (int i = 0; i < play_lists.size(); i++) {
                    if (play_lists.get(i).isSelect()) {
                        play_lists.get(i).setSelect(false);
                    }
                }
                selectPlay = play_lists.get(position).getName();
                play_lists.get(position).setSelect(true);
                firstListAdapter.notifyDataSetChanged();
            }

            @Override
            public void clickTime(View view, final List<RoleDataBean.Time_list> time_lists, int position) {
                Log.e("添加角色", "d点击了" + time_lists.get(position).getTime());
                for (int i = 0; i < time_lists.size(); i++) {
                    if (time_lists.get(i).isSelect()) {
                        time_lists.get(i).setSelect(false);
                    }
                }
                final int mTimePosition = position;

                time_lists.get(position).setSelect(true);
                firstListAdapter.notifyDataSetChanged();
                secondListAdapter = new SecondListAdapter(time_lists.get(position).getRole_list());
                secondList.setAdapter(secondListAdapter);
                secondListAdapter.setOnItemClickListener(new SecondListAdapter.OnItemClickListener() {
                    @Override
                    public void click(View view, List<RoleDataBean.Role_list> roleLists, int position) {
                        Log.e("添加角色", "d点击了" + roleLists.get(position).getRole());
                        if (lastSelectRoleIndex == -1) {
                            lastSelectTimeIndex = mTimePosition;
                            lastSelectRoleIndex = position;
                        } else {
                            time_lists.get(lastSelectTimeIndex).getRole_list().get(lastSelectRoleIndex).setSelect(false);
                            lastSelectTimeIndex = mTimePosition;
                            lastSelectRoleIndex = position;
                        }
                        selectTimeAndRole.setTime(time_lists.get(mTimePosition).getTime());
                        selectTimeAndRole.setRole(roleLists.get(position).getRole());
                        selectTimeAndRole.setState(roleLists.get(position).getState());
                        roleLists.get(position).setSelect(true);
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

    private int lastSelectTimeIndex = -1; //上一次选的时间角标
    private int lastSelectRoleIndex = -1;//上一次选的角色角标

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
        selectCategory.selectCategory(selectPlay, selectTimeAndRole);
    }

    private void delete() {
        selectPlay = "";
        selectTimeAndRole.clear();
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
         * @param selectPlay         选择的剧目
         * @param selectTimeAndRoles 选择的时间角色
         */
        void selectCategory(String selectPlay, SelectTimeAndRole selectTimeAndRoles);
    }

}
