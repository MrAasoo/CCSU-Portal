package com.college.portal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.college.portal.R;
import com.college.portal.model.StudentPref;
import com.college.portal.prefrences.SharedPrefManager;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {


    //Views and Variables
    private CollapsingToolbarLayout mToolBar;
    private TextView stdDepartmentView, stdIdView, greet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //pref
        StudentPref studentPref = SharedPrefManager.getInstance(HomeActivity.this).getStudentInfo();
        //Views
        mToolBar = findViewById(R.id.collapsing_toolbar);
        stdDepartmentView = findViewById(R.id.std_department);
        stdIdView = findViewById(R.id.std_id);

        //Time of day
        greet = findViewById(R.id.greet);
        greet.setText(getTimeOfDay());

        mToolBar.setTitle(studentPref.getStdName());
        stdDepartmentView.setText(studentPref.getStdDepartment());
        stdIdView.setText(studentPref.getStdId());

        Toast.makeText(this, "Image : " + studentPref.getStdImage(), Toast.LENGTH_SHORT).show();



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
        if (hrs >=  6)
            return "Good morning...";      // After 6am
        if (hrs >  4)
            return "Morning's Sunshine!"; // REALLY early
        return "Welcome";
    }

    public void cardViewClicked(View view) {
        switch(view.getId()){
            case R.id.home_profile:
                startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
                break;

            case R.id.home_assignment:
                startActivity(new Intent(HomeActivity.this, AssignmentActivity.class));
                break;

            case R.id.home_news:
                Toast.makeText(this, "News Clicked!", Toast.LENGTH_SHORT).show();
                //TODO : News work ...
                break;

            case R.id.home_notifications:
                Toast.makeText(this, "Notification Clicked!", Toast.LENGTH_SHORT).show();
                //TODO : Notification work ...
                break;

            case R.id.home_settings:
                startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                break;

            case R.id.home_logout:
                SharedPrefManager.getInstance(HomeActivity.this).clearStudent();
                toLoginActivity();
                break;

            case R.id.home_contact:
                Toast.makeText(this, "Contact Clicked!", Toast.LENGTH_SHORT).show();
                //TODO : Contact work ...
                break;
            case R.id.home_about_us:
                startActivity(new Intent(HomeActivity.this, AboutUsActivity.class));
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