package com.sample.zomatosample.application;

import android.app.Application;

import com.sample.zomatosample.data.DataManager;

public class SampleZomatoApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        DataManager.init(getApplicationContext());
    }
}
