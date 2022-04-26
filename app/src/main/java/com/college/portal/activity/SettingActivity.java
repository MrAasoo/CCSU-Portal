package com.college.portal.activity;

import static com.college.portal.api.AppApi.MODE_DARK;
import static com.college.portal.api.AppApi.MODE_LIGHT;
import static com.college.portal.api.AppApi.PAGE_URL;
import static com.college.portal.api.RetroApi.BASE_URL;
import static com.college.portal.api.RetroApi.PRIVACY_URL;
import static com.college.portal.api.RetroApi.TERMS_URL;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.college.portal.R;
import com.college.portal.sharedpreferences.ThemePrefManager;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Application Theme
        RadioGroup themeGroup = findViewById(R.id.radio_group_theme);
        RadioButton defaultBtn = findViewById(R.id.radio_default);
        RadioButton darkBtn = findViewById(R.id.radio_dark);
        RadioButton lightBtn = findViewById(R.id.radio_light);

        ThemePrefManager prefManager = new ThemePrefManager(SettingActivity.this);

        if (prefManager.getThemeMode() != null) {
            switch (prefManager.getThemeMode()) {
                case MODE_DARK:
                    darkBtn.setChecked(true);
                    break;
                case MODE_LIGHT:
                    lightBtn.setChecked(true);
                    break;
            }
        } else {
            defaultBtn.setChecked(true);
            int systemUiThemeMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (systemUiThemeMode == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else if (systemUiThemeMode == Configuration.UI_MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }

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
                        prefManager.setTheme(MODE_DARK);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    case R.id.radio_light:
                        prefManager.setTheme(MODE_LIGHT);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
                webTermsIntent.putExtra(PAGE_URL, BASE_URL + TERMS_URL);
                startActivity(webTermsIntent);
            }
        });

        settingPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webTermsIntent = new Intent(SettingActivity.this, WebViewActivity.class);
                webTermsIntent.putExtra(PAGE_URL, BASE_URL + PRIVACY_URL);
                startActivity(webTermsIntent);
            }
        });


        //App info
        TextView settingAbout = findViewById(R.id.setting_app_info);
        settingAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AppInfoActivity.class));
            }
        });

        //App feedback
        TextView settingFeedback = findViewById(R.id.setting_app_feedback);
        settingFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : Feedback
                Toast.makeText(SettingActivity.this, "App Feedback", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //For appbar back press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }
}