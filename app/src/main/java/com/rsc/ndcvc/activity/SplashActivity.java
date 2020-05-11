package com.rsc.ndcvc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.jacksonandroidnetworking.JacksonParserFactory;
import com.rsc.ndcvc.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //open login
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 3000);
        //main app with network initializer
        runFirst();
    }

    void runFirst() {
        AndroidNetworking.initialize(this);
        AndroidNetworking.setParserFactory(new JacksonParserFactory());
    }
}
