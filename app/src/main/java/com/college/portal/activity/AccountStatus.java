package com.college.portal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.college.portal.R;
import com.college.portal.sharedpreferences.SharedPrefManager;

import static com.college.portal.api.AppApi.ACCOUNT_MESSAGE;
import static com.college.portal.api.AppApi.ACCOUNT_STATUS;
import static com.college.portal.api.AppApi.STUDENT_ACCOUNT_BLOCKED;
import static com.college.portal.api.AppApi.STUDENT_ACCOUNT_NOT_VERIFIED;


public class AccountStatus extends AppCompatActivity {

    //Variables and view
    private TextView messageView, nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_status);

        Bundle bundle = getIntent().getExtras();
        int status = 0;
        String name = "Student";
        if(bundle != null){
            status = getIntent().getIntExtra(ACCOUNT_STATUS,0);
            name = getIntent().getStringExtra(ACCOUNT_MESSAGE);
        }


        messageView = findViewById(R.id.message);
        nameView = findViewById(R.id.name);


        nameView.setText("Hello "+ name +" !");
        switch (status){
            case STUDENT_ACCOUNT_NOT_VERIFIED:
                messageView.setText(R.string.not_verified_message);
                break;
            case STUDENT_ACCOUNT_BLOCKED:
                messageView.setText(R.string.account_blocked);
                break;
        }

    }

    public void onClick(View view) {
        if(view.getId() == R.id.logout){
            if(SharedPrefManager.getInstance(AccountStatus.this).isLoggedIn()){
                SharedPrefManager.getInstance(AccountStatus.this).clearStudent();
            }
            toLoginActivity();
        }

        if(view.getId() == R.id.exit){
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void toLoginActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}