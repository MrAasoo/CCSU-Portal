package com.college.portal.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class LanguagePrefManager {

    public static final String ENGLISH = "en";
    public static final String HINDI = "hi";

    private static final String LANGUAGE_PREF_NAME = "app_language";
    private static final String LANGUAGE_NAME = "language";
    private static LanguagePrefManager mInstance;
    private final Context mContext;

    public LanguagePrefManager(Context mContext) {
        this.mContext = mContext;
    }

    public static synchronized LanguagePrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LanguagePrefManager(context);
        }
        return mInstance;
    }

    public String getLanguage() {
        SharedPreferences preferences = mContext.getSharedPreferences(LANGUAGE_PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(LANGUAGE_NAME, "en");
    }

    public void SetLanguage(String lang) {
        SharedPreferences preferences = mContext.getSharedPreferences(LANGUAGE_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LANGUAGE_NAME, lang);
        editor.apply();
    }

    public void setDefault() {
        SharedPreferences preferences = mContext.getSharedPreferences(LANGUAGE_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
