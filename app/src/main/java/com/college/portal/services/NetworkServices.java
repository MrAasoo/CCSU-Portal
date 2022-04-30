package com.college.portal.services;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;
import static com.college.portal.api.AppApi.ONLINE_STATUS;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;


public class NetworkServices extends Service {


    private static final String TAG = "InternetService";
    Handler handler = new Handler();


    private Runnable periodUpdate = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(periodUpdate, 5 * 1000 - SystemClock.elapsedRealtime() % 1000);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(INTERNET_BROADCAST_ACTION);
            broadcastIntent.putExtra(ONLINE_STATUS, isNetworkConnected(NetworkServices.this));
            sendBroadcast(broadcastIntent);
        }
    };


    public static boolean isNetworkConnected(Context c) {

        if (isConnected(c) || isConnecting(c))
            return true;
        else
            return false;
    }


    private static boolean isConnected(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private static boolean isConnecting(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Service");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not Yet Implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(periodUpdate);
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
