package com.college.portal.modules;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.college.portal.AppCompat;
import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.api.AppApi;

public class EnquiryActivity extends AppCompat {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);

        //Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.enquiry));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView call = findViewById(R.id.call);
        TextView message = findViewById(R.id.message);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermissionPhone()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EnquiryActivity.this);
                    builder.setTitle(R.string.ask_on_call);
                    builder.setIcon(R.drawable.call_image);
                    builder.setMessage(String.format(getString(R.string.call_message), AppApi.PHONE));
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                            phoneIntent.setData(Uri.parse(AppApi.PHONE_URI + AppApi.PHONE));
                            startActivity(phoneIntent);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setCancelable(false);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    requestPermissionPhone(AppApi.PHONE_CALL_CODE);
                }
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EnquiryActivity.this);
                builder.setTitle(R.string.enq_messages);
                builder.setIcon(R.drawable.message_image);
                final View dialogView = getLayoutInflater().inflate(R.layout.layout_sms, null);
                builder.setView(dialogView);
                builder.setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText testSms = dialogView.findViewById(R.id.text_sms);
                        sendSMS(testSms.getText().toString().trim());
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void sendSMS(String sms) {
        if (!sms.isEmpty()) {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse(AppApi.SMS_URI));
            smsIntent.setType(AppApi.SMS_TYPE);
            smsIntent.putExtra(AppApi.SMS_ADDRESS, AppApi.PHONE);
            smsIntent.putExtra(AppApi.SMS_BODY, sms);
            startActivity(smsIntent);
        } else {
            Toast.makeText(this, R.string.cant_send_empty_message, Toast.LENGTH_SHORT).show();
        }
    }

    // Phone Call permission
    private boolean hasPermissionPhone() {
        return ContextCompat.checkSelfPermission(EnquiryActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionPhone(int requestCode) {
        ActivityCompat.requestPermissions(EnquiryActivity.this, new String[]{Manifest.permission.CALL_PHONE}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppApi.PHONE_CALL_CODE) {
            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Showing the toast message
                Toast.makeText(EnquiryActivity.this, R.string.permission_granted, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EnquiryActivity.this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //AppTheme Theme
        AppTheme.setAppTheme(getApplicationContext());

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