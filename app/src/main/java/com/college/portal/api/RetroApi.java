package com.college.portal.api;

import com.college.portal.model.InfoResponse;
import com.college.portal.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroApi {

    // Mobile
    String BASE_URL = "http://192.168.43.126/collegeportalapi/";
    // Emulator
    // String BASE_URL = "http://10.0.2.2:80/collegeportalapi/";

    @GET("login.php")
    Call<LoginResponse> studentLogin(
            @Query("std_id") String stdId,
            @Query("std_password") String stdPassword
    );


    @GET("studentinfo.php")
    Call<InfoResponse> getStudentInfo(
            @Query("std_id") String stdId,
            @Query("std_password") String stdPassword
    );


    String TERMS_URL = "termsdoc.html";
    String PRIVACY_URL = "privacydoc.html";
}
