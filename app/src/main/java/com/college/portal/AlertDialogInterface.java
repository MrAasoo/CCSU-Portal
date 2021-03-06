package com.college.portal;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class AlertDialogInterface extends Dialog {


    // Objects and Variables
    private final String title;
    private final String message;
    private final int icon;
    private ImageView dialogIcon;
    private TextView dialogTitle, dialogMessage;
    private Button btnOk;
    private final Context context;

    public AlertDialogInterface(@NonNull Context context, String title, String message, int icon) {
        super(context);
        this.context = context;
        this.title = title;
        this.message = message;
        this.icon = icon;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog);

        dialogIcon = findViewById(R.id.dialog_icon);
        dialogTitle = findViewById(R.id.dialog_title);
        dialogMessage = findViewById(R.id.dialog_message);
        btnOk = findViewById(R.id.dialog_middle_button);

        dialogIcon.setImageResource(icon);
        dialogTitle.setText(title);
        dialogMessage.setText(message);

    }

    public void dismissAlertDialog(){
        btnOk.setVisibility(View.VISIBLE);
        btnOk.setText(context.getString(R.string.ok));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
