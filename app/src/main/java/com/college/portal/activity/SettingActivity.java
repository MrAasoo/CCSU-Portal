package com.college.portal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.college.portal.R;
import com.college.portal.prefrences.ThemePrefManager;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        // Application Theme

        RadioGroup themeGroup = findViewById(R.id.radio_group_theme);
        RadioButton defaultBtn = findViewById(R.id.radio_default);
        RadioButton darkBtn = findViewById(R.id.radio_dark);
        RadioButton lightBtn = findViewById(R.id.radio_light);

        ThemePrefManager prefManager = new ThemePrefManager(SettingActivity.this);

        if(prefManager.getThemeMode() != null){
            switch(prefManager.getThemeMode()){
                case "dark":
                    darkBtn.setChecked(true);
                    break;
                case "light":
                    lightBtn.setChecked(true);
                    break;
            }
        }else{
            defaultBtn.setChecked(true);
            int systemUiThemeMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if(systemUiThemeMode == Configuration.UI_MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else if(systemUiThemeMode == Configuration.UI_MODE_NIGHT_NO){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }

        themeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_default:
                        prefManager.setDefault();
                        int systemUiThemeMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                        if(systemUiThemeMode == Configuration.UI_MODE_NIGHT_YES){
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }else if(systemUiThemeMode == Configuration.UI_MODE_NIGHT_NO){
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }
                        break;
                    case R.id.radio_dark:
                        prefManager.setTheme("dark");
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    case R.id.radio_light:
                        prefManager.setTheme("light");
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                }
            }
        });



        TextView settingAbout = findViewById(R.id.setting_app_info);
        settingAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AppInfoActivity.class));
            }
        });
    }


}