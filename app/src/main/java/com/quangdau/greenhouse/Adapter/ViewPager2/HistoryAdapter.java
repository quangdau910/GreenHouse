package com.quangdau.greenhouse.Adapter.ViewPager2;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.quangdau.greenhouse.ChildFragment.fragment_child_history_login;



public class HistoryAdapter extends FragmentStateAdapter {
        //Bundle bundle;

        public HistoryAdapter(@NonNull FragmentActivity fragmentActivity) {
                super(fragmentActivity);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
                switch (position){
                        case 0:
                                fragment_child_history_login fragment_child_history_login = new fragment_child_history_login();
                                /*bundle = new Bundle();
                                fragment_child_history_login.setArguments(bundle);*/
                                return fragment_child_history_login;
                        case 1:
                                //Add new fragment
                                return null;

                        default:
                                return null;
                }
        }

        @Override
        public int getItemCount() {
                return 1;
        }
}
