package com.college.portal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.college.portal.R;

public class AboutUsActivity extends AppCompatActivity {

    TextView tvMission, tvVision, tvHistory, tvPhotos, tvVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

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
                Toast.makeText(AboutUsActivity.this, "History Clicked", Toast.LENGTH_SHORT).show();
                //TODO : History.
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

}