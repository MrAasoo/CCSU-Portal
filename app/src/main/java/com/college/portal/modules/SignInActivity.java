package com.college.portal.modules;

import static com.college.portal.api.AppApi.ACCOUNT_MESSAGE;
import static com.college.portal.api.AppApi.ACCOUNT_STATUS;
import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;
import static com.college.portal.api.AppApi.PAGE_URL;
import static com.college.portal.api.AppApi.STUDENT_ACCOUNT_BLOCKED;
import static com.college.portal.api.AppApi.STUDENT_ACCOUNT_NOT_VERIFIED;
import static com.college.portal.api.AppApi.STUDENT_ID_NOT_FOUND;
import static com.college.portal.api.AppApi.STUDENT_PASSWORD_DO_NOT_MATCH;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.college.portal.AlertDialogInterface;
import com.college.portal.AppCompat;
import com.college.portal.AppTheme;
import com.college.portal.ProgressDialogInterface;
import com.college.portal.R;
import com.college.portal.api.RetroApi;
import com.college.portal.api.RetrofitClient;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.modules.model.LoginResponse;
import com.college.portal.modules.model.StudentPref;
import com.college.portal.services.NetworkServices;
import com.college.portal.sharedpreferences.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInActivity extends AppCompat {


    //view objects
    private EditText stdId, stdPassword;
    private Button signInBtn;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);

        //Layouts
        LinearLayout loginHolder = findViewById(R.id.login_holder);
        LinearLayout loginStuff = findViewById(R.id.login_stuff);

        stdId = findViewById(R.id.std_id);
        stdPassword = findViewById(R.id.std_password);
        signInBtn = findViewById(R.id.signIn);

        //Sign In Button
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInClick();
            }
        });

        //Forgot Password
        TextView forgotPassBtn = findViewById(R.id.forgot_password);
        forgotPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onForgotPasswordClick();
            }
        });


        //Animation
        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);


        //Set Animation
        loginHolder.setAnimation(bottomAnim);
        loginStuff.setAnimation(bottomAnim);

        //terms and privacy
        TextView settingTerms = findViewById(R.id.sign_in_terms);
        TextView settingPrivacy = findViewById(R.id.sign_in_policy);


        settingTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webTermsIntent = new Intent(SignInActivity.this, WebViewActivity.class);
                webTermsIntent.putExtra(PAGE_URL, RetroApi.TERMS_URL);
                startActivity(webTermsIntent);
            }
        });

        settingPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webTermsIntent = new Intent(SignInActivity.this, WebViewActivity.class);
                webTermsIntent.putExtra(PAGE_URL, RetroApi.PRIVACY_URL);
                startActivity(webTermsIntent);
            }
        });

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

        //AppTheme Theme
        AppTheme.setAppTheme(getApplicationContext());
    }


    // On Sign in button click
    private void onSignInClick() {
        //set button clickable false
        signInBtn.setClickable(false);
        validateLogin();
    }


    // On Forgot password clicked
    private void onForgotPasswordClick() {
        showAlertDialog(getString(R.string.forgot_password), getString(R.string.forgot_password_message));
    }


    //login validation
    private void validateLogin() {
        String std_id, std_password;
        std_id = stdId.getText().toString().trim();
        std_password = stdPassword.getText().toString().trim();

        if (std_id.isEmpty() || std_id.length() < 4) {
            Toast.makeText(SignInActivity.this, "Enter your student id.", Toast.LENGTH_SHORT).show();
            stdId.setError("Invalid ID");
            stdId.requestFocus();
            //Set Button clickable for next time
            signInBtn.setClickable(true);
            return;
        }
        if (std_password.isEmpty() || std_password.length() < 6) {
            Toast.makeText(SignInActivity.this, "Enter valid password", Toast.LENGTH_SHORT).show();
            stdPassword.setError("Invalid Password");
            stdPassword.requestFocus();
            //Set Button clickable for next time
            signInBtn.setClickable(true);
            return;
        }
        //api call
        apiCallLogin(std_id, std_password);

        //Set Button clickable for next time
        signInBtn.setClickable(true);
    }

    // Login api call
    public void apiCallLogin(String std_id, String std_password) {

        //Show progress dialog
        final ProgressDialogInterface progressDialog = new ProgressDialogInterface(
                SignInActivity.this,
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
                            SharedPrefManager.getInstance(SignInActivity.this).saveStudent(std);
                            toHomeActivity();
                        }
                    } else {    //login failed
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
        AlertDialogInterface dialog = new AlertDialogInterface(SignInActivity.this,
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
        Intent intent = new Intent(SignInActivity.this, AccountStatus.class);
        intent.putExtra(ACCOUNT_STATUS, status);
        intent.putExtra(ACCOUNT_MESSAGE, name);
        startActivity(intent);

    }


    // Start activity Home
    public void toHomeActivity() {
        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}