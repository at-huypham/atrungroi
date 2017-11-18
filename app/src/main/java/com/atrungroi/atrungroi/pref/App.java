package com.atrungroi.atrungroi.pref;

import android.app.Application;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

/**
 * Created by huyphamna.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(2));
    }
}
