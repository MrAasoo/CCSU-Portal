package com.college.portal.modules;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;
import static com.college.portal.api.AppApi.PAGE_URL;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.college.portal.AppCompat;
import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.api.RetroApi;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.services.NetworkServices;

public class AboutUsActivity extends AppCompat {

    private TextView tvMission, tvVision, tvValues, tvHistory;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.about_us));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);

        tvMission = findViewById(R.id.about_mission);
        tvVision = findViewById(R.id.about_vision);
        tvValues = findViewById(R.id.about_values);
        tvHistory = findViewById(R.id.about_history);

        tvMission.setText(getString(R.string.our_mission));
        tvVision.setText(getString(R.string.our_vision));
        tvValues.setText(getString(R.string.our_values));
        tvHistory.setText(getString(R.string.history));

        //Click listeners for text views
        tvMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this, VisionMissionActivity.class);
                intent.putExtra(AppApi.ABOUT_US_ACTIVITY, AppApi.MISSION);
                startActivity(intent);
            }
        });

        tvVision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this, VisionMissionActivity.class);
                intent.putExtra(AppApi.ABOUT_US_ACTIVITY, AppApi.VISION);
                startActivity(intent);
            }
        });

        tvValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this, VisionMissionActivity.class);
                intent.putExtra(AppApi.ABOUT_US_ACTIVITY, AppApi.VALUES);
                startActivity(intent);
            }
        });

        tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(AboutUsActivity.this, WebViewActivity.class);
                webIntent.putExtra(PAGE_URL, RetroApi.HISTORY_URL);
                startActivity(webIntent);
            }
        });

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