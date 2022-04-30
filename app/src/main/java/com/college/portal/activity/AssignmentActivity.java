package com.college.portal.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.college.portal.AppTheme;
import com.college.portal.ProgressDialogInterface;
import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.api.RetrofitClient;
import com.college.portal.model.Assignment;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentActivity extends AppCompatActivity {

    private TextView assiTitle, assiDetails, assiDate, assiDueDate, assiFaculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        //Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        assiTitle = findViewById(R.id.assi_title);
        assiDetails = findViewById(R.id.assi_details);
        assiDate = findViewById(R.id.assi_date);
        assiDueDate = findViewById(R.id.assi_due_date);
        assiFaculty = findViewById(R.id.faculty_name);

        String id = String.valueOf(getIntent().getIntExtra(AppApi.ASSIGNMENT_ID, 0));
        getAssignmentDetails(id);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // AppTheme Theme
        AppTheme.setAppTheme(getApplicationContext());
    }


    private void getAssignmentDetails(String id) {

        final ProgressDialogInterface progressDialog = new ProgressDialogInterface(
                AssignmentActivity.this,
                getString(R.string.progress_loading_message));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();


        Call<JsonObject> call = RetrofitClient.getInstance()
                .getRetroApi()
                .getAssignmentList(id);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                //Log.i("Response
                // ..00string", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        //Log.i("onSuccess", response.body().toString());


                        String jsonresponse = response.body().toString();
                        writeRecycler(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
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

    private void writeRecycler(String jsonresponse) {
        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(jsonresponse);
            if (obj.optBoolean("status")) {

                JSONArray dataArray = obj.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    Assignment assignment = new Assignment();
                    JSONObject jsonObject = dataArray.getJSONObject(i);

                    assiTitle.setText(jsonObject.getString("assi_title"));
                    assiDate.setText(String.format(getString(R.string.assignment_date), jsonObject.getString("assi_date")));
                    assiDueDate.setText(String.format(getString(R.string.assignment_due_date), jsonObject.getString("assi_due_date")));
                    assiDetails.setText(jsonObject.getString("assi_details"));
                    assiFaculty.setText(String.format(getString(R.string.assignment_faculty), jsonObject.getString("faculty_name")));

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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