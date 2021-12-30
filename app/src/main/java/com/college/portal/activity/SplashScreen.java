package com.college.portal.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.college.portal.AlertDialogInterface;
import com.college.portal.R;
import com.college.portal.prefrences.ThemePrefManager;

public class SplashScreen extends AppCompatActivity {

    // constants
    private static final int SPLASH_SCREEN_DELAY = 3000;


    // view objects

    Animation topAnim, bottomAnim, fadeInAnim;
    ImageView logoImage;
    TextView collegeText, phraseText;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        //view and layout

        logoImage = findViewById(R.id.logo_image);
        collegeText = findViewById(R.id.collage_text);
        phraseText = findViewById(R.id.phrase_text);
        linearLayout = findViewById(R.id.linearLayout);


        //Animation
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);


        //Set Animation

        logoImage.setAnimation(topAnim);
        linearLayout.setAnimation(bottomAnim);
        collegeText.setAnimation(fadeInAnim);
        phraseText.setAnimation(fadeInAnim);

        //checking internet state
        if (isConnected()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Open Activity SignIn
                    toLoginActivity();
                }
            }, SPLASH_SCREEN_DELAY);

        }else {
            AlertDialogInterface dialog = new AlertDialogInterface(SplashScreen.this,
                    getString(R.string.no_internet_title),
                    getString(R.string.no_internet_message),
                    R.drawable.ic_nointernet);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.setCancelable(false);
            dialog.create();
            dialog.show();
            Button btnOk = dialog.findViewById(R.id.dialog_right_button);
            Button btnExit = dialog.findViewById(R.id.dialog_left_button);

            btnOk.setVisibility(View.VISIBLE);
            btnOk.setText(R.string.ok_text);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    startActivity(new Intent(SplashScreen.this, SplashScreen.class));
                    finish();
                }
            });

            btnExit.setVisibility(View.VISIBLE);
            btnExit.setText(R.string.exit_text);
            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    finish();
                }
            });


        }
    }


    private boolean isConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void toLoginActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(logoImage, "logoTransition"));
        startActivity(intent, options.toBundle());
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();

        ThemePrefManager prefManager = new ThemePrefManager(SplashScreen.this);

        if(prefManager.getThemeMode() != null){
            switch(prefManager.getThemeMode()){
                case "dark":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
                case "light":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
            }
        }else{
            int systemUiThemeMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if(systemUiThemeMode == Configuration.UI_MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else if(systemUiThemeMode == Configuration.UI_MODE_NIGHT_NO){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }
}