package com.quangdau.greenhouse.Other;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.quangdau.greenhouse.R;

public class ToastError {
    private Context context;
    private final Activity mActivity;
    private String text;

    public ToastError(Context context, Activity mActivity) {
        this.context = context;
        this.mActivity = mActivity;
    }


    public void makeText(String textToast){
        Toast toast = new Toast(context);
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.toast_error, mActivity.findViewById(R.id.toastError));
        TextView txt = view.findViewById(R.id.textViewToast);
        txt.setText(textToast);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM,0,200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }




}
