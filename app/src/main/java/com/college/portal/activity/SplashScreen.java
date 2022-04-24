package com.college.portal.activity;

import static com.college.portal.api.AppApi.ACCOUNT_MESSAGE;
import static com.college.portal.api.AppApi.ACCOUNT_STATUS;
import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;
import static com.college.portal.api.AppApi.STUDENT_ACCOUNT_BLOCKED;
import static com.college.portal.api.AppApi.STUDENT_ACCOUNT_NOT_VERIFIED;
import static com.college.portal.api.AppApi.STUDENT_ID_NOT_FOUND;
import static com.college.portal.api.AppApi.STUDENT_PASSWORD_DO_NOT_MATCH;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.college.portal.ProgressDialogInterface;
import com.college.portal.R;
import com.college.portal.api.RetrofitClient;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.model.LoginResponse;
import com.college.portal.model.StudentPref;
import com.college.portal.services.NetworkServices;
import com.college.portal.sharedpreferences.SharedPrefManager;
import com.college.portal.sharedpreferences.ThemePrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    // constants
    private static final int SPLASH_SCREEN_DELAY = 3000;


    // view objects
    Animation topAnim, bottomAnim, fadeInAnim;
    ImageView logoImage;
    TextView collegeText, phraseText;
    LinearLayout linearLayout;


    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

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


        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);


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


        if (NetworkServices.isNetworkConnected(SplashScreen.this)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SharedPrefManager instance = SharedPrefManager.getInstance(SplashScreen.this);
                    if (!instance.isLoggedIn())
                        toLoginActivity();
                }
            }, SPLASH_SCREEN_DELAY);

        } else {
            showNoInternetAlertDialog();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ThemePrefManager prefManager = new ThemePrefManager(SplashScreen.this);
        if (prefManager.getThemeMode() != null) {
            switch (prefManager.getThemeMode()) {
                case "dark":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
                case "light":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
            }
        } else {
            int systemUiThemeMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (systemUiThemeMode == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else if (systemUiThemeMode == Configuration.UI_MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }

        SharedPrefManager instance = SharedPrefManager.getInstance(SplashScreen.this);
        if (instance.isLoggedIn()) {
            StudentPref studentPref = instance.getStudentInfo();
            apiCallLogin(studentPref.getStdId(), studentPref.getStdPassword());
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(mInternetBroadcastReceiver, mIntentFilter);
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
    }


    private void showNoInternetAlertDialog() {
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


    private void toLoginActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(logoImage, "logoTransition"));
        startActivity(intent, options.toBundle());
    }

    // Login api call
    public void apiCallLogin(String std_id, String std_password) {
        //Show progress dialog
        final ProgressDialogInterface progressDialog = new ProgressDialogInterface(
                SplashScreen.this,
                getString(R.string.progress_signing_message));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();

        Call<LoginResponse> call = RetrofitClient.getInstance()
                .getRetroApi()
                .studentLogin(std_id, std_password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    //progress dialog dismiss
                    progressDialog.dismiss();

                    LoginResponse loginResponse = response.body();
                    //login success
                    if (!loginResponse.getError()) {
                        StudentPref std = loginResponse.getInfo();
                        std.setStdPassword(std_password);
                        if (std != null) {
                            SharedPrefManager.getInstance(SplashScreen.this).saveStudent(std);
                            toHomeActivity();
                        }
                    } else {
                        //login failed
                        switch (loginResponse.getCode()) {
                            case STUDENT_ID_NOT_FOUND:
                                showAlertDialog(getResources().getString(R.string.signing_failed_title), getResources().getString(R.string.id_not_found_message));
                                break;
                            case STUDENT_PASSWORD_DO_NOT_MATCH:
                                showAlertDialog(getResources().getString(R.string.signing_failed_title), getResources().getString(R.string.wrong_password_message));
                                break;
                            case STUDENT_ACCOUNT_NOT_VERIFIED:
                                toAccountStatus(STUDENT_ACCOUNT_NOT_VERIFIED, loginResponse.getMessage());
                                break;
                            case STUDENT_ACCOUNT_BLOCKED:
                                toAccountStatus(STUDENT_ACCOUNT_BLOCKED, loginResponse.getMessage());
                                break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //progress dialog dismiss
                progressDialog.dismiss();
                //alert dialog
                showAlertDialog(getResources().getString(R.string.signing_failed_title), getResources().getString(R.string.server_response_failed_message));
            }
        });
    }

    //Alert dialog
    private void showAlertDialog(String title, String message) {
        AlertDialogInterface dialog = new AlertDialogInterface(SplashScreen.this,
                title,
                message,
                R.drawable.ic_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.create();
        dialog.show();
        dialog.dismissAlertDialog();
    }


    //Start activity AccountStatus
    private void toAccountStatus(int status, String name) {
        Intent intent = new Intent(SplashScreen.this, AccountStatus.class);
        intent.putExtra(ACCOUNT_STATUS, status);
        intent.putExtra(ACCOUNT_MESSAGE, name);
        startActivity(intent);
    }

    // Start activity Home
    public void toHomeActivity() {
        Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}