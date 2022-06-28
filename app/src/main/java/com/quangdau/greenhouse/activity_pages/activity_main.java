package com.quangdau.greenhouse.activity_pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.FragmentParent.fragment_account;
import com.quangdau.greenhouse.FragmentParent.fragment_graph;
import com.quangdau.greenhouse.FragmentParent.fragment_history;
import com.quangdau.greenhouse.FragmentParent.fragment_home;
import com.quangdau.greenhouse.FragmentParent.fragment_settings;
import com.quangdau.greenhouse.Other.BroadcastReceiver;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.modelsAPI.post_renewToken.RenewTokenPost;
import com.quangdau.greenhouse.modelsAPI.res_renewTokenPost.resRenewTokenPost;

import java.time.Instant;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_main extends AppCompatActivity {
    //Declare variables
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    //Parent Fragment
    fragment_home fragmentHome;
    fragment_settings fragmentSettings;
    fragment_account fragmentAccount;
    fragment_history fragmentHistory;
    fragment_graph fragmentGraph;
    //Handler
    Handler handler;
    Runnable runnable;
    //Other
    String token;
    ArrayList<String> arrAuthority;
    long expTime;
    UserPreferences userPreferences;
    Context context;
    boolean backPressCheck;
    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_main);
        //Assign variables
        fragmentHome = new fragment_home();
        fragmentSettings = new fragment_settings();
        fragmentAccount = new fragment_account();
        fragmentHistory = new fragment_history();
        fragmentGraph = new fragment_graph();
        floatingActionButton = findViewById(R.id.fab);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        backPressCheck = false;
        broadcastReceiver = new BroadcastReceiver();
        //Get authority
        parseData();
        packedData(fragmentHome);
        //Get token
        context = this;
        userPreferences = new UserPreferences(context);
        token = userPreferences.getToken();
        //Setting timer
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                ApiServer post = ApiServer.retrofit.create(ApiServer.class);
                RenewTokenPost renewTokenPost = new RenewTokenPost(userPreferences.getToken(), "RenewToken");
                Call <resRenewTokenPost> postRenew = post.postRenewToken(renewTokenPost);
                postRenew.enqueue(new Callback<resRenewTokenPost>() {
                    @Override
                    public void onResponse(Call<resRenewTokenPost> call, Response<resRenewTokenPost> response) {
                        if (response.body() != null && response.body().getResponse().equals("RenewToken")){
                            Log.e("gh", "Main oldToken: "+ token);
                            userPreferences.setToken(response.body().getToken());
                            Log.e("gh", "Main newToken: "+ response.body().getToken());
                        }
                    }

                    @Override
                    public void onFailure(Call<resRenewTokenPost> call, Throwable t) {
                        Log.e("gh", "Main: "+ t);
                    }
                });
            }
        };
        handler.postAtTime(runnable, expTime - 5*60);
        //Setting bottom nav
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        //Pre-select item in bottom nav
        bottomNavigationView.setSelectedItemId(R.id.home);
        //Open default fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragmentHome).commit();
        //Bottom nav listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.graph:
                        packedData(fragmentGraph);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, activity_main.this.fragmentGraph).commit();
                        return true;
                    case R.id.settings:
                        packedData(fragmentSettings);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, activity_main.this.fragmentSettings).commit();
                        return true;
                    case R.id.history:
                        packedData(fragmentHistory);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, activity_main.this.fragmentHistory).commit();
                        return true;
                    case R.id.editTextAccount:
                        packedData(fragmentAccount);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, activity_main.this.fragmentAccount).commit();
                        return true;
                    default:
                }
                return false;
            }
        });
        //Floating act button listener
        floatingActionButton.setOnClickListener(view -> {
            //unselect bottom nav item
            bottomNavigationView.setSelectedItemId(R.id.home);
            packedData(fragmentHome);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragmentHome).commit();
        });
    }



    private void parseData(){
        //Get arrAuthority from activity_login
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            arrAuthority = extras.getStringArrayList("authority");
            expTime = extras.getLong("expTime");
            Instant instant = Instant.ofEpochSecond(expTime);
            Log.e("gh", "Main: "+ expTime);
            Log.e("gh", "Main: "+ instant);
            Log.e("gh", "Main: "+ System.currentTimeMillis());
        }
    }
    private void packedData(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("arrAuthority",arrAuthority);
        fragment.setArguments(bundle);
    }
    @Override
    public void onBackPressed() {
        if (backPressCheck){
            super.onBackPressed();
            return;
        }
        backPressCheck = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                backPressCheck = false;
            }
        }, 2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.EXTRA_NO_CONNECTIVITY);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
}