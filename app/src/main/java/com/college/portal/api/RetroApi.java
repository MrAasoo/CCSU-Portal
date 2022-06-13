package com.college.portal.api;


import com.college.portal.modules.model.InfoResponse;
import com.college.portal.modules.model.LoginResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroApi {

    // 000WebHost
    //String BASE_URL = "https://studentcollegeportal.000webhostapp.com/";
    // Mobile
    String BASE_URL = "http://192.168.43.41/collegeportalapi/";
    //String BASE_URL = "http://192.168.244.128/collegeportalapi/";
    // Emulator
    // String BASE_URL = "http://10.0.2.2:80/collegeportalapi/";

    String TERMS_URL = BASE_URL + "termsdoc.html";
    String PRIVACY_URL = BASE_URL + "privacydoc.html";
    String HISTORY_URL = BASE_URL + "history.php";
    String STUDENT_IMAGES = BASE_URL + "images/student_images/";
    String NEWS_IMAGES = BASE_URL + "images/news_images/";
    String COLLEGE_IMAGES = BASE_URL + "images/college_images/";
    String COLLEGE_VIDEOS = BASE_URL + "videos/college_videos/";
    String CLUB_LOGO = BASE_URL + "images/club_images/logo/";
    String CLUB_BACKGROUND = BASE_URL + "images/club_images/background/";
    String ASSIGNMENT_FILE_PATH = BASE_URL + "files/assignments/";
    String EBOOKS_FILE_PATH = BASE_URL + "files/books/";

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
            @Query("std_id") String stdId
    );

    @GET("college_clubs_info.php")
    Call<JsonObject> getCollegeClubInfo(
            @Query("std_id") String stdId,
            @Query("club_id") String clubId,
            @Query("req") String req
    );

    @GET("college_club_members.php")
    Call<JsonObject> getCollegeClubMember(
            @Query("std_id") String stdId,
            @Query("club_id") String clubId,
            @Query("req") Integer req,
            @Query("sr_no") String srNo
    );

    @GET("campus_map.php")
    Call<JsonObject> getCampusMap();

    @GET("e_library.php")
    Call<JsonObject> getELibraryItemsList(
            @Query("req_type") String reqType
    );

}
