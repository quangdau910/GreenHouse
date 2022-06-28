package com.quangdau.greenhouse.FragmentParent;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.Spinner.spinnerLimitSetting.CategorySpinner;
import com.quangdau.greenhouse.Spinner.spinnerLimitSetting.CategorySpinnerAdapter;
import com.quangdau.greenhouse.activity_pages.activity_login;
import com.quangdau.greenhouse.activity_pages.activity_main;
import com.quangdau.greenhouse.language.Language;

import java.util.ArrayList;
import java.util.List;

public class fragment_account extends Fragment {
    //Declare variables
    UserPreferences userPreferences;
    final String STATE_FRAGMENT = "ACCOUNT_FRAGMENT";
    final String NULL_STATE_FRAGMENT = "NULL";
    AppCompatButton buttonLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        //Assign variables
        userPreferences = new UserPreferences(getActivity());
        buttonLogout = view.findViewById(R.id.buttonLogout);


        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage = new Intent(getActivity(), activity_login.class);
                startActivity(nextPage);
                getActivity().finish();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}