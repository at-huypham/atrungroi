package com.atrungroi.atrungroi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.atrungroi.atrungroi.pref.PrefManager;

/**
 * Created by huyphamna.
 */

public class SplashActivity extends AppCompatActivity{
    private PrefManager mPrefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
        checkPref();
    }

    private void checkPref() {
        mPrefManager = new PrefManager(this);
        if (!mPrefManager.isFirstTime()) {
            launchHomeScreen();
            finish();
        } else {
            launchIntro();
        }
    }

    private void launchHomeScreen() {
        mPrefManager.setFirstTimeLauch(false);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    private void launchIntro(){
        startActivity(new Intent(SplashActivity.this, IntroActivity.class));
    }
}
