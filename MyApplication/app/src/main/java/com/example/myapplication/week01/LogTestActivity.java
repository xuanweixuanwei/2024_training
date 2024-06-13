package com.example.myapplication.week01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.databinding.ActivityLogTestBinding;
import com.example.myapplication.databinding.ActivityMainBinding;

public class LogTestActivity extends AppCompatActivity {

    public static final String TAG ="LogTestActivity";
    ActivityLogTestBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityLogTestBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        viewBinding.helloBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"HELLO WORLD!");
                Log.d(TAG,"HELLO WORLD!");
                Log.d(TAG,"HELLO WORLD!");
                Log.d(TAG,"HELLO WORLD!");
            }
        });
    }


}