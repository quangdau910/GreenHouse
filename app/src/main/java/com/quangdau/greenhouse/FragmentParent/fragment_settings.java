package com.quangdau.greenhouse.FragmentParent;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.quangdau.greenhouse.FragmentChild.fragment_child_limit_setting;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;

import java.util.ArrayList;

public class fragment_settings extends Fragment {
    //Declare variables
    AppCompatButton buttonLimitSetting;
    ArrayList<String> arrAuthority;
    //Child Fragment
    fragment_child_limit_setting fragmentChildLimitSetting;
    //Communicate to fragments
    final String REQUEST_KEY_LIMIT_SETTING = "fragmentLimitSetting";
    Bundle bundleLimitSetting;
    //Other
    UserPreferences userPreferences;
    final String STATE_FRAGMENT = "SETTINGS_FRAGMENT";
    final String NULL_STATE_FRAGMENT = "NULL";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        //Assign variables
        buttonLimitSetting = view.findViewById(R.id.buttonLimitSetting);
        bundleLimitSetting = new Bundle();
        fragmentChildLimitSetting = new fragment_child_limit_setting();
        userPreferences = new UserPreferences(getActivity());
        //Setting listener
        buttonLimitSetting.setOnClickListener(v -> {
            packedData(fragmentChildLimitSetting);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentSettingsContainer, fragmentChildLimitSetting).commit();
            buttonLimitSetting.setVisibility(View.GONE);

        });

        getActivity().getSupportFragmentManager().setFragmentResultListener(REQUEST_KEY_LIMIT_SETTING, this, (requestKey, bundle) -> {
            if ("buttonLimitBack Pressed".equals(bundle.getString("fragmentLimitSetting"))) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(fragmentChildLimitSetting).commit();
            }
        });

        return view;
    }

    private void parseData(){
        assert getArguments() != null;
        arrAuthority = getArguments().getStringArrayList("arrAuthority");
    }
    private void packedData(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("arrAuthority", arrAuthority);
        fragment.setArguments(bundle);
    }

    @Override
        public void onResume() {
        super.onResume();
        Log.e("gh", "setting resume");
        userPreferences.setStateFragment(STATE_FRAGMENT);
    }

    @Override
    public void onPause() {
        super.onPause();
        userPreferences.setStateFragment(NULL_STATE_FRAGMENT);
        Log.e("gh", "setting pause");
        getActivity().getSupportFragmentManager().beginTransaction().remove(fragmentChildLimitSetting).commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("gh", "setting destroy");
    }
}