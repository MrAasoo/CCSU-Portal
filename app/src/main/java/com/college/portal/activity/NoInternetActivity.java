package com.college.portal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.college.portal.AppTheme;
import com.college.portal.R;

public class NoInternetActivity extends AppCompatActivity {

    Button btnTryAgain, btnOnData, btnOnWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        btnTryAgain = findViewById(R.id.try_again);
        btnOnData = findViewById(R.id.on_data);
        btnOnWifi = findViewById(R.id.on_wifi);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        // AppTheme Theme
        AppTheme.setAppTheme(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}