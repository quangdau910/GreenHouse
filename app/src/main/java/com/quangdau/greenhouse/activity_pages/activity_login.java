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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.Spinner.spinnerLimitSetting.CategorySpinner;
import com.quangdau.greenhouse.Spinner.spinnerLimitSetting.CategorySpinnerAdapter;
import com.quangdau.greenhouse.language.Language;
import com.quangdau.greenhouse.modelsAPI.post_authen.authenPost;
import com.quangdau.greenhouse.modelsAPI.res_authenPost.resAuthorityPost;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class activity_login extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 123;
    //declare variables
    TextInputEditText account,password;
    AppCompatButton btnLogin;

    Spinner spinnerLanguage;
    CategorySpinnerAdapter categoryLanguageAdapter;
    public int NextLanguage=0;
    TextInputLayout hintAccount;
    TextInputLayout hintPassword;


    UserPreferences userPreferences;
    Context context;
    Boolean backPressCheck;

    Language language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        //Assign variables
        account =  findViewById(R.id.editTextAccount);
        password =  findViewById(R.id.editTextPassword);
        btnLogin =  findViewById(R.id.buttonLogin);

        hintAccount = findViewById(R.id.textInputLayoutAccount);
        hintPassword = findViewById(R.id.textInputLayoutPassword);

        context = this;
        userPreferences = new UserPreferences(context);
        backPressCheck = false;
        //Check permission
        checkPermission();
        //spinner Language
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
                        case 0: {
                            language.updateLanguage("vi");
                            hintAccount.setHint(getResources().getString(R.string.Hint_account));
                            hintPassword.setHint(getResources().getString(R.string.Hint_password));
                            btnLogin.setText(getResources().getString(R.string.Text_Btn_Login));
                        }
                        break;
                        case 1: {
                            language.updateLanguage("en");
                            hintAccount.setHint(getResources().getString(R.string.Hint_account));
                            hintPassword.setHint(getResources().getString(R.string.Hint_password));
                            btnLogin.setText(getResources().getString(R.string.Text_Btn_Login));
                        }
                        break;
                        default:
                    }
                }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Button Listener
        btnLogin.setOnClickListener(view -> login(Objects.requireNonNull(account.getText()).toString(), Objects.requireNonNull(password.getText()).toString()));
    }


    private void login(String account, String password){
        //Check empty input
        if (account.length() == 0 && password.length() == 0){
            toastNew(getResources().getString(R.string.Account_password_not_empty));
        }else if(account.length() == 0){
            toastNew(getResources().getString(R.string.Account_not_empty));
        }else if(password.length() == 0){
            toastNew(getResources().getString(R.string.Password_not_empty));
        }else{
            ApiServer post = ApiServer.retrofit.create(ApiServer.class);
            String deviceName =  Build.MODEL;
            authenPost authenPost = new authenPost("Authen", account, password, deviceName, getIpAddress());
            Call <resAuthorityPost> postAuth = post.postAuth(authenPost);
            postAuth.enqueue(new Callback<resAuthorityPost>() {
                @Override
                public void onResponse(Call<resAuthorityPost> call, Response<resAuthorityPost> response) {
                    //check response from server
                    if (response.body() != null){
                        if (response.body().getResponse() == null){
                            Intent nextPage= new Intent(activity_login.this, activity_main.class);
                            userPreferences.setToken(response.body().getToken());
                            packedData(nextPage, response);
                            startActivity(nextPage);
                            finish();
                        }else{
                            toastNew(getResources().getString(R.string.Wrong_account_or_password));
                        }
                    }
                }
                @Override
                public void onFailure(Call<resAuthorityPost> call, Throwable t) {
                    Log.e("login", "Error Login: "+ t);
                    toastNew(getResources().getString(R.string.No_response_from_server));
                }
            });
        }
    }

    private void toastNew(String textToast){
        Toast toast = new Toast(activity_login.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_error_login, findViewById(R.id.customToast));
        TextView txt = view.findViewById(R.id.textViewToast);
        txt.setText(textToast);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM,0,100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    private String getIpAddress(){
        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
    }

    private void checkPermission(){
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.e("gh", "Permission grated!");
        }else{
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, REQUEST_PERMISSION_CODE);
        }
    }

    private void packedData(Intent intent, Response<resAuthorityPost> response ){
        assert response.body() != null;
        intent.putExtra("authority", response.body().getAuthority());
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

}