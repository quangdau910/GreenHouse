package com.quangdau.greenhouse.activity_pages;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.Other.NetworkConnection;
import com.quangdau.greenhouse.Other.ToastError;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.Spinner.CategorySpinner;
import com.quangdau.greenhouse.Spinner.CategorySpinnerAdapter;
import com.quangdau.greenhouse.Language.Language;
import com.quangdau.greenhouse.modelsAPI.post_authen.AuthenPost;
import com.quangdau.greenhouse.modelsAPI.res_authenPost.resAuthorityPost;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class activity_login extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 123;
    //Declare variables
    TextInputEditText editTextAccount, editTextPassword;
    AppCompatButton btnLogin;
    TextInputLayout hintAccount;
    TextInputLayout hintPassword;
    //Shared preferences
    UserPreferences userPreferences;
    Context context;
    Boolean backPressCheck;
    //Language
    Spinner spinnerLanguage;
    CategorySpinnerAdapter categoryLanguageAdapter;
    Language language;
    //Other
    NetworkConnection networkConnection;
    ToastError toastError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);
        //Assign variables
        editTextAccount =  findViewById(R.id.editTextAccount);
        editTextPassword =  findViewById(R.id.editTextPassword);
        btnLogin =  findViewById(R.id.buttonLogin);
        hintAccount = findViewById(R.id.textInputLayoutAccount);
        hintPassword = findViewById(R.id.textInputLayoutPassword);
        //Shared preferences
        context = this;
        userPreferences = new UserPreferences(context);
        backPressCheck = false;
        //Network connection
        networkConnection = new NetworkConnection(this);
        //Other
        toastError = new ToastError(this);
        //Check permission
        checkPermission();
        //Spinner language
        language = new Language(this);
        spinnerLanguage = findViewById(R.id.spinner_Language);
        List<CategorySpinner> list = new ArrayList<>();
        list.add(new CategorySpinner("Việt Nam"));
        list.add(new CategorySpinner("English"));
        categoryLanguageAdapter = new CategorySpinnerAdapter(this,R.layout.item_selected_language,list);
        spinnerLanguage.setAdapter(categoryLanguageAdapter);
        if(language.getLang().equals("en")){
            spinnerLanguage.setSelection(1);
        }
        if(language.getLang().equals("vi")){
            spinnerLanguage.setSelection(0);
        }
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            language.updateLanguage("vi");
                            hintAccount.setHint(getResources().getString(R.string.hint_account));
                            hintPassword.setHint(getResources().getString(R.string.hint_password));
                            btnLogin.setText(getResources().getString(R.string.tv_btn_login));
                            break;
                        case 1:
                            language.updateLanguage("en");
                            hintAccount.setHint(getResources().getString(R.string.hint_account));
                            hintPassword.setHint(getResources().getString(R.string.hint_password));
                            btnLogin.setText(getResources().getString(R.string.tv_btn_login));
                            break;
                    }
                }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //Button Listener
        btnLogin.setOnClickListener(view -> login(Objects.requireNonNull(editTextAccount.getText()).toString(), Objects.requireNonNull(editTextPassword.getText()).toString()));
    }

    private void login(String account, String password){
        editTextPassword.clearFocus();
        editTextAccount.clearFocus();
        //Check empty input
        if (networkConnection.isNetworkConnected()) {
            if (account.length() == 0 && password.length() == 0) {
                toastError.makeText(getResources().getString(R.string.empty_account_password));
            } else if (account.length() == 0) {
                toastError.makeText(getResources().getString(R.string.empty_account));
            } else if (password.length() == 0) {
                toastError.makeText(getResources().getString(R.string.empty_password));
            } else {
                btnLogin.setEnabled(false);
                ApiServer post = ApiServer.retrofit.create(ApiServer.class);
                AuthenPost authenPost = new AuthenPost("Authen", account, password, Build.MODEL, getIpAddress());
                Call<resAuthorityPost> postAuth = post.postAuth(authenPost);
                postAuth.enqueue(new Callback<resAuthorityPost>() {
                    @Override
                    public void onResponse(Call<resAuthorityPost> call, Response<resAuthorityPost> response) {
                        //Check response from server
                        if (response.body() != null && response.body().getResponse().equals("Author")) {
                            Intent nextPage = new Intent(activity_login.this, activity_main.class);
                            userPreferences.setToken(response.body().getToken());
                            packedData(nextPage, response);
                            startActivity(nextPage);
                            finish();
                        } else {
                            toastError.makeText(getResources().getString(R.string.wrong_account_password));
                        }
                        btnLogin.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<resAuthorityPost> call, Throwable t) {
                        Log.e("login", "Error Login: " + t);
                        toastError.makeText(getResources().getString(R.string.no_response_from_server));
                        btnLogin.setEnabled(true);
                    }
                });
            }
        }else {
            toastError.makeText("No connection!");
            btnLogin.setEnabled(true);
            editTextAccount.setFocusable(true);
            editTextPassword.setFocusable(true);
        }

    }


    private String getIpAddress(){
        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
    }

    private void checkPermission(){
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.e("gh", "Login: Permission grated!");
        }else{
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, REQUEST_PERMISSION_CODE);
        }
    }

    private void packedData(Intent intent, Response<resAuthorityPost> response ){
        assert response.body() != null;
        intent.putExtra("authority", response.body().getAuthority());
        intent.putExtra("expTime", response.body().getExpTime());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        if (backPressCheck){
            super.onBackPressed();
            return;
        }
        backPressCheck = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> backPressCheck = false, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.e("gh", "Login: resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.e("gh", "Login: pause");
        finish();
    }
}