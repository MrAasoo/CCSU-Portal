package com.college.portal.activity;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;
import static com.college.portal.api.RetroApi.BASE_URL;
import static com.college.portal.api.RetroApi.STUDENT_IMAGES;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.model.StudentPref;
import com.college.portal.services.NetworkServices;
import com.college.portal.sharedpreferences.SharedPrefManager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    //Views and Variables
    private CollapsingToolbarLayout mToolBar;
    private ImageView stdImage;
    private TextView stdDepartment, stdBranch, stdId, greet;
    private CoordinatorLayout coordinatorLayout;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);

        //student pref
        StudentPref studentPref = SharedPrefManager.getInstance(HomeActivity.this).getStudentInfo();

        //Views
        mToolBar = findViewById(R.id.collapsing_toolbar);
        stdDepartment = findViewById(R.id.std_department);
        stdBranch = findViewById(R.id.std_branch);
        stdId = findViewById(R.id.std_id);
        coordinatorLayout = findViewById(R.id.home);

        //Time of day
        greet = findViewById(R.id.greet);
        stdImage = findViewById(R.id.std_image);
        greet.setText(getTimeOfDay());

        mToolBar.setTitle(studentPref.getStdName());
        stdDepartment.setText(studentPref.getStdDepartment());
        stdBranch.setText(studentPref.getStdBranchName());
        stdId.setText(studentPref.getStdId());

        Picasso.get()
                .load(BASE_URL + STUDENT_IMAGES + studentPref.getStdImage())
                .placeholder(R.drawable.ic_app_icon)
                .into(stdImage);

        //Log.i("HomeActivity", "onCreate:--- image url  ----> " + BASE_URL + STUDENT_IMAGES + studentPref.getStdImage());

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

    private String getTimeOfDay() {
        Calendar c = Calendar.getInstance();
        int hrs = c.get(Calendar.HOUR_OF_DAY);
        //Log.e("TAG", "onCreate: " + hrs);
        if (hrs >= 20 || hrs <= 4)
            return getString(R.string.good_night_msg);         // After 8pm
        if (hrs >= 16)
            return getString(R.string.good_evening_msg);       // After 4pm
        if (hrs >= 11)
            return getString(R.string.good_afternoon_msg);     // After 12pm
        if (hrs >= 6)
            return getString(R.string.good_morning_msg);       // After 6am
        if (hrs > 4)
            return getString(R.string.morning_msg);   // Really early
        return getString(R.string.welcome);
    }

    public void cardViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_profile:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                break;

            case R.id.home_assignment:
                startActivity(new Intent(getApplicationContext(), AssignmentListActivity.class));
                break;

            case R.id.home_library:
                startActivity(new Intent(getApplicationContext(), LibraryActivity.class));
                break;

            case R.id.home_news:
                startActivity(new Intent(getApplicationContext(), NewsListActivity.class));
                break;

            case R.id.home_notifications:
                startActivity(new Intent(getApplicationContext(), MessagesActivity.class));
                break;

            case R.id.home_settings:
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                break;

            case R.id.home_logout:
                SharedPrefManager.getInstance(getApplicationContext()).clearStudent();
                toLoginActivity();
                break;

            case R.id.home_contact:
                startActivity(new Intent(getApplicationContext(), ContactUsActivity.class));
                break;

            case R.id.home_about_us:
                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                break;

            case R.id.home_club:
                startActivity(new Intent(getApplicationContext(), ClubsActivity.class));
                //Snackbar.make(coordinatorLayout, R.string.clubs, BaseTransientBottomBar.LENGTH_LONG).show();
                break;

        }
    }

    private void toLoginActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}