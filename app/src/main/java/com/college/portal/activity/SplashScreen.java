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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.college.portal.AlertDialogInterface;
import com.college.portal.AppTheme;
import com.college.portal.ProgressDialogInterface;
import com.college.portal.R;
import com.college.portal.api.RetrofitClient;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.model.LoginResponse;
import com.college.portal.model.StudentPref;
import com.college.portal.services.NetworkServices;
import com.college.portal.sharedpreferences.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    // constants
    private static final int SPLASH_SCREEN_DELAY = 3000;
    private static final int SPLASH_SCREEN_RESTART_DELAY = 1500;

    // For System ui
    private View decoderView;

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
        decoderView = getWindow().getDecorView();
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


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (NetworkServices.isNetworkConnected(SplashScreen.this)) {
                    SharedPrefManager instance = SharedPrefManager.getInstance(SplashScreen.this);
                    if (instance.isLoggedIn()) {
                        StudentPref studentPref = instance.getStudentInfo();
                        apiCallLogin(studentPref.getStdId(), studentPref.getStdPassword());
                    } else {
                        toLoginActivity();
                    }
                }
            }
        }, SPLASH_SCREEN_DELAY);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(mInternetBroadcastReceiver, mIntentFilter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (NetworkServices.isNetworkConnected(SplashScreen.this)) {
                    SharedPrefManager instance = SharedPrefManager.getInstance(SplashScreen.this);
                    if (instance.isLoggedIn()) {
                        StudentPref studentPref = instance.getStudentInfo();
                        apiCallLogin(studentPref.getStdId(), studentPref.getStdPassword());
                    } else {
                        toLoginActivity();
                    }
                }
            }
        }, SPLASH_SCREEN_RESTART_DELAY);
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
            decoderView.setSystemUiVisibility(hideSystemUI());
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

    private void toLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
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
                        toHomeActivity();
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