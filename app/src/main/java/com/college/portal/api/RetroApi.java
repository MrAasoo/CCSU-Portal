package com.college.portal.api;

import com.college.portal.model.ContactUs;
import com.college.portal.model.InfoResponse;
import com.college.portal.model.LoginResponse;
import com.google.gson.JsonObject;

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


    @GET("collegecontacts.php")
    Call<JsonObject> getCollegeContactUs();

    @GET("collegeevents.php")
    Call<JsonObject> getNewsList();

    @GET("assignment.php")
    Call<JsonObject> getAssignmentList();


    String TERMS_URL = "termsdoc.html";
    String PRIVACY_URL = "privacydoc.html";
    String HISTORY_URL = "history.html";
}
