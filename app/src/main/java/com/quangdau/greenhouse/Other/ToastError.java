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
    private String text;

    public ToastError(Context context) {
        this.context = context;
    }

    public void makeText(String textToast){
        Activity activity = (Activity) context;
        Toast toast = new Toast(context);
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.toast_error, activity.findViewById(R.id.toastError));
        TextView txt = view.findViewById(R.id.textViewToast);
        txt.setText(textToast);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM,0,200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

}
