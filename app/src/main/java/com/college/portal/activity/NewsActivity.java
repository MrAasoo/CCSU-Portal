package com.college.portal.activity;

import static com.college.portal.api.AppApi.NEWS_ID;
import static com.college.portal.api.AppApi.NEWS_SLIDER_YES;
import static com.college.portal.api.RetroApi.BASE_URL;
import static com.college.portal.api.RetroApi.NEWS_IMAGES;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.college.portal.ProgressDialogInterface;
import com.college.portal.R;
import com.college.portal.api.RetrofitClient;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    private ImageView newsImage;
    private TextView newsTitle, newsSubtitle, newsDetail, newsDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        int id = getIntent().getIntExtra(NEWS_ID, 0);

        newsImage = findViewById(R.id.news_image);
        newsTitle = findViewById(R.id.news_title);
        newsSubtitle = findViewById(R.id.news_subtitle);
        newsDetail = findViewById(R.id.news_detail);
        newsDate = findViewById(R.id.news_date);

        getNewsDetails(id);
    }

    private void getNewsDetails(int id) {


        final ProgressDialogInterface progressDialog = new ProgressDialogInterface(
                NewsActivity.this,
                getString(R.string.progress_loading_message));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();


        Call<JsonObject> call = RetrofitClient.getInstance()
                .getRetroApi()
                .getNewsList(id, NEWS_SLIDER_YES);

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
                        writeRecycler(jsonResponse);

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

    private void writeRecycler(String jsonResponse) {
        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(jsonResponse);
            if (obj.optBoolean("status")) {

                JSONArray dataArray = obj.getJSONArray("data");
                JSONObject jsonObject = dataArray.getJSONObject(0);

                String title = jsonObject.getString("n_type");
                switch (title) {
                    case "0":
                        getSupportActionBar().setTitle("News");
                        break;
                    case "1":
                        getSupportActionBar().setTitle("Notice");
                        break;
                    case "2":
                        getSupportActionBar().setTitle("Event");
                        break;
                    default:
                        getSupportActionBar().setTitle(getString(R.string.collage_news));
                }


                newsTitle.setText(jsonObject.getString("n_title"));
                newsSubtitle.setText(jsonObject.getString("n_subtitle"));
                newsDetail.setText(jsonObject.getString("n_detail"));
                if (!jsonObject.getString("n_image").isEmpty()) {
                    Picasso.get().load(BASE_URL + NEWS_IMAGES + jsonObject.getString("n_image")).placeholder(R.drawable.place_holder).into(newsImage);
                } else {
                    newsImage.setVisibility(View.GONE);
                }
                newsDate.setText(String.format(getString(R.string.date), jsonObject.getString("n_date")));

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
}