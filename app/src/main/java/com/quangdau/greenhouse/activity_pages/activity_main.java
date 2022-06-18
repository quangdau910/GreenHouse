package com.quangdau.greenhouse.activity_pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.quangdau.greenhouse.ParentFragment.fragment_account;
import com.quangdau.greenhouse.ParentFragment.fragment_graph;
import com.quangdau.greenhouse.ParentFragment.fragment_history;
import com.quangdau.greenhouse.ParentFragment.fragment_home;
import com.quangdau.greenhouse.ParentFragment.fragment_settings;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;

import java.util.ArrayList;

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
    //Other
    String token;
    ArrayList<String> arrAuthority;
    UserPreferences userPreferences;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        fragmentHome = new fragment_home();
        fragmentSettings = new fragment_settings();
        fragmentAccount = new fragment_account();
        fragmentHistory = new fragment_history();
        fragmentGraph = new fragment_graph();
        //Assign variables
        floatingActionButton = findViewById(R.id.fab);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        //Get authority
        parseData();
        packedData(fragmentHome);
        //Get token
        context = this;
        userPreferences = new UserPreferences(context);
        token = userPreferences.getToken();
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
                        fragmentGraph = new fragment_graph();
                        packedData(fragmentGraph);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, activity_main.this.fragmentGraph).commit();
                        return true;
                    case R.id.settings:
                        fragmentSettings = new fragment_settings();
                        packedData(fragmentSettings);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, activity_main.this.fragmentSettings).commit();
                        return true;
                    case R.id.history:
                        fragmentHistory = new fragment_history();
                        packedData(fragmentHistory);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, activity_main.this.fragmentHistory).commit();
                        return true;
                    case R.id.account:
                        fragmentAccount = new fragment_account();
                        packedData(fragmentAccount);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, activity_main.this.fragmentAccount).commit();
                        return true;
                    default:
                }
                return false;
            }
        });
        //Floating act button listener
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //unselect bottom nav item
                bottomNavigationView.setSelectedItemId(R.id.home);
                fragmentHome = new fragment_home();
                packedData(fragmentHome);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragmentHome).commit();
            }
        });
    }


    private void packedData(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("arrAuthority",arrAuthority);
        fragment.setArguments(bundle);
    }
    private void parseData(){
        //Get arrAuthority from activity_login
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            arrAuthority = extras.getStringArrayList("authority");
        }
    }
}