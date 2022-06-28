package com.quangdau.greenhouse.Other;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkConnection {
    private final Context context;

    public NetworkConnection(Context context) {
        this.context = context;
    }
    public boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
