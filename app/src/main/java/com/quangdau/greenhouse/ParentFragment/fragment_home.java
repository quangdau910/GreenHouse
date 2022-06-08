package com.quangdau.greenhouse.ParentFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.quangdau.greenhouse.Adapter.ViewPager2.HomeAdapter;
import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.ChildFragment.fragment_child_home1;
import com.quangdau.greenhouse.ChildFragment.fragment_child_home2;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.modelsAPI.get_RSSI.RSSIData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_home extends Fragment {
    //Declare variables
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    String token;
    ArrayList<String> arrAuthority;
    final Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        //Link
        tabLayout = view.findViewById(R.id.tabLayoutHome);
        viewPager2 = view.findViewById(R.id.viewPager2Home);
        //Get data from activity_main (token, arrAuthority)
        parseData();
        //Setting adapter
        HomeAdapter adapter = new HomeAdapter(getActivity());
        adapter.setToken(token);
        viewPager2.setAdapter(adapter);
        for (int i = 0; i < arrAuthority.size(); i++){
            switch (i){
                case 0:
                    adapter.addFragment(new fragment_child_home1(), arrAuthority.get(i));
                    break;
                case 1:
                    adapter.addFragment(new fragment_child_home2(), arrAuthority.get(i));
                    break;
                default:
                    break;
            }
        }
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(adapter.fragmentTitle.get(position));
            }
        }).attach();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                getRSSIData(token, adapter.fragmentTitle.get(tabLayout.getSelectedTabPosition()));
            }
        }, 1000);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        return view;
    }

    private void parseData(){
        assert getArguments() != null;
        token = getArguments().getString("token");
        arrAuthority = getArguments().getStringArrayList("arrAuthority");
    }

    private void getRSSIData(String token, String houseID){
        ApiServer get = ApiServer.retrofit.create(ApiServer.class);
        Call<RSSIData> call = get.getRSSIData(token, "GetRSSIData", houseID);
        call.enqueue(new Callback<RSSIData>() {
            @Override
            public void onResponse(Call<RSSIData> call, Response<RSSIData> response) {
                assert response.body() != null;
                if (response.body().getResponse().equals("Response RSSI Data")){
                    updateRSSIUI(response.body().getData().getValue());
                }else ;
            }

            @Override
            public void onFailure(Call<RSSIData> call, Throwable t) {

            }
        });
    }
    private void updateRSSIUI(Integer value){
        TabLayout.Tab tab = tabLayout.getTabAt(tabLayout.getSelectedTabPosition());
        if (value >= 0 && value <= 25) tab.setIcon(R.drawable.ic_signal_wifi_1_bar);
        if (value > 25 && value <= 50) tab.setIcon(R.drawable.ic_signal_wifi_2_bar);
        if (value > 50 && value <= 75) tab.setIcon(R.drawable.ic_signal_wifi_3_bar);
        if (value > 75 && value <= 100) tab.setIcon(R.drawable.ic_signal_wifi_4_bar);
    }
}