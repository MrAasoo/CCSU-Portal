package com.college.portal.modules.library;

import static com.college.portal.api.AppApi.INTERNET_BROADCAST_ACTION;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.college.portal.AlertDialogInterface;
import com.college.portal.AppCompat;
import com.college.portal.AppTheme;
import com.college.portal.ProgressDialogInterface;
import com.college.portal.R;
import com.college.portal.api.AppApi;
import com.college.portal.api.RetrofitClient;
import com.college.portal.broadcasts.InternetBroadcastReceiver;
import com.college.portal.modules.library.adapter.BooksAdapter;
import com.college.portal.modules.library.model.Books;
import com.college.portal.services.NetworkServices;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookListActivity extends AppCompat {


    protected RecyclerView recyclerView;
    protected BooksAdapter mAdapter;
    protected ArrayList<Books> mList;

    //For Network
    private IntentFilter mIntentFilter;
    private InternetBroadcastReceiver mInternetBroadcastReceiver;

    public static void requestStoragePermission(int requestCode, Activity mActivity) {
        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
    }

    public static boolean hasStoragePermission(Context mContext) {
        return (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        //Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        switch (getIntent().getStringExtra(AppApi.LIBRARY_REQ)) {
            case AppApi.E_BOOK:
                getSupportActionBar().setTitle(getString(R.string.e_books));
                break;
            case AppApi.E_MAG:
                getSupportActionBar().setTitle(getString(R.string.e_magazine));
                break;
            default:
                getSupportActionBar().setTitle(getString(R.string.library));
        }

        //Network broadcast
        mInternetBroadcastReceiver = new InternetBroadcastReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(INTERNET_BROADCAST_ACTION);
        Intent serviceIntent = new Intent(this, NetworkServices.class);
        startService(serviceIntent);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mList = new ArrayList<>();
        mAdapter = new BooksAdapter(BookListActivity.this, BookListActivity.this, mList);
        recyclerView.setAdapter(mAdapter);

        getBookList(getIntent().getStringExtra("req_type"));

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
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getBookList(String req_type) {

        final ProgressDialogInterface progressDialog = new ProgressDialogInterface(
                BookListActivity.this,
                getString(R.string.progress_loading_message));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();


        Call<JsonObject> call = RetrofitClient.getInstance()
                .getRetroApi()
                .getELibraryItemsList(req_type);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                //Log.i("Response
                // ..00string", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        //Log.i("onSuccess", response.body().toString());
                        String jsonResponse = response.body().toString();
                        writeRecycler(jsonResponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
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
                for (int i = 0; i < dataArray.length(); i++) {
                    Books book = new Books();
                    JSONObject jsonObject = dataArray.getJSONObject(i);

                    if (!jsonObject.getString("srno").equals("null")) {
                        book.setBookTitle(jsonObject.getString("book_title"));
                        book.setBookAuthor(jsonObject.getString("book_author"));
                        book.setPublisher(jsonObject.getString("publisher"));
                        book.setPublishDate(jsonObject.getString("publish_date"));
                        book.setBookPath(jsonObject.getString("book_path"));

                        mList.add(book);
                    } else {
                        Toast.makeText(this, "No Books found.", Toast.LENGTH_SHORT).show();
                    }
                }

                mAdapter.notifyDataSetChanged();
            } else {
                AlertDialogInterface dialog = new AlertDialogInterface(BookListActivity.this,
                        "No result found!",
                        "Please try again later...",
                        R.drawable.ic_app_icon);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.create();
                dialog.show();
                dialog.dismissAlertDialog();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppApi.STORAGE_PERMISSION) {
            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Showing the toast message
                Toast.makeText(BookListActivity.this, "Phone Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BookListActivity.this, "Phone Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}