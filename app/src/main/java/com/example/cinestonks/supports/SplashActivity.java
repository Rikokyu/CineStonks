package com.example.cinestonks.supports;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cinestonks.BLL.chuyen_account;
import com.example.cinestonks.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);

        // Chuyển sang màn hình đăng nhập sau 2 giây
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, chuyen_account.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}
