package com.college.portal.api;

public interface AppApi {

    int STUDENT_ID_NOT_FOUND = 201;
    int STUDENT_PASSWORD_DO_NOT_MATCH = 202;
    int STUDENT_ACCOUNT_NOT_VERIFIED = 203;
    int STUDENT_ACCOUNT_BLOCKED = 204;


    String ACCOUNT_STATUS = "status";
    String ACCOUNT_MESSAGE = "message";
    String PAGE_URL = "page_url";
    String ASSIGNMENT_LIST = "0";
    String ASSIGNMENT_ID = "assignment_id";
    String NEWS_ID = "news_id";


    //Application
    String INTERNET_BROADCAST_ACTION = "check_internet";
    String ONLINE_STATUS = "online_status";
    String MODE_DARK = "dark";
    String MODE_LIGHT = "light";
    int NEWS_SLIDER_YES = 1;
    int NEWS_SLIDER_NO = 0;

}
