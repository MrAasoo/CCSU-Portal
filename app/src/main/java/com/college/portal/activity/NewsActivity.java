package com.college.portal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.college.portal.ProgressDialogInterface;
import com.college.portal.R;
import com.college.portal.adapter.ContactUsAdapter;
import com.college.portal.adapter.NewsAdapter;
import com.college.portal.api.RetrofitClient;
import com.college.portal.model.ContactUs;
import com.college.portal.model.News;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;
    protected NewsAdapter mAdapter;
    protected List<News> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.news_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mList = new ArrayList<>();
        mAdapter = new NewsAdapter(getApplicationContext(), mList);
        recyclerView.setAdapter(mAdapter);

        getNewsList();

    }

    private void getNewsList() {

        final ProgressDialogInterface progressDialog = new ProgressDialogInterface(
                NewsActivity.this,
                getString(R.string.progress_loading_message));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();


        Call<JsonObject> call = RetrofitClient.getInstance()
                .getRetroApi()
                .getNewsList();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        //Log.e("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        writeRecycler(jsonresponse);

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

    private void writeRecycler(String jsonresponse) {
        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(jsonresponse);
            if (obj.optBoolean("status")) {

                JSONArray dataArray = obj.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    News news = new News();
                    JSONObject jsonObject = dataArray.getJSONObject(i);

                    news.setnId(Integer.valueOf(jsonObject.getString("n_id")));
                    news.setnTitle(jsonObject.getString("n_title"));
                    news.setnMessage(jsonObject.getString("n_message"));
                    news.setnDate(jsonObject.getString("n_date"));

                    mList.add(news);

                }
                mAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //For appbar back press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

}