package com.college.portal.api;

public interface AppApi {

    // storage permission
    int STORAGE_PERMISSION = 1400;

    // Sign In
    int STUDENT_ID_NOT_FOUND = 201;
    int STUDENT_PASSWORD_DO_NOT_MATCH = 202;
    int STUDENT_ACCOUNT_NOT_VERIFIED = 203;
    int STUDENT_ACCOUNT_BLOCKED = 204;

    String ACCOUNT_STATUS = "status";
    String ACCOUNT_MESSAGE = "message";
    String PAGE_URL = "page_url";


    // Club
    String GET_CLUB_DATA = "1";
    String IS_STUDENT_CLUB_MEMBER = "2";
    String GET_STUDENT_CLUB_STATUS = "3";

    String CLUB_ID = "club_id";
    String CLUB_EVENT = "event";
    String CLUB_ANNOUNCEMENT = "announcement";
    String CLUB_REQ = "req_type";
    String CLUB_EVENT_ID = "club_event_id";
    String IS_ADMIN = "is_admin";
    String STD_ID = "std_id";
    String CLUB_MEMBER = "2";
    String CLUB_ADMIN = "1";

    String MEMBER_YES = "1";
    String MEMBER_NO = "2";
    String MEMBER_BLOCKED = "3";

    int CLUB_MEMBER_LIST = 201;
    int CLUB_MEMBER_LIST_ADMIN = 202;
    int JOIN_CLUB = 2010;
    int CANCEL_REQ = 2020;
    int LEAVE_CLUB = 2030;
    int ACCEPT_MEMBER = 2040;
    int REMOVE_MEMBER = 2050;
    int BLOCK_MEMBER = 2060;
    int UNBLOCK_MEMBER = 2070;


    //Assignment
    String ASSIGNMENT_LIST = "0";
    String ASSIGNMENT_ID = "assignment_id";


    //Application Network
    String INTERNET_BROADCAST_ACTION = "check_internet";
    String ONLINE_STATUS = "online_status";

    //App Theme
    String MODE_DARK = "dark";
    String MODE_LIGHT = "light";

    // News
    int NEWS_SLIDER_YES = 1;
    int NEWS_SLIDER_NO = 0;

    String NEWS_ID = "news_id";

    // About Us - College Gallery
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

    // Message ~ Noticeboard, Class Discussion
    String NOTICE_ID = "notice_id";
    String NOTICE_BOARD_ID = "0";

    // Library
    String E_MAG = "emag";
    String E_BOOK = "ebook";
    String LIBRARY_REQ = "req_type";

}
