package com.college.portal.modules.clubs;

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

import com.college.portal.AppTheme;
import com.college.portal.ProgressDialogInterface;
import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.api.RetrofitClient;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.modules.clubs.adapter.ClubMemberAdapter;
import com.college.portal.modules.clubs.adapter.ClubMemberAdminAdapter;
import com.college.portal.modules.clubs.model.Member;
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

public class ClubMemberListActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ClubMemberAdapter mAdapter;
    private ClubMemberAdminAdapter mAdminAdapter;
    private List<Member> mList;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    private boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_member_list);


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

        isAdmin = getIntent().getBooleanExtra(AppApi.IS_ADMIN, false);

        // Member List
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mList = new ArrayList<>();

        if (isAdmin) {
            mAdminAdapter = new ClubMemberAdminAdapter(ClubMemberListActivity.this, mList, getIntent().getStringExtra(AppApi.CLUB_ID));
            recyclerView.setAdapter(mAdminAdapter);
            getMemberList(getIntent().getStringExtra(AppApi.CLUB_ID), AppApi.CLUB_MEMBER_LIST_ADMIN);
        } else {
            mAdapter = new ClubMemberAdapter(ClubMemberListActivity.this, mList);
            recyclerView.setAdapter(mAdapter);
            getMemberList(getIntent().getStringExtra(AppApi.CLUB_ID), AppApi.CLUB_MEMBER_LIST);
        }


    }

    private void getMemberList(String clubId, int reqType) {

        final ProgressDialogInterface progressDialog = new ProgressDialogInterface(
                ClubMemberListActivity.this,
                getString(R.string.progress_loading_message));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();


        Call<JsonObject> call = RetrofitClient.getInstance()
                .getRetroApi()
                .getCollegeClubMember("0", clubId, reqType, "0");

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                //Log.i("Response string", response.body().toString());
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
                    Member member = new Member();
                    JSONObject jsonObject = dataArray.getJSONObject(i);

                    member.setMemberType(jsonObject.getString("member_type"));
                    member.setStdName(jsonObject.getString("std_name"));
                    member.setStdImage(jsonObject.getString("std_image"));
                    member.setBranchName(jsonObject.getString("branch_name"));
                    member.setJoinDate(jsonObject.getString("join_date"));

                    if (isAdmin) {
                        member.setSrNo(jsonObject.getString("sr_no"));
                        member.setStdId(jsonObject.getString("std_id"));
                        member.setMemberStatus(jsonObject.getString("member_status"));
                    }

                    mList.add(member);
                }
                if (isAdmin)
                    mAdminAdapter.notifyDataSetChanged();
                else
                    mAdapter.notifyDataSetChanged();

            } else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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