package com.college.portal.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.college.portal.model.StudentPref;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "student_pref";
    private static SharedPrefManager mInstance;
    private Context mContext;

    public SharedPrefManager(Context context) {
        this.mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance == null){
            mInstance =new SharedPrefManager(context);
        }

        return mInstance;
    }

    public void saveStudent(StudentPref studentPref){
        SharedPreferences preferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("std_id", studentPref.getStdId());
        editor.putString("std_password", studentPref.getStdPassword());
        editor.putString("std_name", studentPref.getStdName());
        editor.putString("std_image", studentPref.getStdImage());
        editor.putString("std_department", studentPref.getStdDepartment());
        editor.putString("std_department_id", studentPref.getStdDepartmentId());
        editor.putString("std_branch", studentPref.getStdBranchName());
        editor.putString("std_branch_id", studentPref.getStdBranchId());

        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences preferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return preferences.getString("std_id", null) != null;
    }

    public StudentPref getStudentInfo(){
        SharedPreferences preferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new StudentPref(preferences.getString("std_id", null),
                preferences.getString("std_password", null),
                preferences.getString("std_name", null),
                preferences.getString("std_image", null),
                preferences.getString("std_department", null),
                preferences.getString("std_department_id", null),
                preferences.getString("std_branch", null),
                preferences.getString("std_branch_id", null)
        );
    }

    public void clearStudent(){
        SharedPreferences preferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

}
