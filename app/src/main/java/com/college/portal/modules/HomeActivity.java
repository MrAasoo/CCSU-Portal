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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.college.portal.AppCompat;
import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.api.RetroApi;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.modules.campus_map.CampusMapListActivity;
import com.college.portal.modules.classroom.ClassRoomActivity;
import com.college.portal.modules.clubs.ClubsActivity;
import com.college.portal.modules.gallery.CollegeGalleryActivity;
import com.college.portal.modules.library.LibraryActivity;
import com.college.portal.modules.model.StudentPref;
import com.college.portal.modules.news_updates.NewsListActivity;
import com.college.portal.modules.student_zone.AssignmentListActivity;
import com.college.portal.modules.student_zone.ProfileActivity;
import com.college.portal.modules.student_zone.SubjectListActivity;
import com.college.portal.services.NetworkServices;
import com.college.portal.sharedpreferences.SharedPrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class HomeActivity extends AppCompat {

    //Views and Variables
    private ImageView stdImage;
    private TextView stdName, stdDepartment, stdBranch, stdId, greet;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;


    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    // User
    private StudentPref studentPref;

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

        // Firebase user
        mAuth = FirebaseAuth.getInstance();

        //student pref
        studentPref = SharedPrefManager.getInstance(HomeActivity.this).getStudentInfo();

        //Views
        stdDepartment = findViewById(R.id.std_department);
        stdBranch = findViewById(R.id.std_branch);
        stdId = findViewById(R.id.std_id);

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


        TextView enquiry = findViewById(R.id.enquiry);
        enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, EnquiryActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        mUser = mAuth.getCurrentUser();
        if (mUser == null) {
            mAuth.createUserWithEmailAndPassword(studentPref.getStdId() + "@stportal.com", studentPref.getStdPassword())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(HomeActivity.this, "FirebaseAuth Successful", Toast.LENGTH_SHORT).show();
                                mUser = mAuth.getCurrentUser();
                            } else {
                                mAuth.signInWithEmailAndPassword(studentPref.getStdId() + "@stportal.com", studentPref.getStdPassword())
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                //Toast.makeText(HomeActivity.this, "FirebaseAuth Successful", Toast.LENGTH_SHORT).show();
                                                mUser = mAuth.getCurrentUser();
                                            }
                                        });
                            }
                        }
                    });
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

    public void cardViewClicked(@NonNull View view) {
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
                startActivity(new Intent(getApplicationContext(), ClassRoomActivity.class));
                break;
/*
            case R.id.home_mails:
                startActivity(new Intent(getApplicationContext(), MailListActivity.class));
                break;

            case R.id.home_message:
                startActivity(new Intent(getApplicationContext(), MessageActivity.class));
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
                        .setMessage(getString(R.string.logout_message))
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                SharedPrefManager.getInstance(getApplicationContext()).clearStudent();
                                toLoginActivity();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
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