package com.example.administrator.threelistdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    RecyclerView firstList ;
    RecyclerView secondList ;
    RecyclerView thirdList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstList = findViewById(R.id.first_list);
        secondList = findViewById(R.id.second_list);
        thirdList = findViewById(R.id.third_list);
    }
}
