package com.college.portal.modules;


import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.college.portal.AppCompat;
import com.college.portal.AppTheme;
import com.college.portal.LocalManager;
import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.api.RetroApi;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.services.NetworkServices;
import com.college.portal.sharedpreferences.LanguagePrefManager;
import com.college.portal.sharedpreferences.ThemePrefManager;

public class SettingActivity extends AppCompat {

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    private final ThemePrefManager prefManager = new ThemePrefManager(SettingActivity.this);
    private final LanguagePrefManager langPrefManager = new LanguagePrefManager(SettingActivity.this);
    //Theme
    private RadioGroup themeGroup, langGroup;
    private RadioButton defaultBtn, darkBtn, lightBtn, english, hindi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.settings);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(AppApi.INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);

        // Application Theme
        themeGroup = findViewById(R.id.radio_group_theme);
        defaultBtn = findViewById(R.id.radio_default);
        darkBtn = findViewById(R.id.radio_dark);
        lightBtn = findViewById(R.id.radio_light);

        langGroup = findViewById(R.id.radio_group_language);
        english = findViewById(R.id.radio_english);
        hindi = findViewById(R.id.radio_hindi);

        updateRadioButtons();


        themeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_default:
                        prefManager.setDefault();
                        int systemUiThemeMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                        if (systemUiThemeMode == Configuration.UI_MODE_NIGHT_YES) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        } else if (systemUiThemeMode == Configuration.UI_MODE_NIGHT_NO) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }
                        break;
                    case R.id.radio_dark:
                        prefManager.setTheme(AppApi.MODE_DARK);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    case R.id.radio_light:
                        prefManager.setTheme(AppApi.MODE_LIGHT);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                }
            }
        });

        // Language
        langGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId) {
                    case R.id.radio_english:
                        new LanguagePrefManager(SettingActivity.this).SetLanguage(LanguagePrefManager.ENGLISH);
                        LocalManager.setNewLocale(getBaseContext(), LanguagePrefManager.ENGLISH);
                        recreate();
                        break;
                    case R.id.radio_hindi:
                        new LanguagePrefManager(SettingActivity.this).SetLanguage(LanguagePrefManager.HINDI);
                        LocalManager.setNewLocale(getBaseContext(), LanguagePrefManager.HINDI);
                        recreate();
                        break;
                }
            }
        });


        //terms and privacy
        TextView settingTerms = findViewById(R.id.setting_terms);
        TextView settingPrivacy = findViewById(R.id.setting_privacy);

        settingTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webTermsIntent = new Intent(SettingActivity.this, WebViewActivity.class);
                webTermsIntent.putExtra(AppApi.PAGE_URL, RetroApi.TERMS_URL);
                startActivity(webTermsIntent);
            }
        });

        settingPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webTermsIntent = new Intent(SettingActivity.this, WebViewActivity.class);
                webTermsIntent.putExtra(AppApi.PAGE_URL, RetroApi.PRIVACY_URL);
                startActivity(webTermsIntent);
            }
        });


        //AppSettings info
        TextView settingAbout = findViewById(R.id.setting_app_info);
        settingAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AppInfoActivity.class));
            }
        });

        //AppSettings feedback
        TextView settingFeedback = findViewById(R.id.setting_app_feedback);
        settingFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, FeedbackActivity.class));
            }
        });
    }

    private void updateRadioButtons() {

        //theme
        if (prefManager.getThemeMode() != null) {
            switch (prefManager.getThemeMode()) {
                case AppApi.MODE_DARK:
                    darkBtn.setChecked(true);
                    break;
                case AppApi.MODE_LIGHT:
                    lightBtn.setChecked(true);
                    break;
            }
        } else {
            defaultBtn.setChecked(true);
        }


        //language
        if (langPrefManager.getLanguage() != null) {
            switch (langPrefManager.getLanguage()) {
                case LanguagePrefManager.ENGLISH:
                    english.setChecked(true);
                    break;
                case LanguagePrefManager.HINDI:
                    hindi.setChecked(true);
                    break;
            }
        } else {
            english.setChecked(true);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mInternetBroadcastReceiver);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mInternetBroadcastReceiver, mIntentFilter);
        updateRadioButtons();

        //AppTheme Theme
        AppTheme.setAppTheme(getApplicationContext());
    }

    //For appbar back press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}