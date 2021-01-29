package com.application.newsapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.application.newsapp.R;
import com.application.newsapp.api.AppConstant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    Context context;
    Activity activity;
    public Handler handler = new Handler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        context = SplashActivity.this;
        activity = SplashActivity.this;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();

            }
        },3000);

    }





    }

