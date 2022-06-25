package com.quangdau.greenhouse.FragmentParent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.Spinner.spinnerLimitSetting.CategorySpinner;
import com.quangdau.greenhouse.Spinner.spinnerLimitSetting.CategorySpinnerAdapter;
import com.quangdau.greenhouse.language.Language;

import java.util.ArrayList;
import java.util.List;

public class fragment_account extends Fragment {
    //Declare variables
    UserPreferences userPreferences;
    final String STATE_FRAGMENT = "ACCOUNT_FRAGMENT";
    final String NULL_STATE_FRAGMENT = "NULL";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        userPreferences = new UserPreferences(getActivity());

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