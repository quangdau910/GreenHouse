package com.quangdau.greenhouse.ParentFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.quangdau.greenhouse.ChildFragment.fragment_child_limit_setting;
import com.quangdau.greenhouse.ChildFragment.fragment_child_mode_setting;
import com.quangdau.greenhouse.ChildFragment.fragment_child_timer_setting;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;

public class fragment_settings extends Fragment {
    //Declare variables
    Button buttonLimitSetting;
    //Child Fragment
    fragment_child_limit_setting fragmentChildLimitSetting = new fragment_child_limit_setting();
    fragment_child_timer_setting fragmentChildTimerSetting = new fragment_child_timer_setting();
    fragment_child_mode_setting fragmentChildModeSetting = new fragment_child_mode_setting();
    //Other
    UserPreferences userPreferences;
    final String STATE_FRAGMENT = "SETTINGS_FRAGMENT";
    final String NULL_STATE_FRAGMENT = "NULL";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        //Assign variables
        buttonLimitSetting = view.findViewById(R.id.buttonLimitSetting);
        userPreferences = new UserPreferences(getActivity());



        buttonLimitSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentSettingsContainer, fragmentChildLimitSetting).addToBackStack(null).commit();
                buttonLimitSetting.setVisibility(View.GONE);
            }
        });






        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        userPreferences.setStateFragment(STATE_FRAGMENT);
    }

    @Override
    public void onPause() {
        super.onPause();
        userPreferences.setStateFragment(NULL_STATE_FRAGMENT);
    }
}