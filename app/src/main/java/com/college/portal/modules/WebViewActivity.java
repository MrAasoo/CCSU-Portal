package com.college.portal.modules;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;
import static com.college.portal.api.AppApi.PAGE_URL;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.college.portal.AppCompat;
import com.college.portal.AppTheme;
import com.college.portal.R;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.services.NetworkServices;

public class WebViewActivity extends AppCompat {

    private WebView webView;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        //Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.college_name_short));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);

        String url = getResources().getString(R.string.link_dummy_data);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = getIntent().getStringExtra(PAGE_URL);
        }
        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mInternetBroadcastReceiver);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mInternetBroadcastReceiver, mIntentFilter);

        //AppTheme Theme
        AppTheme.setAppTheme(getApplicationContext());

    }

    //For appbar back press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (webView.canGoBack()) webView.goBack();
                else this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}