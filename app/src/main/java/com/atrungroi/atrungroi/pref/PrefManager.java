package com.atrungroi.atrungroi.pref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by huyphamna
 * Just show intro in the first time start app
 */
public class PrefManager {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    // shared pref mode
    private int PRIVATE_MODE = 0;

    // shared preferences file name
    private static final String PREF_NAME = "atrungroi";
    private static final String IS_FIRST_TIME_LAUCH = "IsFirstTimeLauch";

    public PrefManager(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mPreferences.edit();
    }

    public void setFirstTimeLauch(boolean isFirstTime) {
        mEditor.putBoolean(IS_FIRST_TIME_LAUCH, isFirstTime);
        mEditor.commit();
    }

    public boolean isFirstTime() {
        return mPreferences.getBoolean(IS_FIRST_TIME_LAUCH, true);
    }

    public String tokenUser() {
        return mPreferences.getString("regId", "none");
    }
}
