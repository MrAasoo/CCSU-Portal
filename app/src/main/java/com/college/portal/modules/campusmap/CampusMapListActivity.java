package com.college.portal.modules.campusmap;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.college.portal.AlertDialogInterface;
import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.api.RetrofitClient;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.modules.campusmap.adapter.CampusMapListAdapter;
import com.college.portal.modules.campusmap.model.CampusMap;
import com.college.portal.services.NetworkServices;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampusMapListActivity extends AppCompatActivity {

    //Map list
    private RecyclerView recyclerView;
    private CampusMapListAdapter mAdapter;
    private List<CampusMap> mList;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_map_list);

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

        // get list
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mList = new ArrayList<>();
        mAdapter = new CampusMapListAdapter(CampusMapListActivity.this, mList);
        recyclerView.setAdapter(mAdapter);
        getMapList();
    }

    private void getMapList() {
        Call<JsonObject> call = RetrofitClient.getInstance()
                .getRetroApi()
                .getCampusMap();


        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("response_string", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //Log.e("onSuccess", response.body().toString());

                        String jsonResponse = response.body().toString();
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(jsonResponse);
                            if (obj.optBoolean("status")) {

                                JSONArray dataArray = obj.getJSONArray("data");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    CampusMap map = new CampusMap();
                                    JSONObject jsonObject = dataArray.getJSONObject(i);

                                    map.setSrNo(Integer.valueOf(jsonObject.getString("sr_no")));
                                    map.setLocationName(jsonObject.getString("location_name"));
                                    map.setLocationLatitude(jsonObject.getString("location_latitude"));
                                    map.setLocationLongitude(jsonObject.getString("location_longitude"));


                                    mList.add(map);

                                }
                                mAdapter.notifyDataSetChanged();

                            } else {
                                AlertDialogInterface dialog = new AlertDialogInterface(CampusMapListActivity.this,
                                        "No data found",
                                        "Please try again...",
                                        R.drawable.ic_alert);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.setCancelable(false);
                                dialog.create();
                                dialog.show();
                                dialog.dismissAlertDialog();
                            }
                        } catch (JSONException e) {
                            Log.e("catch", "writeRecycler: ", e);
                        }
                    } else {
                        Log.e("onEmptyResponse", "Returned empty response");
                        //Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("onFailure : ", "Something went wrong .....", t);
            }
        });
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