package com.quangdau.greenhouse.ParentFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.quangdau.greenhouse.ChildFragment.fragment_child_limit_setting;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;

import java.util.ArrayList;

public class fragment_settings extends Fragment {
    //Declare variables
    Button buttonLimitSetting;
    ArrayList<String> arrAuthority;
    //Child Fragment
    fragment_child_limit_setting fragmentChildLimitSetting;
    //Other
    UserPreferences userPreferences;
    final String STATE_FRAGMENT = "SETTINGS_FRAGMENT";
    final String NULL_STATE_FRAGMENT = "NULL";
    AlertDialog.Builder builder;

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
        fragmentChildLimitSetting = new fragment_child_limit_setting();
        buttonLimitSetting = view.findViewById(R.id.buttonLimitSetting);
        userPreferences = new UserPreferences(getActivity());
        builder = new AlertDialog.Builder(getActivity());


        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to close this application ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),"you choose yes action for alertbox",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        //dialog.cancel();
                        Toast.makeText(getActivity(),"you choose no action for alertbox",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        /*//Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("AlertDialogExample");
        alert.show();*/



        buttonLimitSetting.setOnClickListener(v -> {
            packedData(fragmentChildLimitSetting);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentSettingsContainer, fragmentChildLimitSetting).commit();
            buttonLimitSetting.setVisibility(View.GONE);
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