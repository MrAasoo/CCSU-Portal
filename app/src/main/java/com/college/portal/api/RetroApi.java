package com.college.portal.api;


import com.college.portal.model.InfoResponse;
import com.college.portal.model.LoginResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroApi {

    // 000WebHost
    //String BASE_URL = "https://studentcollegeportal.000webhostapp.com/";
    // Mobile
    //String BASE_URL = "http://192.168.43.180/collegeportalapi/";
    // Emulator
    // String BASE_URL = "http://10.0.2.2:80/collegeportalapi/";

    String TERMS_URL = "termsdoc.html";
    String PRIVACY_URL = "privacydoc.html";
    String HISTORY_URL = "history.php";
    String STUDENT_IMAGES = "images/student_images/";
    String NEWS_IMAGES = "images/news_images/";
    String COLLEGE_IMAGES = "images/college_images/";
    String COLLEGE_VIDEOS = "videos/college_videos/";
    String CLUB_LOGO = "images/club_images/logo/";
    String CLUB_BACKGROUND = "images/club_images/background/";

    @GET("login.php")
    Call<LoginResponse> studentLogin(
            @Query("std_id") String stdId,
            @Query("std_password") String stdPassword
    );


    @GET("student_info.php")
    Call<InfoResponse> getStudentInfo(
            @Query("std_id") String stdId,
            @Query("std_password") String stdPassword
    );


    @GET("college_contact.php")
    Call<JsonObject> getCollegeContactUs();


    @GET("college_news.php")
    Call<JsonObject> getNewsList(
            @Query("n_id") Integer nId,
            @Query("n_slider") Integer nSlider
    );


    @GET("college_gallery.php")
    Call<JsonObject> getCollegeGalleryList(
            @Query("gallery_type") Integer nGalleryType
    );


    @GET("assignment.php")
    Call<JsonObject> getAssignmentList(
            @Query("std_id") String stdId,
            @Query("assi_id") String assiId
    );


    @GET("student_subjects.php")
    Call<JsonObject> getSubjectList(
            @Query("std_id") String stdId
    );


    @GET("feedback.php")
    Call<JsonObject> feedback(
            @Query("std_id") String stdId,
            @Query("std_password") String stdPassword,
            @Query("feedback_message") String feedbackMessage
    );


    @GET("college_clubs.php")
    Call<JsonObject> getCollegeClub(
            @Query("std_id") String stdId,
            @Query("club_id") String clubId
    );

}
