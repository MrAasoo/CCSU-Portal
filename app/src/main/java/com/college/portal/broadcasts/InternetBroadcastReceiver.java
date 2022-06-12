package com.college.portal.broadcasts;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;
import static com.college.portal.api.AppApi.ONLINE_STATUS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.college.portal.modules.NoInternetActivity;

public class InternetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(INTERNET_BROADCAST_ACTION)) ;
        {
            if (!intent.getBooleanExtra(ONLINE_STATUS, false)) {
                context.startActivity(new Intent(context, NoInternetActivity.class));
            }
        }
    }
}
