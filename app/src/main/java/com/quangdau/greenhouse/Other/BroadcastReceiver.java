package com.quangdau.greenhouse.Other;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.EXTRA_NO_CONNECTIVITY.equals(intent.getAction())){
            NetworkConnection networkConnection = new NetworkConnection(context);
            if (networkConnection.isNetworkConnected()){
                Toast.makeText(context, "Internet connected!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Internet disconnected!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
