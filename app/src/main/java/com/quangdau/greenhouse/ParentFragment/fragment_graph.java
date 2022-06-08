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
import com.quangdau.greenhouse.Adapter.ViewPager2.GraphAdapter;
import com.quangdau.greenhouse.ChildFragment.fragment_child_graph1;
import com.quangdau.greenhouse.ChildFragment.fragment_child_graph2;
import com.quangdau.greenhouse.R;

import java.util.ArrayList;


public class fragment_graph extends Fragment {
    //Declare variables
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    String token;
    ArrayList<String> arrAuthority;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_graph, container, false);
        //Link
        tabLayout = view.findViewById(R.id.tabLayoutGraph);
        viewPager2 = view.findViewById(R.id.viewPager2Graph);
        //Get data from activity_main (token, arrAuthority)
        parseData();
        //Setting adapter
        GraphAdapter adapter = new GraphAdapter(getActivity());
        adapter.setToken(token);
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
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(adapter.fragmentTitle.get(position));
            }
        }).attach();
        return view;
    }

    private void parseData(){
        assert getArguments() != null;
        token = getArguments().getString("token");
        arrAuthority = getArguments().getStringArrayList("arrAuthority");
    }
}