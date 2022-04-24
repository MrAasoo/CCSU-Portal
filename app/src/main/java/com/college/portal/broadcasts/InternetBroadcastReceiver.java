package com.college.portal.broadcasts;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;
import static com.college.portal.api.AppApi.ONLINE_STATUS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;

import com.college.portal.AlertDialogInterface;
import com.college.portal.R;

public class InternetBroadcastReceiver extends BroadcastReceiver {

    private boolean isDialogShown = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(INTERNET_BROADCAST_ACTION)) ;
        {
            if (!intent.getBooleanExtra(ONLINE_STATUS, false)) {
                if (!isDialogShown) {
                    AlertDialogInterface dialog = new AlertDialogInterface(context,
                            context.getString(R.string.no_internet_title),
                            context.getString(R.string.no_internet_message),
                            R.drawable.ic_nointernet);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCancelable(false);
                    dialog.create();
                    dialog.show();
                    Button btnOk = dialog.findViewById(R.id.dialog_right_button);
                    Button btnExit = dialog.findViewById(R.id.dialog_left_button);

                    btnOk.setVisibility(View.VISIBLE);
                    btnOk.setText(R.string.ok_text);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isDialogShown = false;
                            dialog.dismiss();
                            // TODO : No internet
                        }
                    });

                    btnExit.setVisibility(View.VISIBLE);
                    btnExit.setText(R.string.exit_text);
                    btnExit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isDialogShown = false;
                            dialog.dismiss();
                            // TODO : No internet
                        }
                    });
                    isDialogShown = true;
                }
            }
        }
    }
}
