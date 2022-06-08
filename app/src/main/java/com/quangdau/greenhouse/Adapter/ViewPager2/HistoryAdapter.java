package com.quangdau.greenhouse.Adapter.ViewPager2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.quangdau.greenhouse.ChildFragment.fragment_child_history_login;



public class HistoryAdapter extends FragmentStateAdapter {

        private String token;
        Bundle bundle;

        public void setToken(String token) {
                this.token = token;
        }

        public HistoryAdapter(@NonNull FragmentActivity fragmentActivity) {
                super(fragmentActivity);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
                switch (position){
                        case 0:
                                fragment_child_history_login fragment_child_history_login = new fragment_child_history_login();
                                bundle = new Bundle();
                                bundle.putString("token", token);
                                fragment_child_history_login.setArguments(bundle);
                                return fragment_child_history_login;

                        default:
                                return null;
                }
        }

        @Override
        public int getItemCount() {
                return 1;
        }
}
