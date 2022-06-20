package com.college.portal.modules;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.college.portal.AppCompat;
import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.api.AppApi;

public class VisionMissionActivity extends AppCompat {

    // For system ui
    private View decorView;

    //Views
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_mission);
        decorView = getWindow().getDecorView();

        textView = findViewById(R.id.text_view);
        imageView = findViewById(R.id.image_view);


        switch (getIntent().getStringExtra(AppApi.ABOUT_US_ACTIVITY)) {
            case AppApi.MISSION:
                textView.setText(getString(R.string.our_mission_text));
                imageView.setImageResource(R.drawable.our_mission);
                break;

            case AppApi.VISION:
                textView.setText(getString(R.string.our_vision_text));
                imageView.setImageResource(R.drawable.our_vision);
                break;

            case AppApi.VALUES:
                textView.setText(getString(R.string.our_values_text));
                imageView.setImageResource(R.drawable.our_values);
                break;

            default:
                textView.setText(getString(R.string.error));
                imageView.setImageResource(R.drawable.no_internet);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
}