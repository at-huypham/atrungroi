package com.atrungroi.atrungroi.pref;

import android.app.Application;
import android.os.SystemClock;

import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

/**
 * Created by huyphamna.
 */

public class App extends Application {
    private static App mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(0));
        mInstance = this;
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
    public static synchronized App getInstance() {
        return mInstance;
    }
}
