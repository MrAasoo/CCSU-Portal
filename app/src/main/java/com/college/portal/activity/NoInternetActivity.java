package com.college.portal.activity;

import static com.college.portal.services.NetworkServices.isNetworkConnected;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.college.portal.AlertDialogInterface;
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
                if (isNetworkConnected(NoInternetActivity.this))
                    finish();
                else {
                    AlertDialogInterface alertDialog = new AlertDialogInterface(NoInternetActivity.this,
                            getString(R.string.no_internet_title),
                            getString(R.string.no_internet_message),
                            R.drawable.ic_nointernet);
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog.setCancelable(false);
                    alertDialog.create();
                    alertDialog.show();
                    alertDialog.dismissAlertDialog();
                }
            }
        });

        btnOnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });

        btnOnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setClickable(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Intent i = new Intent(Settings.Panel.ACTION_WIFI);
                    startActivity(i);
                } else {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (wifiManager.isWifiEnabled()) {
                                Toast.makeText(NoInternetActivity.this, "Wifi is Enabled", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }
                    }, 2000);

                }
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