package com.college.portal.modules.clubs;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.college.portal.AppCompat;
import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.api.RetroApi;
import com.college.portal.api.RetrofitClient;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.modules.SignInActivity;
import com.college.portal.modules.model.StudentPref;
import com.college.portal.services.NetworkServices;
import com.college.portal.sharedpreferences.SharedPrefManager;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubPageActivity extends AppCompat {

    private TextView clubMotive, clubStartDate, totalMembers, joinDate;
    private TextView clubEvents, clubMembers, clubAnnouncements, clubMessages;
    private ImageView clubLogo, clubBgImage;
    private LinearLayout isNotMember, isMember;
    private Button joinNow, cancelRequest, leaveClub;
    private String stdId = "0", clubId = "0", srNo = "0", clubKey, clubName;
    private boolean isAdmin = false;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_page);

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

        // Views
        initViews();

        // Click Events
        initClickListener();

        SharedPrefManager instance = SharedPrefManager.getInstance(ClubPageActivity.this);
        if (instance.isLoggedIn()) {
            StudentPref studentPref = instance.getStudentInfo();
            stdId = studentPref.getStdId();
        } else {
            Intent intent = new Intent(ClubPageActivity.this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        clubId = getIntent().getStringExtra(AppApi.CLUB_ID);

        getClubInfo(stdId, clubId, AppApi.GET_CLUB_DATA);
        getClubInfo(stdId, clubId, AppApi.IS_STUDENT_CLUB_MEMBER);


    }

    private void initViews() {
        clubMotive = findViewById(R.id.club_motive);
        clubStartDate = findViewById(R.id.club_started);
        totalMembers = findViewById(R.id.total_members);
        isMember = findViewById(R.id.is_member);
        isNotMember = findViewById(R.id.is_not_member);

        clubLogo = findViewById(R.id.std_image);
        clubBgImage = findViewById(R.id.club_background);
        joinDate = findViewById(R.id.join_date);


        // Buttons
        leaveClub = findViewById(R.id.leave_club);
        joinNow = findViewById(R.id.join_now);
        cancelRequest = findViewById(R.id.cancel_request);

        //is members tv
        clubEvents = findViewById(R.id.club_events);
        clubMembers = findViewById(R.id.club_members);
        clubAnnouncements = findViewById(R.id.club_announcements);
        clubMessages = findViewById(R.id.club_messages);

    }

    private void initClickListener() {

        joinNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ClubPageActivity.this);
                builder.setIcon(R.drawable.ic_app_icon)
                        .setTitle(getString(R.string.join_club) + "!")
                        .setMessage("Do you want to join club?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                clubMemberRequest(stdId, clubId, AppApi.JOIN_CLUB, srNo);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        cancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ClubPageActivity.this);
                builder.setIcon(R.drawable.ic_app_icon)
                        .setTitle(getString(R.string.cancel_request) + "!")
                        .setMessage("Are you sure to want cancel request?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                clubMemberRequest(stdId, clubId, AppApi.CANCEL_REQ, srNo);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        leaveClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ClubPageActivity.this);
                builder.setIcon(R.drawable.ic_app_icon)
                        .setTitle(getString(R.string.leave) + "!")
                        .setMessage("Are you sure to want leave club?")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                clubMemberRequest(stdId, clubId, AppApi.LEAVE_CLUB, srNo);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        });

        clubEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClubPageActivity.this, ClubEventListActivity.class);
                intent.putExtra(AppApi.CLUB_REQ, AppApi.CLUB_EVENT);
                intent.putExtra(AppApi.CLUB_ID, clubId);
                startActivity(intent);
            }
        });

        clubAnnouncements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClubPageActivity.this, ClubEventListActivity.class);
                intent.putExtra(AppApi.CLUB_REQ, AppApi.CLUB_ANNOUNCEMENT);
                intent.putExtra(AppApi.CLUB_ID, clubId);
                startActivity(intent);
            }
        });

        clubMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClubPageActivity.this, ClubMemberListActivity.class);
                intent.putExtra(AppApi.IS_ADMIN, isAdmin);
                intent.putExtra(AppApi.CLUB_ID, clubId);
                startActivity(intent);
            }
        });

        clubMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClubPageActivity.this, ClubMessagesActivity.class);
                intent.putExtra(AppApi.CLUB_KEY, clubKey);
                intent.putExtra(AppApi.CLUB_NAME, clubName);
                startActivity(intent);
            }
        });


    }

    private void clubMemberRequest(String stdId, String clubId, int req, String srNo) {
        Call<JsonObject> call = RetrofitClient.getInstance().getRetroApi().getCollegeClubMember(stdId, clubId, req, srNo);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String jsonResponse = response.body().toString();
                        try {
                            JSONObject obj = new JSONObject(jsonResponse);
                            if (obj.optBoolean("status")) {
                                getClubInfo(stdId, clubId, AppApi.IS_STUDENT_CLUB_MEMBER);
                            } else {
                                Toast.makeText(ClubPageActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Log.e("ClubPageActivity", "onResponse: ", e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("onFailure : ", "Something went wrong .....", t);
            }
        });
    }

    private void getClubInfo(String stdId, String clubId, String reqType) {

        Call<JsonObject> call = RetrofitClient.getInstance()
                .getRetroApi()
                .getCollegeClubInfo(stdId, clubId, reqType);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                //Log.i("Response String", response.body().toString());

                if (response.isSuccessful()) {
                    if (response.body() != null) {


                        //Log.i("onSuccess", response.body().toString());

                        String jsonResponse = response.body().toString();
                        switch (reqType) {
                            case AppApi.GET_CLUB_DATA:
                                writeClubInfo(jsonResponse);
                                break;
                            case AppApi.IS_STUDENT_CLUB_MEMBER:
                                isStudentClubMember(jsonResponse);
                                break;
                            case AppApi.GET_STUDENT_CLUB_STATUS:
                                getStudentStatus(jsonResponse);
                                break;
                        }

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
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

    private void getStudentStatus(String jsonResponse) {
        Log.i("onSuccess", jsonResponse);
        try {
            JSONObject obj = new JSONObject(jsonResponse);
            if (obj.optBoolean("status")) {
                JSONArray dataArray = obj.getJSONArray("data");
                JSONObject jsonObject = dataArray.getJSONObject(0);

                joinDate.setText(String.format(getString(R.string.join_on_place_holder), jsonObject.getString("join_date")));
                if (jsonObject.getString("member_type").equals(AppApi.CLUB_ADMIN))
                    isAdmin = true;
            }
        } catch (JSONException e) {
            Log.e("catch", "isStudentClubMember: " + e.getMessage());
        }

    }

    private void isStudentClubMember(String jsonResponse) {
        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(jsonResponse);
            if (obj.optBoolean("status")) {

                JSONArray dataArray = obj.getJSONArray("data");

                JSONObject jsonObject = dataArray.getJSONObject(0);

                if (!jsonObject.getString("sr_no").equals("null")) {

                    srNo = jsonObject.getString("sr_no");

                    if (jsonObject.getString("member_status").equals("1")) { // Is member
                        isMember.setVisibility(View.VISIBLE);
                        isNotMember.setVisibility(View.GONE);
                        leaveClub.setVisibility(View.VISIBLE);
                        getClubInfo(stdId, clubId, AppApi.GET_STUDENT_CLUB_STATUS);
                    } else if (jsonObject.getString("member_status").equals("2")) { // Requested
                        isMember.setVisibility(View.GONE);
                        isNotMember.setVisibility(View.VISIBLE);
                        cancelRequest.setVisibility(View.VISIBLE);
                        joinNow.setVisibility(View.GONE);
                        leaveClub.setVisibility(View.GONE);
                    } else if (jsonObject.getString("member_status").equals("3")) { // Blocked
                        isMember.setVisibility(View.GONE);
                        isNotMember.setVisibility(View.GONE);
                        leaveClub.setVisibility(View.VISIBLE);
                    }

                } else {
                    Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                }

            } else { // neither member nor requested
                isNotMember.setVisibility(View.VISIBLE);
                isMember.setVisibility(View.GONE);
                cancelRequest.setVisibility(View.GONE);
                leaveClub.setVisibility(View.GONE);
                joinNow.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            Log.e("catch", "isStudentClubMember: " + e.getMessage());
        }
    }

    private void writeClubInfo(String jsonResponse) {
        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(jsonResponse);
            if (obj.optBoolean("status")) {

                JSONArray dataArray = obj.getJSONArray("data");

                JSONObject jsonObject = dataArray.getJSONObject(0);

                if (!jsonObject.getString("club_id").equals("null")) {

                    clubName = jsonObject.getString("club_name");
                    clubKey = jsonObject.getString("club_key");
                    getSupportActionBar().setTitle(clubName);
                    clubMotive.setText(jsonObject.getString("club_motive"));
                    clubStartDate.setText(jsonObject.getString("club_start_date"));
                    totalMembers.setText(obj.getString("total_members"));

                    if (!jsonObject.getString("club_logo").equals("null"))
                        Picasso.get().load(RetroApi.CLUB_LOGO + jsonObject.getString("club_logo")).placeholder(R.drawable.club_logo_placeholder).into(clubLogo);
                    else
                        clubLogo.setImageResource(R.drawable.club_logo_placeholder);

                    if (!jsonObject.getString("club_background_image").equals("null"))
                        Picasso.get().load(RetroApi.CLUB_BACKGROUND + jsonObject.getString("club_background_image")).placeholder(R.drawable.place_holder).into(clubBgImage);
                    else
                        clubBgImage.setImageResource(R.drawable.place_holder);

                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Log.e("catch", "writeClubInfo: " + e.getMessage());
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