package com.redbox.quickRent.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.redbox.quickRent.R;

public class SplashScreen extends AppCompatActivity {
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sessionManager = new SessionManager(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionManager.isLogin()){
                    startActivity(new Intent(SplashScreen.this,DashBoardActivity.class));
                }else {
                    startActivity((new Intent(SplashScreen.this,LoginActivity.class)));
                }
            }
        },4500);
    }
}
