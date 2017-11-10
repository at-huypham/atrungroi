package com.atrungroi.atrungroi.ui;

import android.app.Application;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

/**
 * Created by huyphamna on 10/11/2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(5));
    }
}
