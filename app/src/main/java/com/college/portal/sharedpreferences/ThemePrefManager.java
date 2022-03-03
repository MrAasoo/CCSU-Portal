package com.college.portal.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemePrefManager {

    private static final String SHARED_PREF_NAME = "app_theme";
    private static final String APP_THEME_MODE = "app_theme_mode";
    private static ThemePrefManager mInstance;
    private Context mContext;

    public ThemePrefManager(Context context){
        this.mContext = context;
    }

    public static synchronized ThemePrefManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new ThemePrefManager(context);
        }
        return mInstance;
    }

    public String getThemeMode(){
        SharedPreferences preferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(APP_THEME_MODE, null);
    }

    public void setTheme(String mode){
        SharedPreferences preferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(APP_THEME_MODE, mode);
        editor.apply();
    }

    public void setDefault(){
        SharedPreferences preferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }



}
