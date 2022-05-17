package com.college.portal.activity;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;
import static com.college.portal.api.AppApi.PAGE_URL;
import static com.college.portal.api.RetroApi.BASE_URL;
import static com.college.portal.api.RetroApi.HISTORY_URL;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.services.NetworkServices;

public class AboutUsActivity extends AppCompatActivity {

    private TextView tvMission, tvVision, tvHistory, tvPhotos, tvVideos;

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
        tvHistory = findViewById(R.id.about_history);
        tvPhotos = findViewById(R.id.about_photo);
        tvVideos = findViewById(R.id.about_video);

        //Click listeners for text views
        tvMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutUsActivity.this, "Our Mission Clicked", Toast.LENGTH_SHORT).show();
                //TODO : Our mission.
            }
        });

        tvVision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutUsActivity.this, "Our Vision Clicked", Toast.LENGTH_SHORT).show();
                //TODO : Our vision.
            }
        });

        tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webTermsIntent = new Intent(AboutUsActivity.this, WebViewActivity.class);
                webTermsIntent.putExtra(PAGE_URL, BASE_URL + HISTORY_URL);
                startActivity(webTermsIntent);
            }
        });


        tvPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutUsActivity.this, "Photos Clicked", Toast.LENGTH_SHORT).show();
                //TODO : Photos.
            }
        });


        tvVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutUsActivity.this, "Videos Clicked", Toast.LENGTH_SHORT).show();
                //TODO : Videos.
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