package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.week01.LogTestActivity;
import com.example.myapplication.week02.QueryActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private ActivityMainBinding viewBinding;
    private Button btnToWeek01;
    private Button btnToWeek02;
    private Button btnToWeek03;
    private View.OnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        initLayout();
        initListener();
    }


    private void initLayout() {
        btnToWeek01 = viewBinding.toWeek01;
        btnToWeek02 = viewBinding.toWeek02;
        btnToWeek03 = viewBinding.toWeek03;
    }

    private void initListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.toWeek01:
                        startActivity(new Intent(MainActivity.this, LogTestActivity.class));
                        break;
                    case R.id.toWeek02:
//                    todo
                        startActivity(new Intent(MainActivity.this, QueryActivity.class));
                        break;
                    case R.id.toWeek03:
//                     todo
                        startActivity(new Intent(MainActivity.this, LogTestActivity.class));
                        break;
                    default:
                        break;
                }
            }
        };
//    设置监听器
        btnToWeek01.setOnClickListener(listener);
        btnToWeek02.setOnClickListener(listener);
        btnToWeek03.setOnClickListener(listener);
    }
}