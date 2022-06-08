package com.quangdau.greenhouse.ParentFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.quangdau.greenhouse.Adapter.ViewPager2.HistoryAdapter;


import com.quangdau.greenhouse.R;

public class fragment_history extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_history, container, false);
        tabLayout = view.findViewById(R.id.tabLayoutHistory);
        viewPager2 = view.findViewById(R.id.viewPager2History);
        parseData();

        //Setting adapter
        HistoryAdapter adapter = new HistoryAdapter(getActivity());
        adapter.setToken(token);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Login");
                        break;
                    default:
                        break;
                }
            }
        }).attach();
        return view;
    }
    private void parseData(){
        assert getArguments() != null;
        token = getArguments().getString("token");
    }
}