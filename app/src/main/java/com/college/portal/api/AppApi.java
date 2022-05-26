package com.college.portal.api;

public interface AppApi {

    // Sign In
    int STUDENT_ID_NOT_FOUND = 201;
    int STUDENT_PASSWORD_DO_NOT_MATCH = 202;
    int STUDENT_ACCOUNT_NOT_VERIFIED = 203;
    int STUDENT_ACCOUNT_BLOCKED = 204;


    String ACCOUNT_STATUS = "status";
    String ACCOUNT_MESSAGE = "message";
    String PAGE_URL = "page_url";

    //Assignment
    String ASSIGNMENT_LIST = "0";
    String ASSIGNMENT_ID = "assignment_id";


    //Application
    String INTERNET_BROADCAST_ACTION = "check_internet";
    String ONLINE_STATUS = "online_status";

    //App Theme
    String MODE_DARK = "dark";
    String MODE_LIGHT = "light";

    // News
    int NEWS_SLIDER_YES = 1;
    int NEWS_SLIDER_NO = 0;

    String NEWS_ID = "news_id";

    // About Us
    int GALLERY_TYPE_IMAGE = 1;
    int GALLERY_TYPE_VIDEO = 2;

    String MEDIA_PATH = "media_path";
    String MEDIA_DESCRIPTION = "media_description";
    String MEDIA_POST = "media_post";
    String ABOUT_US_ACTIVITY = "about_us_activity";
    String MISSION = "mission";
    String VISION = "vision";
    String VALUES = "values";

    // Enquiry
    int PHONE_CALL_CODE = 101;

    String PHONE = "+91 7081654255";
    String PHONE_URI = "tel:";
    String SMS_URI = "smsto:";
    String SMS_TYPE = "vnd.android-dir/mms-sms";
    String SMS_ADDRESS = "address";
    String SMS_BODY = "sms_body";
    String STATUS = "status";
}
