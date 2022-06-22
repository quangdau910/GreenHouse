package com.quangdau.greenhouse.Adapter.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.quangdau.greenhouse.FragmentChild.fragment_child_graph1;
import com.quangdau.greenhouse.FragmentChild.fragment_child_graph2;

import java.util.ArrayList;

public class GraphAdapter extends FragmentStateAdapter {
        public final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        public final ArrayList<String> fragmentTitle = new ArrayList<>();
        Bundle bundle;


        public GraphAdapter(@NonNull FragmentActivity fragmentActivity) {
                super(fragmentActivity);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
                switch (position){
                        case 0:
                                fragment_child_graph1 fragment_child_graph1 = new fragment_child_graph1();
                                bundle = new Bundle();
                                fragment_child_graph1.setArguments(bundle);
                                return fragment_child_graph1;

                        case 1:
                                fragment_child_graph2 fragment_child_graph2 = new fragment_child_graph2();
                                bundle = new Bundle();
                                fragment_child_graph2.setArguments(bundle);
                                return fragment_child_graph2;

                        default:
                                Log.e("gh", "return none!");
                                return fragmentArrayList.get(position);
                }
        }

        @Override
        public int getItemCount() {
                return fragmentTitle.size();
        }

        public void addFragment(Fragment fragment, String title){
                fragmentArrayList.add(fragment);
                fragmentTitle.add(title);
        }
}
