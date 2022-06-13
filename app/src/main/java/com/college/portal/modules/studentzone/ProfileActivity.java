package com.college.portal.modules.studentzone;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.college.portal.AlertDialogInterface;
import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.api.RetroApi;
import com.college.portal.api.RetrofitClient;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.modules.SignInActivity;
import com.college.portal.modules.model.InfoResponse;
import com.college.portal.modules.model.StudentPref;
import com.college.portal.modules.studentzone.model.StudentInfo;
import com.college.portal.services.NetworkServices;
import com.college.portal.sharedpreferences.SharedPrefManager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    //Variables and Views
    private CollapsingToolbarLayout mToolBar;
    private TextView stdId, stdDep, stdBranch, stdSem, stdAcademic, stdFather, stdMother, stdDob, stdContact, stdEmail, stdAddress, stdPin;
    private ImageView stdImage;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);

        // collapsing toolbar
        mToolBar = findViewById(R.id.collapsing_toolbar);

        // text views
        stdId = findViewById(R.id.std_id);
        stdDep = findViewById(R.id.std_dep);
        stdBranch = findViewById(R.id.std_branch);
        stdSem = findViewById(R.id.std_sem);
        stdAcademic = findViewById(R.id.std_session);
        stdFather = findViewById(R.id.std_father);
        stdMother = findViewById(R.id.std_mother);
        stdDob = findViewById(R.id.std_dob);
        stdContact = findViewById(R.id.std_contact);
        stdEmail = findViewById(R.id.std_email);
        stdAddress = findViewById(R.id.std_address);
        stdPin = findViewById(R.id.std_pin);
        stdImage = findViewById(R.id.std_image);

        //pref
        StudentPref studentPref = SharedPrefManager.getInstance(ProfileActivity.this).getStudentInfo();

        //Collapsing Toolbar
        mToolBar.setTitle(studentPref.getStdName());
        stdId.setText(studentPref.getStdId());

        Picasso.get()
                .load(RetroApi.STUDENT_IMAGES + studentPref.getStdImage())
                .placeholder(R.drawable.ic_app_icon)
                .into(stdImage);

        //Log.i("ProfileActivity", "onCreate:--- imageurl  ----> " + BASE_URL+ STUDENT_IMAGE_PATH + studentPref.getStdImage());
        apiCallGetStudentInfo(studentPref.getStdId(), studentPref.getStdPassword());

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

    private void apiCallGetStudentInfo(String stdId, String stdPassword) {
        Call<InfoResponse> call = RetrofitClient.getInstance()
                .getRetroApi()
                .getStudentInfo(stdId, stdPassword);

        call.enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {

                if (response.isSuccessful()) {
                    InfoResponse infoResponse = response.body();

                    //login success
                    if (!infoResponse.getError()) {
                        StudentInfo studentInfo = infoResponse.getInfo();

                        //Set values to textView
                        stdDep.setText(studentInfo.getStdDepartment());
                        stdBranch.setText(String.format(getString(R.string.student_branch_text), studentInfo.getStdBranchName(), studentInfo.getStdBranchFullName()));
                        stdSem.setText(studentInfo.getStdSem());
                        stdAcademic.setText(studentInfo.getStdAcademic());
                        stdFather.setText(studentInfo.getStdFather());
                        stdMother.setText(studentInfo.getStdMother());
                        stdDob.setText(studentInfo.getStdDob());
                        stdContact.setText(studentInfo.getStdContact());
                        stdEmail.setText(studentInfo.getStdEmail());
                        stdAddress.setText(String.format(getString(R.string.address_placeholder), studentInfo.getStdCity(), studentInfo.getStdDist()));
                        stdPin.setText(studentInfo.getStdPin());

                    } else { //if error
                        AlertDialogInterface dialog = new AlertDialogInterface(ProfileActivity.this,
                                "No Data Found!",
                                "Try to refresh or Contact to administration",
                                R.drawable.ic_app_icon);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(false);
                        dialog.create();
                        dialog.show();
                        dialog.dismissAlertDialog();
                        //Toast.makeText(ProfileActivity.this, "No Data found!", Toast.LENGTH_SHORT).show();
                        Log.e(SignInActivity.class.getSimpleName(), "onResponse: " + infoResponse.getError() + " : " + infoResponse.getCode());

                    }
                }

            }

            @Override
            public void onFailure(Call<InfoResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Server response failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //For appbar back press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

}