package com.college.portal.api;

public interface AppApi {

    int STUDENT_ID_NOT_FOUND = 201;
    int STUDENT_PASSWORD_DO_NOT_MATCH = 202;
    int STUDENT_ACCOUNT_NOT_VERIFIED = 203;
    int STUDENT_ACCOUNT_BLOCKED = 204;

    String ACCOUNT_STATUS = "status";
    String ACCOUNT_MESSAGE = "message";
    String PAGE_URL = "page_url";

}
