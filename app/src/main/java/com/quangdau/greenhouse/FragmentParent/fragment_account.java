package com.quangdau.greenhouse.FragmentParent;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quangdau.greenhouse.Services.ServiceRemoveToken;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.activity_pages.activity_login;

public class fragment_account extends Fragment {
    //Declare variables
    UserPreferences userPreferences;
    final String STATE_FRAGMENT = "ACCOUNT_FRAGMENT";
    final String NULL_STATE_FRAGMENT = "NULL";
    AppCompatButton buttonLogout;
    Intent intentRemoveToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        //Assign variables
        userPreferences = new UserPreferences(getActivity());
        buttonLogout = view.findViewById(R.id.buttonLogout);
        intentRemoveToken = new Intent(getActivity(), ServiceRemoveToken.class);


        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startService(intentRemoveToken);
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
        Log.e("gh", "Account: resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("gh", "Account: paused");
        userPreferences.setStateFragment(NULL_STATE_FRAGMENT);
        //removeToken(userPreferences.getToken());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("gh", "Account: destroy");
    }
}