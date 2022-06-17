package com.college.portal.modules;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.college.portal.AlertDialogInterface;
import com.college.portal.AppTheme;
import com.college.portal.ProgressDialogInterface;
import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.api.RetrofitClient;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.modules.model.StudentPref;
import com.college.portal.services.NetworkServices;
import com.college.portal.sharedpreferences.SharedPrefManager;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity {

    //Cons
    private static final int ACCOUNT_SIGN_IN_FAIL = 2;
    private static final int FEEDBACK_SUBMITTED = 1;
    private static final int FEEDBACK_SUBMISSION_FAILED = 0;


    // Views
    private Button btnSubmit;
    private EditText feedbackMessage;
    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);

        feedbackMessage = findViewById(R.id.feedback_message);
        btnSubmit = findViewById(R.id.submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!messageIsEmpty()) {
                    SharedPrefManager prefManager = new SharedPrefManager(FeedbackActivity.this);
                    StudentPref studentPref = prefManager.getStudentInfo();
                    uploadFeedbackMessage(studentPref.getStdId(), studentPref.getStdPassword(), feedbackMessage.getText().toString().trim());
                } else {
                    Toast.makeText(FeedbackActivity.this, "Feedback Can't be sent empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void uploadFeedbackMessage(String stdId, String stdPassword, String feedbackMessage) {
        final ProgressDialogInterface progressDialog = new ProgressDialogInterface(
                FeedbackActivity.this,
                getString(R.string.progress_loading_message));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();


        Call<JsonObject> call = RetrofitClient.getInstance()
                .getRetroApi()
                .feedback(stdId, stdPassword, feedbackMessage);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        //Log.i("onSuccess", response.body().toString());
                        String jsonResponse = response.body().toString();
                        try {
                            JSONObject jsonObject = new JSONObject(jsonResponse);
                            if (jsonObject.optBoolean(AppApi.STATUS)) {
                                // Feedback -> SUBMITTED
                                if (jsonObject.optInt("message") == FEEDBACK_SUBMITTED) {
                                    AlertDialogInterface dialog = new AlertDialogInterface(FeedbackActivity.this,
                                            "Feedback",
                                            "Feedback submitted successfully...",
                                            android.R.drawable.stat_sys_upload_done);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog.setCancelable(false);
                                    dialog.create();
                                    dialog.show();
                                    dialog.dismissAlertDialog();
                                }
                            } else {
                                String title = "";
                                String message = "";
                                int icon = R.drawable.ic_app_icon;
                                switch (jsonObject.optInt("message")) {
                                    //Feedback -> NOT SUBMITTED
                                    case FEEDBACK_SUBMISSION_FAILED:
                                        title = "Feedback not submitted";
                                        message = "Feedback submission failed... Try again later.";
                                        icon = android.R.drawable.ic_dialog_alert;
                                        break;

                                    // Feedback -> AUTH FAIL
                                    case ACCOUNT_SIGN_IN_FAIL:

                                        title = "Feedback not submitted";
                                        message = "Something went wrong... Try again later";
                                        icon = android.R.drawable.ic_dialog_info;
                                        break;
                                }

                                AlertDialogInterface dialog = new AlertDialogInterface(FeedbackActivity.this, title, message, icon);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.setCancelable(false);
                                dialog.create();
                                dialog.show();
                                dialog.dismissAlertDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(FeedbackActivity.this, "Unable to connect : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean messageIsEmpty() {
        return feedbackMessage.getText().toString().trim().isEmpty();
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

    //For appbar back press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}