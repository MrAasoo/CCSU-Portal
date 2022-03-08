package com.college.portal.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.college.portal.AlertDialogInterface;
import com.college.portal.R;
import com.college.portal.api.RetrofitClient;
import com.college.portal.model.InfoResponse;
import com.college.portal.model.StudentInfo;
import com.college.portal.model.StudentPref;
import com.college.portal.sharedpreferences.SharedPrefManager;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    //Variables and Views
    private CollapsingToolbarLayout mToolBar;
    private TextView stdId, stdDep, stdSem, stdAcademic, stdFather, stdMother, stdDob, stdContact, stdEmail, stdAddress, stdPin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //views
        mToolBar = findViewById(R.id.collapsing_toolbar);
        stdId = findViewById(R.id.std_id);
        stdDep = findViewById(R.id.std_dep);
        stdSem = findViewById(R.id.std_sem);
        stdAcademic = findViewById(R.id.std_session);
        stdFather = findViewById(R.id.std_father);
        stdMother = findViewById(R.id.std_mother);
        stdDob = findViewById(R.id.std_dob);
        stdContact = findViewById(R.id.std_contact);
        stdEmail = findViewById(R.id.std_email);
        stdAddress = findViewById(R.id.std_address);
        stdPin = findViewById(R.id.std_pin);




        //pref
        StudentPref studentPref = SharedPrefManager.getInstance(ProfileActivity.this).getStudentInfo();

        //Collapsing Toolbar
        mToolBar.setTitle(studentPref.getStdName());
        stdId.setText(studentPref.getStdId());
        stdDep.setText(studentPref.getStdDepartment());

        apiCallGetStudentInfo(studentPref.getStdId(), studentPref.getStdPassword());

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
                        stdSem.setText(studentInfo.getStdSem());
                        stdAcademic.setText(studentInfo.getStdAcademic());
                        stdFather.setText(studentInfo.getStdFather());
                        stdMother.setText(studentInfo.getStdMother());
                        stdDob.setText(studentInfo.getStdDob());
                        stdContact.setText(studentInfo.getStdContact());
                        stdEmail.setText(studentInfo.getStdEmail());
                        stdAddress.setText(String.format("City : %s\nDistrict : %s", studentInfo.getStdCity(), studentInfo.getStdDist()));
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
}