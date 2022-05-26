package com.college.portal.activity;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.services.NetworkServices;

public class CollegeGalleryVideoActivity extends AppCompatActivity {

    // For System ui
    private View decorView;

    // Views
    private TextView mediaTitle, postDate;
    private VideoView videoView;
    private LinearLayout contentHolder;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_gallery_video);
        decorView = getWindow().getDecorView();

        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);

        // Views
        videoView = findViewById(R.id.gallery_video);
        mediaTitle = findViewById(R.id.media_title);
        postDate = findViewById(R.id.post_date);
        contentHolder = findViewById(R.id.content_holder);

        mediaTitle.setText(getIntent().getStringExtra(AppApi.MEDIA_DESCRIPTION));
        postDate.setText(String.format(getString(R.string.post_date_placeholder), getIntent().getStringExtra(AppApi.MEDIA_POST)));
        videoView.setVideoURI(Uri.parse(getIntent().getStringExtra(AppApi.MEDIA_PATH)));

        MediaController mediaController = new MediaController(CollegeGalleryVideoActivity.this) {
            @Override
            public void show() {
                super.show();
                contentHolder.setVisibility(VISIBLE);
            }

            @Override
            public void hide() {
                super.hide();
                contentHolder.setVisibility(GONE);
            }

            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getKeyCode() == KeyEvent.ACTION_UP)
                    ((Activity) getContext()).onBackPressed();

                return super.dispatchKeyEvent(event);
            }
        };

        //Back press for Pie+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mediaController.addOnUnhandledKeyEventListener((v, event) -> {
                //Handle BACK button
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
                    this.onBackPressed();

                return true;
            });
        }

        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                contentHolder.setVisibility(View.GONE);
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                contentHolder.setVisibility(View.VISIBLE);
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


    //For hiding system ui
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemUI());
        }
    }

    public int hideSystemUI() {
        return View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}