package com.quangdau.greenhouse.Other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.activity_pages.activity_login;

public class NetworkConnection {
    private final Context context;

    public NetworkConnection(Context context) {
        this.context = context;
    }

    public boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void checkStatusCode(Integer statusCode){
        switch (statusCode){
            case 401:
                Log.e("gh", "code 401: Unauthorized");
                Toast.makeText(context, context.getResources().getString(R.string.login_again), Toast.LENGTH_SHORT).show();
                Intent nextPage = new Intent(context, activity_login.class);
                context.startActivity(nextPage);
                ((Activity) context).finish();
                break;
            case 403:

                break;
        }
    }
}
