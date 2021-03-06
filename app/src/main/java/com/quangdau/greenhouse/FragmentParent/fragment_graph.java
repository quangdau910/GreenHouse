package com.quangdau.greenhouse.FragmentParent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.quangdau.greenhouse.Adapter.ViewPager2.GraphAdapter;
import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.FragmentChild.fragment_child_graph1;
import com.quangdau.greenhouse.FragmentChild.fragment_child_graph2;
import com.quangdau.greenhouse.Other.NetworkConnection;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.modelsAPI.get_RSSI.RSSIData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_graph extends Fragment {
    //Declare variables
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ArrayList<String> arrAuthority;
    UserPreferences userPreferences;
    NetworkConnection networkConnection;
    String timeRSSI;
    //Handler post delay
    Runnable runnable;
    Handler mainHandler;
    final long TIME_DELAY = 4000; //1000 millis
    final String STATE_FRAGMENT = "GRAPH_FRAGMENT";
    final String NULL_STATE_FRAGMENT = "NULL";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_graph, container, false);
        //Assign variables
        tabLayout = view.findViewById(R.id.tabLayoutGraph);
        viewPager2 = view.findViewById(R.id.viewPager2Graph);
        userPreferences = new UserPreferences(getActivity());
        networkConnection = new NetworkConnection(getActivity());
        //Get arrAuthority
        parseData();
        //Setting adapter
        GraphAdapter adapter = new GraphAdapter(getActivity());
        viewPager2.setAdapter(adapter);
        for (int i = 0; i < arrAuthority.size(); i++){
            switch (i){
                case 0:
                    adapter.addFragment(new fragment_child_graph1(), arrAuthority.get(i));
                    break;
                case 1:
                    adapter.addFragment(new fragment_child_graph2(), arrAuthority.get(i));
                    break;
                default:
                    break;
            }
        }
        viewPager2.setOffscreenPageLimit(2);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (adapter.fragmentTitle.get(position)){
                    case "house1":
                    {
                        tab.setText(getResources().getString(R.string.house_1));
                    }break;
                    case "house2":
                    {
                        tab.setText(getResources().getString(R.string.house_2));
                    }break;
                }
            }
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (userPreferences.getStateFragment().equals(STATE_FRAGMENT)){
                    getRSSIData(userPreferences.getToken(), adapter.fragmentTitle.get(tabLayout.getSelectedTabPosition()));
                    timeRSSI = "0";
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Do something after time
        runnable = new Runnable() {
            @Override
            public void run() {
                if (userPreferences.getStateFragment().equals(STATE_FRAGMENT)){
                    getRSSIData(userPreferences.getToken(), adapter.fragmentTitle.get(tabLayout.getSelectedTabPosition()));
                }
                mainHandler.postDelayed(this, TIME_DELAY);
            }
        };
        mainHandler = new Handler(Looper.getMainLooper());

        return view;
    }

    private void getRSSIData(String token, String houseID){
        if (networkConnection.isNetworkConnected()){
            ApiServer get = ApiServer.retrofit.create(ApiServer.class);
            Call<RSSIData> call = get.getRSSIData(token, "GetRSSIData", houseID);
            call.enqueue(new Callback<RSSIData>() {
                @Override
                public void onResponse(Call<RSSIData> call, Response<RSSIData> response) {
                    if (response.body() != null && response.body().getResponse().equals("Response RSSI Data") ){
                        updateRSSIUI(response.body().getData().getValue(), response.body().getData().getTime());
                        timeRSSI = response.body().getData().getTime();
                    }else {
                        networkConnection.checkStatusCode(response.code());
                    }
                }

                @Override
                public void onFailure(Call<RSSIData> call, Throwable t) {
                    Log.e("gh", "Home RSSI: "+ t);
                }
            });
        }
    }

    private void updateRSSIUI(Integer value, String time){
        TabLayout.Tab tab = tabLayout.getTabAt(tabLayout.getSelectedTabPosition());
        assert tab != null;
        if (!time.equals(timeRSSI)){
            if (value >= 0 && value <= 25) tab.setIcon(R.drawable.ic_signal_wifi_1_bar);
            if (value > 25 && value <= 50) tab.setIcon(R.drawable.ic_signal_wifi_2_bar);
            if (value > 50 && value <= 75) tab.setIcon(R.drawable.ic_signal_wifi_3_bar);
            if (value > 75 && value <= 100) tab.setIcon(R.drawable.ic_signal_wifi_4_bar);
        }else {
            tab.setIcon(R.drawable.ic_signal_wifi_bad);
        }
    }


    private void parseData(){
        assert getArguments() != null;
        arrAuthority = getArguments().getStringArrayList("arrAuthority");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("gh", "Graph: resume");
        userPreferences.setStateFragment(STATE_FRAGMENT);
        mainHandler.post(runnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("gh", "Graph: pause");
        userPreferences.setStateFragment(NULL_STATE_FRAGMENT);
        mainHandler.removeCallbacks(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("gh", "Graph: destroy");
    }
}