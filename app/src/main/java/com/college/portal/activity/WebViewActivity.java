package com.college.portal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.college.portal.R;

import static com.college.portal.api.AppApi.PAGE_URL;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String url = "";
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            url = getIntent().getStringExtra(PAGE_URL);
        }
        WebView webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }
}