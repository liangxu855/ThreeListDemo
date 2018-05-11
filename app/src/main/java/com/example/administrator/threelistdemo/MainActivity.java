package com.example.administrator.threelistdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.threelistdemo.model.PlayDataBean;
import com.example.administrator.threelistdemo.model.RoleDataBean;
import com.example.administrator.threelistdemo.model.SelectTimeAndRole;
import com.example.administrator.threelistdemo.widget.popwindow.SelectPopWindow;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements SelectPopWindow.SelectCategory {
    TextView tvResult;
    public static final String playJson = "{\"play_list\":[{\"name\":\"欢乐喜剧人\"},{\"name\":\"大闹天竺\"},{\"name\":\"少年巴比伦\"},{\"name\":\"天空之眼\"},{\"name\":\"咸鱼传奇\"}]}";
    public static final String roleJson = "{\"time_list\":[{\"time\":\"05.01\",\"role_list\":[{\"role\":\"角色1\",\"state\":\"0\"},{\"role\":\"临时1\",\"state\":\"1\"},{\"role\":\"角色2\",\"state\":\"0\"},{\"role\":\"角色3\",\"state\":\"0\"},{\"role\":\"临时2\",\"state\":\"1\"},{\"role\":\"临时3\",\"state\":\"1\"},{\"role\":\"角色4\",\"state\":\"0\"}]},{\"time\":\"05.02\",\"role_list\":[{\"role\":\"角色1\",\"state\":\"0\"},{\"role\":\"角色2\",\"state\":\"0\"},{\"role\":\"角色3\",\"state\":\"0\"},{\"role\":\"临时1\",\"state\":\"1\"},{\"role\":\"临时2\",\"state\":\"1\"},{\"role\":\"临时3\",\"state\":\"1\"},{\"role\":\"角色4\",\"state\":\"0\"}]},{\"time\":\"05.03\",\"role_list\":[{\"role\":\"临时1\",\"state\":\"1\"},{\"role\":\"角色1\",\"state\":\"0\"},{\"role\":\"角色2\",\"state\":\"0\"},{\"role\":\"临时2\",\"state\":\"1\"},{\"role\":\"临时3\",\"state\":\"1\"},{\"role\":\"临时4\",\"state\":\"1\"},{\"role\":\"角色3\",\"state\":\"0\"}]},{\"time\":\"05.04\",\"role_list\":[{\"role\":\"角色1\",\"state\":\"0\"},{\"role\":\"角色2\",\"state\":\"0\"},{\"role\":\"角色3\",\"state\":\"0\"},{\"role\":\"临时1\",\"state\":\"1\"},{\"role\":\"临时2\",\"state\":\"1\"},{\"role\":\"临时3\",\"state\":\"1\"},{\"role\":\"角色4\",\"state\":\"0\"}]},{\"time\":\"05.05\",\"role_list\":[{\"role\":\"角色1\",\"state\":\"0\"},{\"role\":\"角色2\",\"state\":\"0\"},{\"role\":\"角色3\",\"state\":\"0\"},{\"role\":\"临时1\",\"state\":\"1\"},{\"role\":\"临时2\",\"state\":\"1\"},{\"role\":\"临时3\",\"state\":\"1\"},{\"role\":\"角色4\",\"state\":\"0\"}]}]}";
    private PlayDataBean playDataBean;
    private RoleDataBean roleDataBean;
    private SelectPopWindow mPopupWindow = null;
    private TextView select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //将Toolbar显示到界面
        setSupportActionBar(toolbar);
        //设置默认的标题不显示
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvResult = findViewById(R.id.tv_result);
        Gson gson = new Gson();
        playDataBean = gson.fromJson(playJson, PlayDataBean.class);
        roleDataBean = gson.fromJson(roleJson,RoleDataBean.class);
        select = findViewById(R.id.toolbar_subtitle);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //显示popwindow
                if(mPopupWindow == null){
                    mPopupWindow = new SelectPopWindow(MainActivity.this,playDataBean,roleDataBean,MainActivity.this);
                }
                mPopupWindow.showAsDropDown(select, -5, 10);
            }
        });
    }

    @Override
    public void selectCategory(String selectPlay, SelectTimeAndRole selectTimeAndRole) {
        mPopupWindow.dismiss();
        Toast.makeText(this,selectPlay+selectTimeAndRole.toString(),Toast.LENGTH_SHORT).show();
        tvResult.setText(selectPlay+selectTimeAndRole.toString());
    }
}
