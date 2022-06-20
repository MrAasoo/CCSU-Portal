package com.college.portal.modules.classroom;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.college.portal.AppCompat;
import com.college.portal.AppTheme;
import com.college.portal.ProgressDialogInterface;
import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.api.RetroApi;
import com.college.portal.api.RetrofitClient;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.modules.SignInActivity;
import com.college.portal.modules.classroom.module.NoticeBoard;
import com.college.portal.modules.model.StudentPref;
import com.college.portal.services.NetworkServices;
import com.college.portal.sharedpreferences.SharedPrefManager;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeBoardActivity extends AppCompat {

    private TextView noticeTitle, noticeMessage, noticeDate;
    private Button downloadBtn, openBtn;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

        //Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.notice_board));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);


        noticeTitle = findViewById(R.id.notice_title);
        noticeMessage = findViewById(R.id.notice_message);
        noticeDate = findViewById(R.id.notice_date);

        downloadBtn = findViewById(R.id.download_btn);
        openBtn = findViewById(R.id.open_btn);

        SharedPrefManager instance = SharedPrefManager.getInstance(NoticeBoardActivity.this);
        if (instance.isLoggedIn()) {
            StudentPref studentPref = instance.getStudentInfo();
            getNoticeBoard(studentPref.getStdDepartmentId(), studentPref.getStdBranchId(), getIntent().getStringExtra(AppApi.NOTICE_ID));
        } else {
            toLoginActivity();
        }


    }


    private void toLoginActivity() {
        Intent intent = new Intent(NoticeBoardActivity.this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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

    private void getNoticeBoard(String departmentId, String branchId, String noticeId) {

        final ProgressDialogInterface progressDialog = new ProgressDialogInterface(
                NoticeBoardActivity.this,
                getString(R.string.progress_loading_message));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();


        Call<JsonObject> call = RetrofitClient.getInstance()
                .getRetroApi()
                .getNoticeBoard(noticeId, departmentId, branchId);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                //Log.i("Response String", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        //Log.i("onSuccess", response.body().toString());

                        String jsonResponse = response.body().toString();
                        writeRecycler(jsonResponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
                        //Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("onFailure : ", "Something went wrong .....", t);
                progressDialog.dismiss();
            }
        });
    }

    private void writeRecycler(String jsonResponse) {
        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(jsonResponse);
            if (obj.optBoolean("status")) {

                JSONArray dataArray = obj.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    NoticeBoard notice = new NoticeBoard();
                    JSONObject jsonObject = dataArray.getJSONObject(i);

                    if (!jsonObject.getString("notice_id").equals("null")) {

                        noticeTitle.setText(jsonObject.getString("notice_title"));
                        noticeMessage.setText(jsonObject.getString("notice_message"));
                        noticeDate.setText(String.format(getString(R.string.date_place_holder), jsonObject.getString("notice_date")));

                        if (jsonObject.getString("file_path").equals("null")) {
                            downloadBtn.setVisibility(View.GONE);
                            openBtn.setVisibility(View.GONE);
                        } else {
                            downloadOpenFile(jsonObject.getString("file_path"));
                        }

                    } else {
                        Toast.makeText(this, "No Assignments found.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void downloadOpenFile(final String filePath) {

        File myFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + filePath);

        if (myFile.exists()) {
            downloadBtn.setVisibility(View.GONE);
            openBtn.setVisibility(View.VISIBLE);

            openBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (hasStoragePermission()) {
                        Uri uri = FileProvider.getUriForFile(NoticeBoardActivity.this, "com.college.portal" + ".provider", myFile);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, "application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);
                    } else {
                        requestStoragePermission(AppApi.STORAGE_PERMISSION);
                    }
                }
            });
        } else {
            openBtn.setVisibility(View.GONE);
            downloadBtn.setVisibility(View.VISIBLE);
            downloadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (hasStoragePermission()) {
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(RetroApi.NOTICEBOARD_FILE_PATH + filePath + ""));
                        //Log.i("URI", "onClick: " + Uri.parse(RetroApi.ASSIGNMENT_FILE_PATH + assi_path + ""));
                        request.setTitle(filePath);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                                | DownloadManager.Request.NETWORK_MOBILE);
                        request.setMimeType("application/pdf");
                        request.allowScanningByMediaScanner();
                        request.setAllowedOverMetered(true);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filePath);

                        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                        dm.enqueue(request);
                    } else {
                        requestStoragePermission(AppApi.STORAGE_PERMISSION);
                    }
                }
            });
        }
    }

    private void requestStoragePermission(int requestCode) {
        ActivityCompat.requestPermissions(NoticeBoardActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
    }

    private boolean hasStoragePermission() {
        return (ContextCompat.checkSelfPermission(NoticeBoardActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(NoticeBoardActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppApi.STORAGE_PERMISSION) {
            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Showing the toast message
                Toast.makeText(NoticeBoardActivity.this, "Phone Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(NoticeBoardActivity.this, "Phone Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}