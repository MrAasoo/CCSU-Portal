package com.college.portal.modules;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.api.RetroApi;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.modules.campusmap.CampusMapListActivity;
import com.college.portal.modules.clubs.ClubsActivity;
import com.college.portal.modules.gallery.CollegeGalleryActivity;
import com.college.portal.modules.library.LibraryActivity;
import com.college.portal.modules.model.StudentPref;
import com.college.portal.modules.studentzone.AssignmentListActivity;
import com.college.portal.modules.studentzone.MessagesActivity;
import com.college.portal.modules.studentzone.ProfileActivity;
import com.college.portal.modules.studentzone.SubjectListActivity;
import com.college.portal.services.NetworkServices;
import com.college.portal.sharedpreferences.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    //Views and Variables
    private ImageView stdImage;
    private TextView stdName, stdDepartment, stdBranch, stdId, greet;
    private Button enquiry;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Toolbar
        Toolbar mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.college_name_short));

        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);

        //student pref
        StudentPref studentPref = SharedPrefManager.getInstance(HomeActivity.this).getStudentInfo();

        //Views
        stdDepartment = findViewById(R.id.std_department);
        stdBranch = findViewById(R.id.std_branch);
        stdId = findViewById(R.id.std_id);
        enquiry = findViewById(R.id.enquiry);

        //Time of day
        greet = findViewById(R.id.greet);
        stdName = findViewById(R.id.std_name);
        stdImage = findViewById(R.id.std_image);
        greet.setText(getTimeOfDay());

        stdName.setText(studentPref.getStdName());
        stdDepartment.setText(studentPref.getStdDepartment());
        stdBranch.setText(studentPref.getStdBranchName());
        stdId.setText(studentPref.getStdId());

        Picasso.get()
                .load(RetroApi.STUDENT_IMAGES + studentPref.getStdImage())
                .placeholder(R.drawable.ic_app_icon)
                .into(stdImage);

        //Log.i("HomeActivity", "onCreate:--- image url  ----> " + BASE_URL + STUDENT_IMAGES + studentPref.getStdImage());

        enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, EnquiryActivity.class));
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
        return getString(R.string.morning_msg);   // Really early
    }

    public void cardViewClicked(View view) {
        switch (view.getId()) {
            //Student zone
            case R.id.home_profile:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                break;

            case R.id.home_assignment:
                startActivity(new Intent(getApplicationContext(), AssignmentListActivity.class));
                break;

            case R.id.home_subject:
                startActivity(new Intent(getApplicationContext(), SubjectListActivity.class));
                break;

            case R.id.home_messages:
                startActivity(new Intent(getApplicationContext(), MessagesActivity.class));
                break;
/*
            case R.id.home_mails:
                startActivity(new Intent(getApplicationContext(), MailListActivity.class));
                break;
*/
            case R.id.home_library:
                startActivity(new Intent(getApplicationContext(), LibraryActivity.class));
                break;

            case R.id.home_news:
                startActivity(new Intent(getApplicationContext(), NewsListActivity.class));
                break;

            case R.id.home_contact:
                startActivity(new Intent(getApplicationContext(), ContactUsActivity.class));
                break;

            case R.id.home_about_us:
                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                break;

            case R.id.home_college_gallery:
                startActivity(new Intent(getApplicationContext(), CollegeGalleryActivity.class));
                break;

            case R.id.home_club:
                startActivity(new Intent(getApplicationContext(), ClubsActivity.class));
                break;

            case R.id.home_campus_map:
                startActivity(new Intent(getApplicationContext(), CampusMapListActivity.class));
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.home_settings:
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                break;

            case R.id.home_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setIcon(R.drawable.ic_app_icon)
                        .setTitle(getString(R.string.logout_text))
                        .setMessage("Are you sure to logout?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPrefManager.getInstance(getApplicationContext()).clearStudent();
                                toLoginActivity();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toLoginActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}