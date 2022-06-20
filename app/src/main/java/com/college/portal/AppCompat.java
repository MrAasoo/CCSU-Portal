package com.college.portal;

import static android.content.pm.PackageManager.GET_META_DATA;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class AppCompat extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        resetTitle();
    }

    private void resetTitle() {
        try {
            int label = getPackageManager().getActivityInfo(getComponentName(), GET_META_DATA).labelRes;
            if (label != 0) {
                setTitle(label);
            }
        } catch (Exception e) {
            Log.e("AppCompat", "resetTitle: ", e);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(LocalManager.setLocale(newBase));
    }
}
