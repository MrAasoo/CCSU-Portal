package com.college.portal.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.college.portal.R;
import com.college.portal.api.AppApi;

public class VisionMissionActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_mission);

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
}