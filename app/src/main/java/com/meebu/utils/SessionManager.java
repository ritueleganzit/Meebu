package com.meebu.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.meebu.SignInActivity;

import java.util.HashMap;

/**
 * Created by eleganz on 2/11/18.
 */

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "MeebuUser";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String USER_ID = "user_id";
    public static final String MOBILE = "mobile";
    public static final String PASSWORD= "password";
    public static final String EMAIL= "email";
    public static final String FULLNAME= "fullname";



    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(String email,String fullname,String user_id,String password,String mobile){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(USER_ID, user_id);
        editor.putString(MOBILE, mobile);
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);

        editor.putString(FULLNAME, fullname);



        // commit changes
        editor.commit();
    }



    public void updatePassword(String password){

        editor.putString(PASSWORD, password);
        editor.commit();


    }


    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, SignInActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }


    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(USER_ID, pref.getString(USER_ID, null));
        user.put(EMAIL, pref.getString(EMAIL, null));
        user.put(PASSWORD, pref.getString(PASSWORD, null));
        user.put(MOBILE, pref.getString(MOBILE, null));
        user.put(FULLNAME, pref.getString(FULLNAME, null));



        // return user
        return user;
    }



    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, SignInActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }



}
