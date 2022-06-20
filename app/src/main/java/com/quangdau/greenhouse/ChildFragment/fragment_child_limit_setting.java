package com.quangdau.greenhouse.ChildFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.quangdau.greenhouse.R;

import java.util.ArrayList;


public class fragment_child_limit_setting extends Fragment {
    //Declare variables
    ExtendedFloatingActionButton fabSaveChange;
    EditText editTextMin1, editTextMin2, editTextMin3, editTextMin4, editTextMax1, editTextMax2, editTextMax3, editTextMax4;
    SwitchCompat switchStatusSoilMoisture1, switchStatusSoilMoisture2, switchStatusSoilMoisture3, switchStatusSoilMoisture4;
    Spinner spinnerHouseID;
    View button;
    ArrayList<String> arrAuthority;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_child_limit_setting, container, false);
        //Assign variables
        button = getActivity().findViewById(R.id.buttonLimitSetting);
        spinnerHouseID = view.findViewById(R.id.spinnerLimitSetting);
        fabSaveChange = view.findViewById(R.id.fabLimitSaveChange);
        editTextMin1 = view.findViewById(R.id.editTextLimitMin1);
        editTextMin2 = view.findViewById(R.id.editTextLimitMin2);
        editTextMin3 = view.findViewById(R.id.editTextLimitMin3);
        editTextMin4 = view.findViewById(R.id.editTextLimitMin4);
        editTextMax1 = view.findViewById(R.id.editTextLimitMax1);
        editTextMax2 = view.findViewById(R.id.editTextLimitMax2);
        editTextMax3 = view.findViewById(R.id.editTextLimitMax3);
        editTextMax4 = view.findViewById(R.id.editTextLimitMax4);
        //Set adapter spinner
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrAuthority);
        spinnerHouseID.setAdapter(spinnerAdapter);









        return view;
    }








    private void parseData(){
        assert getArguments() != null;
        arrAuthority = getArguments().getStringArrayList("arrAuthority");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("gh", "limit resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("gh", "limit pause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("gh", "limit destroy");
    }
}