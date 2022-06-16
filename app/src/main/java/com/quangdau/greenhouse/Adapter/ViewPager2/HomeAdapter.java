package com.quangdau.greenhouse.Adapter.ViewPager2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.quangdau.greenhouse.ChildFragment.fragment_child_home1;
import com.quangdau.greenhouse.ChildFragment.fragment_child_home2;

import java.util.ArrayList;

public class HomeAdapter extends FragmentStateAdapter {
        public final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        public final ArrayList<String> fragmentTitle = new ArrayList<>();
        Bundle bundle;


        public HomeAdapter(@NonNull FragmentActivity fragmentActivity) {
                super(fragmentActivity);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
                switch (position){
                        case 0:
                                fragment_child_home1 fragment_child_home1 = new fragment_child_home1();
                                bundle = new Bundle();
                                bundle.putString("houseID", "house1");
                                fragment_child_home1.setArguments(bundle);
                                return fragment_child_home1;

                        case 1:
                                fragment_child_home2 fragment_child_home2= new fragment_child_home2();
                                bundle = new Bundle();
                                bundle.putString("houseID", "house2");
                                fragment_child_home2.setArguments(bundle);
                                return fragment_child_home2;

                        default:
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
