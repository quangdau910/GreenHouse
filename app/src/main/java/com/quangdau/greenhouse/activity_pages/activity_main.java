package com.quangdau.greenhouse.activity_pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.quangdau.greenhouse.FragmentParent.fragment_account;
import com.quangdau.greenhouse.FragmentParent.fragment_graph;
import com.quangdau.greenhouse.FragmentParent.fragment_history;
import com.quangdau.greenhouse.FragmentParent.fragment_home;
import com.quangdau.greenhouse.FragmentParent.fragment_settings;
import com.quangdau.greenhouse.Other.BroadcastReceiver;
import com.quangdau.greenhouse.Other.NetworkConnection;
import com.quangdau.greenhouse.Other.RenewToken;
import com.quangdau.greenhouse.Services.ServiceRemoveToken;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;



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
    Intent intentRemoveToken;
    long expTime;
    UserPreferences userPreferences;
    Context context;
    boolean backPressCheck;
    BroadcastReceiver broadcastReceiver;
    NetworkConnection networkConnection;
    final long TIME_RENEW_TOKEN = TimeUnit.MINUTES.toMillis(55); //Send RenewToken after 55 minutes (Token exp 1h)
    RenewToken mRenewToken;
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
        broadcastReceiver = new BroadcastReceiver(this);
        networkConnection = new NetworkConnection(this);
        mRenewToken = new RenewToken(this);
        intentRemoveToken = new Intent(this, ServiceRemoveToken.class);
        //Get authority
        parseData();
        packedData(fragmentHome);
        //Get token
        context = this;
        userPreferences = new UserPreferences(context);
        token = userPreferences.getToken();
        //Setting handler postDelays
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                mRenewToken.renewToken();
                handler.postDelayed(this, TIME_RENEW_TOKEN);
            }
        };
        handler.postDelayed(runnable, TIME_RENEW_TOKEN);
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
                }
                return false;
            }
        });
        //Floating act button listener
        floatingActionButton.setOnClickListener(view -> {
            //Unselect bottom nav item
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
        Log.e("gh", "Main: start");
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("gh", "Main: stop");
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("gh", "Main: resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("gh", "Main: paused");
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("gh", "Main: destroy");
        handler.removeCallbacks(runnable);
        startService(intentRemoveToken);
    }
}