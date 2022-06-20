package com.college.portal.modules.clubs;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.college.portal.AppCompat;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubEventActivity extends AppCompat {

    private ImageView eventImage;
    private TextView eventTopic, eventMotive, eventPostDate, eventDate;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_event);

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

        eventTopic = findViewById(R.id.event_topic);
        eventMotive = findViewById(R.id.event_motive);
        eventPostDate = findViewById(R.id.event_post_date);
        eventDate = findViewById(R.id.event_date);
        eventImage = findViewById(R.id.event_image);

        getClubEventList(getIntent().getStringExtra(AppApi.CLUB_ID), "", getIntent().getStringExtra(AppApi.CLUB_EVENT_ID));
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


    private void getClubEventList(String clubId, String eventType, String eventId) {

        final ProgressDialogInterface progressDialog = new ProgressDialogInterface(
                ClubEventActivity.this,
                getString(R.string.progress_loading_message));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();


        Call<JsonObject> call = RetrofitClient.getInstance()
                .getRetroApi()
                .getClubEvents(eventId, eventType, clubId);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("Responsestring", response.body().toString());
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
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject jsonObject = dataArray.getJSONObject(i);

                    eventTopic.setText(jsonObject.getString("event_topic"));
                    eventMotive.setText(jsonObject.getString("event_motive"));
                    eventPostDate.setText(String.format(getString(R.string.event_post_date), jsonObject.getString("event_post_date")));

                    if (!jsonObject.getString("event_date").equals("null"))
                        eventDate.setText(String.format(getString(R.string.event_date), jsonObject.getString("event_date")));
                    else
                        eventDate.setVisibility(View.GONE);

                    if (!jsonObject.getString("event_image_path").equals("null"))
                        Picasso.get().load(RetroApi.EVENT_IMAGE + jsonObject.getString("event_image_path")).placeholder(R.drawable.place_holder).into(eventImage);
                    else
                        eventImage.setVisibility(View.GONE);

                    switch (jsonObject.getString("event_type")) {
                        case AppApi.CLUB_EVENT:
                            getSupportActionBar().setTitle(getString(R.string.events));
                            break;
                        case AppApi.CLUB_ANNOUNCEMENT:
                            getSupportActionBar().setTitle(getString(R.string.announcements));
                            break;
                        default:
                            getSupportActionBar().setTitle(getString(R.string.club));
                    }

                }

            } else {
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}