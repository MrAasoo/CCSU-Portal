package com.college.portal;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ProgressDialogInterface extends Dialog {


    // Objects and Variables
    private Activity activity;
    private TextView progressMessage;
    private String message;

    public ProgressDialogInterface(@NonNull Activity activity, String message) {
        super(activity);
        this.activity = activity;
        this.message = message;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_progress);

        progressMessage = findViewById(R.id.progress_message);
        progressMessage.setText(message);
    }

}
