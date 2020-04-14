package com.sample.zomatosample.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    public static final String ACCESS_TOKEN = "access_token";
    private static PreferenceManager INSTANCE = null;

    private PreferenceManager() {
    }

    public static PreferenceManager getInstance() {
        if (INSTANCE == null) {
            return new PreferenceManager();
        } else {
            return INSTANCE;
        }
    }

    private SharedPreferences getSharedPreferences(Context context) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getInt(Context context, String key) {
        return getSharedPreferences(context).getInt(key, 0);
    }

    public void putInt(Context context, String key, int value) {
        getSharedPreferences(context).edit().putLong(key, value).apply();
    }

    public String getString(Context context, String key) {
        return getSharedPreferences(context).getString(key, null);
    }

    public void putString(Context context, String key, String value) {
        getSharedPreferences(context).edit().putString(key, value).apply();
    }

    public boolean getBoolean(Context context, String key) {
        return getSharedPreferences(context).getBoolean(key, false);
    }

    public void putBoolean(Context context, String key, boolean value) {
        getSharedPreferences(context).edit().putBoolean(key, value).apply();
    }

    public void clearAllPrefs(Context context) {
        getSharedPreferences(context).edit().clear().apply();
    }

}
