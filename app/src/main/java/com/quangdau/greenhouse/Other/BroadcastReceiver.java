package com.quangdau.greenhouse.Other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.quangdau.greenhouse.R;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    private final Activity mActivity;
    private boolean networkDisconnected;

    public BroadcastReceiver(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkConnection networkConnection = new NetworkConnection(context);
        if (!networkConnection.isNetworkConnected()){
            toastNetworkOffline(context.getResources().getString(R.string.network_offline), context, R.layout.toast_network_offline, R.id.toastNetworkOffline);
            networkDisconnected = true;
        }else if(networkConnection.isNetworkConnected() && networkDisconnected){
            toastNetworkOffline(context.getResources().getString(R.string.network_online), context, R.layout.toast_network_online, R.id.toastNetworkOnline);
            networkDisconnected = false;
        }
    }
    private void toastNetworkOffline(String textToast, Context context, int layoutID, int id){
        Toast toast = new Toast(context);
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(layoutID, mActivity.findViewById(id));
        TextView txt = view.findViewById(R.id.textViewToast);
        txt.setText(textToast);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM,0,200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

}
