package com.college.portal.modules;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;
import static com.college.portal.api.AppApi.NEWS_SLIDER_NO;
import static com.college.portal.api.AppApi.NEWS_SLIDER_YES;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.college.portal.AppTheme;
import com.college.portal.ProgressDialogInterface;
import com.college.portal.R;
import com.college.portal.ZoomOutPageTransformer;
import com.college.portal.api.RetrofitClient;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.modules.adapter.NewsAdapter;
import com.college.portal.modules.adapter.NewsSliderAdapter;
import com.college.portal.modules.model.News;
import com.college.portal.services.NetworkServices;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListActivity extends AppCompatActivity {

    // news list
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;
    private List<News> mList;

    // news image slider
    private NewsSliderAdapter newsSliderAdapter;
    private List<News> mSlideList;
    private ViewPager newsViewPager;
    private TabLayout newsTabLayout;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        //Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);

        // News Slider
        newsViewPager = findViewById(R.id.news_view_pager);
        newsTabLayout = findViewById(R.id.news_tab_layout);

        mSlideList = new ArrayList<>();
        newsSliderAdapter = new NewsSliderAdapter(NewsListActivity.this, mSlideList);
        newsViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        newsViewPager.setAdapter(newsSliderAdapter);

        // News Slider Timer
        Timer timer = new Timer();
        timer.schedule(new SlideTimer(), 5000, 3000);
        newsTabLayout.setupWithViewPager(newsViewPager, true);
        getNewsList(NEWS_SLIDER_YES);

        // News List
        recyclerView = findViewById(R.id.news_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mList = new ArrayList<>();
        mAdapter = new NewsAdapter(NewsListActivity.this, mList);
        recyclerView.setAdapter(mAdapter);
        getNewsList(NEWS_SLIDER_NO);

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

    private void getNewsList(int nSlider) {

        final ProgressDialogInterface progressDialog = new ProgressDialogInterface(
                NewsListActivity.this,
                getString(R.string.progress_loading_message));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();


        Call<JsonObject> call = RetrofitClient.getInstance()
                .getRetroApi()
                .getNewsList(0, nSlider);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        //Log.e("onSuccess", response.body().toString());

                        String jsonResponse = response.body().toString();
                        writeRecycler(jsonResponse, nSlider);

                    } else {
                        Log.e("onEmptyResponse", "Returned empty response");
                        //Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("onFailure : ", "Something went wrong .....", t);
                progressDialog.dismiss();
            }
        });
    }

    private void writeRecycler(String jsonResponse, int nSlider) {
        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(jsonResponse);
            if (obj.optBoolean("status")) {

                if (nSlider == NEWS_SLIDER_NO) {
                    JSONArray dataArray = obj.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        News news = new News();
                        JSONObject jsonObject = dataArray.getJSONObject(i);

                        news.setnId(Integer.valueOf(jsonObject.getString("n_id")));
                        news.setnTitle(jsonObject.getString("n_title"));
                        news.setnSubtitle(jsonObject.getString("n_subtitle"));
                        news.setnDate(jsonObject.getString("n_date"));

                        mList.add(news);

                    }
                    mAdapter.notifyDataSetChanged();
                } else if (nSlider == NEWS_SLIDER_YES) {

                    JSONArray dataArray = obj.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        News news = new News();
                        JSONObject jsonObject = dataArray.getJSONObject(i);

                        news.setnId(Integer.valueOf(jsonObject.getString("n_id")));
                        news.setnTitle(jsonObject.getString("n_title"));
                        news.setnSubtitle(jsonObject.getString("n_subtitle"));
                        news.setnImage(jsonObject.getString("n_image"));

                        mSlideList.add(news);

                    }
                    newsSliderAdapter.notifyDataSetChanged();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public class SlideTimer extends TimerTask {

        @Override
        public void run() {

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (newsViewPager.getCurrentItem() < mSlideList.size() - 1) {
                        newsViewPager.setCurrentItem(newsViewPager.getCurrentItem() + 1);
                    } else {
                        newsViewPager.setCurrentItem(0);
                    }
                }

            });

        }

    }

}