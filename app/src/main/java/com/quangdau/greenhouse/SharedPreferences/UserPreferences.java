package com.quangdau.greenhouse.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {
    private static final String USER_SHARED_PREFERENCES = "USER_SHARED_PREFERENCES";
    private final Context context;

    public UserPreferences(Context context) {
        this.context = context;
    }

    public void setToken(String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", value).apply();
    }
    public String getToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "null");
    }
    public void setStateFragment(String stateFragment){
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("stateFragment", stateFragment).apply();
    }
    public String getStateFragment(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString("stateFragment", "null");
    }
}
