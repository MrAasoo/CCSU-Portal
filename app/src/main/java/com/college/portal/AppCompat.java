package com.college.portal;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.college.portal.sharedpreferences.LanguagePrefManager;

public class AppCompat extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        // Language
        LanguagePrefManager langPrefManager = new LanguagePrefManager(getApplicationContext());
        LocalManager localManager = new LocalManager(this);
        localManager.updateResources(langPrefManager.getLanguage());

    }
}
