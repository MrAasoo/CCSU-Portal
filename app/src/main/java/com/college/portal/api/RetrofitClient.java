package com.college.portal.api;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.college.portal.api.RetroApi.BASE_URL;

public class RetrofitClient {

    private static RetrofitClient mInstance;
    private Retrofit retrofit;
    private Gson gson;

    private RetrofitClient(){
        gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit =  new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
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
