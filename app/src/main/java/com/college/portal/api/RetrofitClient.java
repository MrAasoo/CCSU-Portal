package com.college.portal.api;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // Mobile
    private static final String BASE_URL = "http://192.168.43.126/collegeportalapi/";

    // Emulator
    // private static final String BASE_URL = "http://10.0.2.2:80/collegeportalapi/";


    private static RetrofitClient mInstance;
    private Retrofit retrofit;
    private Gson gson;

    private RetrofitClient(){
        gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit =  new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized  RetrofitClient getInstance(){
        if(mInstance == null){
            mInstance =  new RetrofitClient();
        }
        return mInstance;
    }

    public RetroApi getRetroApi(){
        return retrofit.create(RetroApi.class);
    }

}
