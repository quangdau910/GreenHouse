package com.quangdau.greenhouse.activity_pages;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.modelsAPI.post_authen.authenPost;
import com.quangdau.greenhouse.modelsAPI.res_authority.authority;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class activity_login extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 123;
    //declare variables
    TextInputEditText account,password;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        //Assign variables
        account =  findViewById(R.id.account);
        password =  findViewById(R.id.passWord);
        btnLogin =  findViewById(R.id.Login);
        //Button Listener
        btnLogin.setOnClickListener(view -> login(Objects.requireNonNull(account.getText()).toString(), Objects.requireNonNull(password.getText()).toString()));
        //
        checkPermission();



    }
    private void toastNew(String textToast){
        Toast toast = new Toast(activity_login.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error_login, findViewById(R.id.custom_toast));
        TextView txt = view.findViewById(R.id.text_custom_toast);
        txt.setText(textToast);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM,0,100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    private void login(String account, String password){
        //Check empty input
        if (account.length() == 0 && password.length() == 0){
            toastNew("Account and password must not empty!");
        }else if(account.length() == 0){
            toastNew("Account must not empty!");
        }else if(password.length() == 0){
            toastNew("Password must not empty!");
        }else{
            ApiServer post = ApiServer.retrofit.create(ApiServer.class);
            authenPost authenPost = new authenPost("Authen", account, password, Build.MODEL, getIpAddress());
            Call <authority> postAuth = post.postAuth(authenPost);
            postAuth.enqueue(new Callback<authority>() {
                @Override
                public void onResponse(Call<authority> call, Response<authority> response) {
                    //check response from server
                    if (response.body().getResponse() == null){
                        Intent nextPage= new Intent(activity_login.this, activity_main.class);
                        //Transfer data to activity_main
                        packedData(nextPage, response);
                        startActivity(nextPage);
                        finish();
                    }else{
                        toastNew("Wrong account or password!");
                    }
                }
                @Override
                public void onFailure(Call<authority> call, Throwable t) {
                    Log.d("login", "Failed! "+ t);
                    toastNew("No response from server!");
                }
            });
        }
    }


    private String getIpAddress(){
        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipAddress = Formatter.formatIpAddress(ip);
        return ipAddress;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void checkPermission(){
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return;
        }else{
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, REQUEST_PERMISSION_CODE);
        }
    }

    private void packedData(Intent intent, Response<authority> response ){
        intent.putExtra("token", response.body().getToken());
        intent.putExtra("authority", response.body().getAuthority());
    }


}