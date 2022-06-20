package com.college.portal.modules.news_updates;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;
import static com.college.portal.api.AppApi.NEWS_ID;
import static com.college.portal.api.AppApi.NEWS_SLIDER_YES;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.college.portal.AppTheme;
import com.college.portal.ProgressDialogInterface;
import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.api.RetroApi;
import com.college.portal.api.RetrofitClient;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.services.NetworkServices;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    private ImageView newsImage;
    private TextView newsTitle, newsSubtitle, newsDetail, newsDate;
    private Button downloadBtn, openBtn;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

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

        int id = getIntent().getIntExtra(NEWS_ID, 0);

        newsImage = findViewById(R.id.news_image);
        newsTitle = findViewById(R.id.news_title);
        newsSubtitle = findViewById(R.id.news_subtitle);
        newsDetail = findViewById(R.id.news_detail);
        newsDate = findViewById(R.id.news_date);

        downloadBtn = findViewById(R.id.download_btn);
        openBtn = findViewById(R.id.open_btn);


        getNewsDetails(id);
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

    private void getNewsDetails(int id) {

        final ProgressDialogInterface progressDialog = new ProgressDialogInterface(
                NewsActivity.this,
                getString(R.string.progress_loading_message));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            progressDialog.create();
        progressDialog.show();


        Call<JsonObject> call = RetrofitClient.getInstance()
                .getRetroApi()
                .getNewsList(id, NEWS_SLIDER_YES);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                //Log.i("Response string", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        //Log.e("onSuccess", response.body().toString());

                        String jsonResponse = response.body().toString();
                        writeRecycler(jsonResponse);

                    } else {
                        Log.e("onEmptyResponse", "Returned empty response");
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
                JSONObject jsonObject = dataArray.getJSONObject(0);

                String title = jsonObject.getString("n_type");
                switch (title) {
                    case "0":
                        getSupportActionBar().setTitle(getString(R.string.news));
                        break;
                    case "1":
                        getSupportActionBar().setTitle(getString(R.string.notice));
                        break;
                    case "2":
                        getSupportActionBar().setTitle(getString(R.string.events));
                        break;
                    default:
                        getSupportActionBar().setTitle(getString(R.string.college_news));
                }


                newsTitle.setText(jsonObject.getString("n_title"));
                newsSubtitle.setText(jsonObject.getString("n_subtitle"));
                newsDetail.setText(jsonObject.getString("n_detail"));
                if (!jsonObject.getString("n_image").isEmpty()) {
                    Picasso.get().load(RetroApi.NEWS_IMAGES + jsonObject.getString("n_image")).placeholder(R.drawable.place_holder).into(newsImage);
                } else {
                    newsImage.setVisibility(View.GONE);
                }
                newsDate.setText(String.format(getString(R.string.date_place_holder), jsonObject.getString("n_date")));

                if (jsonObject.getString("n_path").equals("null")) {
                    downloadBtn.setVisibility(View.GONE);
                    openBtn.setVisibility(View.GONE);
                } else {
                    downloadOpenFile(jsonObject.getString("n_path"));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void downloadOpenFile(final String nPath) {

        File myFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + nPath);

        if (myFile.exists()) {
            downloadBtn.setVisibility(View.GONE);
            openBtn.setVisibility(View.VISIBLE);

            openBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (hasStoragePermission()) {
                        Uri uri = FileProvider.getUriForFile(NewsActivity.this, "com.college.portal" + ".provider", myFile);
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
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(RetroApi.NEWS_FILE_PATH + nPath + ""));

                        request.setTitle(nPath);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                                | DownloadManager.Request.NETWORK_MOBILE);
                        request.setMimeType("application/pdf");
                        request.allowScanningByMediaScanner();
                        request.setAllowedOverMetered(true);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nPath);

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
        ActivityCompat.requestPermissions(NewsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
    }

    private boolean hasStoragePermission() {
        return (ContextCompat.checkSelfPermission(NewsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(NewsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppApi.STORAGE_PERMISSION) {
            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Showing the toast message
                Toast.makeText(NewsActivity.this, "Phone Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(NewsActivity.this, "Phone Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
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