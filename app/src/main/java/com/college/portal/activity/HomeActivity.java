package com.college.portal.activity;

import static com.college.portal.api.RetroApi.BASE_URL;
import static com.college.portal.api.RetroApi.STUDENT_IMAGE_PATH;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.college.portal.R;
import com.college.portal.model.StudentPref;
import com.college.portal.sharedpreferences.SharedPrefManager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {


    //Views and Variables
    private CollapsingToolbarLayout mToolBar;
    private ImageView stdImage;
    private TextView stdDepartmentView, stdIdView, greet;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //student pref
        StudentPref studentPref = SharedPrefManager.getInstance(HomeActivity.this).getStudentInfo();

        //Views
        mToolBar = findViewById(R.id.collapsing_toolbar);
        stdDepartmentView = findViewById(R.id.std_department);
        stdIdView = findViewById(R.id.std_id);
        coordinatorLayout = findViewById(R.id.home);

        //Time of day
        greet = findViewById(R.id.greet);
        stdImage = findViewById(R.id.std_image);
        greet.setText(getTimeOfDay());

        mToolBar.setTitle(studentPref.getStdName());
        stdDepartmentView.setText(studentPref.getStdDepartment());
        stdIdView.setText(studentPref.getStdId());

        Picasso.get()
                .load(BASE_URL+ STUDENT_IMAGE_PATH + studentPref.getStdImage())
                .placeholder(R.drawable.ic_app_icon)
                .into(stdImage);

        //Log.i("HomeActivity", "onCreate:--- imageurl  ----> " + BASE_URL+ STUDENT_IMAGE_PATH + studentPref.getStdImage());

    }

    private String getTimeOfDay() {
        Calendar c = Calendar.getInstance();
        int hrs = c.get(Calendar.HOUR_OF_DAY);
        Log.e("TAG", "onCreate: " + hrs);
        if (hrs >= 20 || hrs <= 4)
            return "Good Night...";       //After 8pm
        if (hrs >= 16)
            return "Good evening...";      // After 4pm
        if (hrs > 12)
            return "Good afternoon...";    // After 12pm
        if (hrs >= 6)
            return "Good morning...";      // After 6am
        if (hrs > 4)
            return "Morning's Sunshine!"; // REALLY early
        return "Welcome";
    }

    public void cardViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_profile:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                break;

            case R.id.home_assignment:
                startActivity(new Intent(getApplicationContext(), AssignmentActivity.class));
                break;

            case R.id.home_library:
                startActivity(new Intent(getApplicationContext(), LibraryActivity.class));
                break;

            case R.id.home_news:
                startActivity(new Intent(getApplicationContext(), NewsActivity.class));
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
                Snackbar.make(coordinatorLayout, R.string.clubs, BaseTransientBottomBar.LENGTH_LONG).show();
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