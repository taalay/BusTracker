package com.tali.admin.bustracker;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

public class App extends Application {

    private static App singleton;
    private Typeface avenirFont;
    public static final String APP_PREFERENCES = "userPreferences";


    public static App getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

    }

    public static SharedPreferences getUserPreferences() {
        return singleton.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

}
