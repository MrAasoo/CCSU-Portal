package com.college.portal.modules;

import static com.college.portal.api.AppApi.ACCOUNT_MESSAGE;
import static com.college.portal.api.AppApi.ACCOUNT_STATUS;
import static com.college.portal.api.AppApi.STUDENT_ACCOUNT_BLOCKED;
import static com.college.portal.api.AppApi.STUDENT_ACCOUNT_NOT_VERIFIED;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.college.portal.AppCompat;
import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.sharedpreferences.SharedPrefManager;


public class AccountStatus extends AppCompat {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_status);

        Bundle bundle = getIntent().getExtras();
        int status = 0;
        String name = "Student";
        if (bundle != null) {
            status = getIntent().getIntExtra(ACCOUNT_STATUS, 0);
            name = getIntent().getStringExtra(ACCOUNT_MESSAGE);
        }


        //Variables and view
        TextView messageView = findViewById(R.id.message);
        TextView nameView = findViewById(R.id.name);
        Button btnLogout = findViewById(R.id.logout);
        Button btnExit = findViewById(R.id.exit);


        nameView.setText(String.format(getString(R.string.hello), name));
        switch (status) {
            case STUDENT_ACCOUNT_NOT_VERIFIED:
                messageView.setText(R.string.not_verified_message);
                break;
            case STUDENT_ACCOUNT_BLOCKED:
                messageView.setText(R.string.account_blocked);
                break;
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPrefManager.getInstance(AccountStatus.this).isLoggedIn()) {
                    SharedPrefManager.getInstance(AccountStatus.this).clearStudent();
                }
                toLoginActivity();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //AppTheme Theme
        AppTheme.setAppTheme(getApplicationContext());
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