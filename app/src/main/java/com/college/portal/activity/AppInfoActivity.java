package com.college.portal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.college.portal.BuildConfig;
import com.college.portal.R;

public class AppInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        // Views
        TextView versionInfo = findViewById(R.id.about_version);
        versionInfo.setText(BuildConfig.VERSION_NAME);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}