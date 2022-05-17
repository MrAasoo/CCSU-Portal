package com.college.portal.activity;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;

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

public class LibraryActivity extends AppCompatActivity {

    private TextView eBooks, blogs, lectureVideos, eMagazine, myCollegeLibrary;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

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

        // Library stuff
        eBooks = findViewById(R.id.library_ebook);
        blogs = findViewById(R.id.library_blogs);
        eMagazine = findViewById(R.id.library_e_magazine);
        lectureVideos = findViewById(R.id.library_lecture_videos);
        myCollegeLibrary = findViewById(R.id.library_my_library);

        eBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), getString(R.string.e_books), Toast.LENGTH_SHORT).show();
            }
        });

        blogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), getString(R.string.blogs), Toast.LENGTH_SHORT).show();
            }
        });

        eMagazine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), getString(R.string.magazine), Toast.LENGTH_SHORT).show();
            }
        });

        lectureVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), getString(R.string.lectures), Toast.LENGTH_SHORT).show();
            }
        });

        myCollegeLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), getString(R.string.my_library), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mInternetBroadcastReceiver, mIntentFilter);

        //AppTheme Theme
        AppTheme.setAppTheme(getApplicationContext());

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mInternetBroadcastReceiver);
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