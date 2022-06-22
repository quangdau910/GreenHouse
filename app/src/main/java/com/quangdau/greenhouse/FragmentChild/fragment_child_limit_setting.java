package com.quangdau.greenhouse.FragmentChild;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.modelsAPI.get_limitSettings.limitSettingsData;
import com.quangdau.greenhouse.modelsAPI.get_limitSettings.objGetLimitSettingData;
import com.quangdau.greenhouse.modelsAPI.post_limitSettings.limitSettingsPost;
import com.quangdau.greenhouse.modelsAPI.post_limitSettings.objPostLimitSettingData;
import com.quangdau.greenhouse.modelsAPI.res_limitSettingsPost.resLimitSettingsPost;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_child_limit_setting extends Fragment {
    //Declare variables
    ImageButton buttonLimitBack;
    ExtendedFloatingActionButton fabSaveChange;
    EditText editTextMin1, editTextMin2, editTextMin3, editTextMin4, editTextMax1, editTextMax2, editTextMax3, editTextMax4;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchStatusSoilMoisture1, switchStatusSoilMoisture2, switchStatusSoilMoisture3, switchStatusSoilMoisture4;
    Spinner spinnerHouseID;
    View buttonLimitSetting;
    //Communicate fragment
    final String REQUEST_KEY = "fragmentLimitSetting";
    Bundle bundle;
    //Other
    Boolean flatCheckChangeDataLimitSettings;
    ColorStateList cslFABSaveChange;
    AlertDialog.Builder builder;
    ArrayList<String> arrAuthority;
    String token;



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
        buttonLimitSetting = getActivity().findViewById(R.id.buttonLimitSetting);
        spinnerHouseID = view.findViewById(R.id.spinnerLimitHouseID);
        buttonLimitBack = view.findViewById(R.id.buttonLimitBack);
        fabSaveChange = view.findViewById(R.id.fabLimitSaveChange);
        editTextMin1 = view.findViewById(R.id.editTextLimitMin1);
        editTextMin2 = view.findViewById(R.id.editTextLimitMin2);
        editTextMin3 = view.findViewById(R.id.editTextLimitMin3);
        editTextMin4 = view.findViewById(R.id.editTextLimitMin4);
        editTextMax1 = view.findViewById(R.id.editTextLimitMax1);
        editTextMax2 = view.findViewById(R.id.editTextLimitMax2);
        editTextMax3 = view.findViewById(R.id.editTextLimitMax3);
        editTextMax4 = view.findViewById(R.id.editTextLimitMax4);
        switchStatusSoilMoisture1 = view.findViewById(R.id.switchLimitStatusSoilMoisture1);
        switchStatusSoilMoisture2 = view.findViewById(R.id.switchLimitStatusSoilMoisture2);
        switchStatusSoilMoisture3 = view.findViewById(R.id.switchLimitStatusSoilMoisture3);
        switchStatusSoilMoisture4 = view.findViewById(R.id.switchLimitStatusSoilMoisture4);
        flatCheckChangeDataLimitSettings = false;
        bundle = new Bundle();
        //Set adapter spinner
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrAuthority);
        spinnerHouseID.setAdapter(spinnerAdapter);
        //UpdateUI fabSaveChange
        fabSaveChange.shrink();
        cslFABSaveChange = ColorStateList.valueOf(getResources().getColor(R.color.green_10));
        //Set listener
        editTextMin1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from edittext
                    editTextMin1.clearFocus();
                }
                if(!flatCheckChangeDataLimitSettings) updateUIButtonSaveChange();
                return false;
            }
        });
        editTextMin2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from edittext
                    editTextMin2.clearFocus();
                }
                if(!flatCheckChangeDataLimitSettings) updateUIButtonSaveChange();
                return false;
            }
        });
        editTextMin3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from edittext
                    editTextMin3.clearFocus();
                }
                if(!flatCheckChangeDataLimitSettings) updateUIButtonSaveChange();
                return false;
            }
        });
        editTextMin4.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from edittext
                    editTextMin4.clearFocus();
                }
                if(!flatCheckChangeDataLimitSettings) updateUIButtonSaveChange();
                return false;
            }
        });
        editTextMax1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from edittext
                    editTextMax1.clearFocus();
                }
                if(!flatCheckChangeDataLimitSettings) updateUIButtonSaveChange();
                return false;
            }
        });
        editTextMax2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from edittext
                    editTextMax2.clearFocus();
                }
                if(!flatCheckChangeDataLimitSettings) updateUIButtonSaveChange();
                return false;
            }
        });
        editTextMax3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from edittext
                    editTextMax3.clearFocus();
                }
                if(!flatCheckChangeDataLimitSettings) updateUIButtonSaveChange();
                return false;
            }
        });
        editTextMax4.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from edittext
                    editTextMax4.clearFocus();
                }
                if(!flatCheckChangeDataLimitSettings) updateUIButtonSaveChange();
                return false;
            }
        });
        switchStatusSoilMoisture1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchStatusSoilMoisture1.isPressed()){
                    if (!flatCheckChangeDataLimitSettings) updateUIButtonSaveChange();
                }
            }
        });
        switchStatusSoilMoisture2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchStatusSoilMoisture2.isPressed()){
                    if (!flatCheckChangeDataLimitSettings) updateUIButtonSaveChange();
                }
            }
        });
        switchStatusSoilMoisture3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchStatusSoilMoisture3.isPressed()){
                    if (!flatCheckChangeDataLimitSettings) updateUIButtonSaveChange();
                }
            }
        });
        switchStatusSoilMoisture4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchStatusSoilMoisture4.isPressed()){
                    if (!flatCheckChangeDataLimitSettings) updateUIButtonSaveChange();
                }
            }
        });

        buttonLimitBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("fragmentLimitSetting", "buttonLimitBack Pressed");
                getActivity().getSupportFragmentManager().setFragmentResult(REQUEST_KEY, bundle);
            }
        });

        fabSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flatCheckChangeDataLimitSettings){
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("AlertDialogExample");
                    alert.show();
                }else{

                }
            }
        });

        //Setting alert dialog
        builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to save change?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {





                        limitSettingsPost mLimitSettingsPost = new limitSettingsPost(getDataSettings(), spinnerHouseID.getSelectedItem().toString(),token, "123");
                        ApiServer post = ApiServer.retrofit.create(ApiServer.class);
                        Call <resLimitSettingsPost> postLimitSettings = post.postLimitSettings(mLimitSettingsPost);
                        postLimitSettings.enqueue(new Callback<resLimitSettingsPost>() {
                            @Override
                            public void onResponse(Call<resLimitSettingsPost> call, Response<resLimitSettingsPost> response) {
                                if (response.body() != null){
                                    //Do something
                                }
                            }

                            @Override
                            public void onFailure(Call<resLimitSettingsPost> call, Throwable t) {

                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(),"you choose no action for alert box", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });



        //Announce fragmentLimitSetting is created
        bundle.putString("fragmentLimitSetting", "fragmentLimitSettingCreatedView");
        getActivity().getSupportFragmentManager().setFragmentResult(REQUEST_KEY, bundle);
        return view;
    }






    private void getLimitSettingsData(){
        ApiServer get = ApiServer.retrofit.create(ApiServer.class);
        Call<limitSettingsData> call = get.getLimitSettingsData(token, "GetLimitSettingsData", spinnerHouseID.getSelectedItem().toString());
        call.enqueue(new Callback<limitSettingsData>() {
            @Override
            public void onResponse(Call<limitSettingsData> call, Response<limitSettingsData> response) {
                if (response.body() != null){
                    Log.e("gh", ""+ response.body().getResponse());
                    Log.e("gh", "objData size: " + response.body().getData().size());
                    //updateUILimitSettings(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<limitSettingsData> call, Throwable t) {

            }
        });


    }


    private void updateUILimitSettings(ArrayList<objGetLimitSettingData> objGetLimitSettingData){
        for (int i = 0; i < objGetLimitSettingData.size(); i++){
            switch(objGetLimitSettingData.get(i).getSensor()){
                case "soil_moisture1":
                    editTextMin1.setText(objGetLimitSettingData.get(i).getMin());
                    editTextMax1.setText(objGetLimitSettingData.get(i).getMax());
                    switchStatusSoilMoisture1.setChecked(objGetLimitSettingData.get(i).getStatus());
                    break;
                case "soil_moisture2":
                    editTextMin2.setText(objGetLimitSettingData.get(i).getMin());
                    editTextMax2.setText(objGetLimitSettingData.get(i).getMax());
                    switchStatusSoilMoisture2.setChecked(objGetLimitSettingData.get(i).getStatus());
                    break;
                case "soil_moisture3":
                    editTextMin3.setText(objGetLimitSettingData.get(i).getMin());
                    editTextMax3.setText(objGetLimitSettingData.get(i).getMax());
                    switchStatusSoilMoisture3.setChecked(objGetLimitSettingData.get(i).getStatus());
                    break;
                case "soil_moisture4":
                    editTextMin4.setText(objGetLimitSettingData.get(i).getMin());
                    editTextMax4.setText(objGetLimitSettingData.get(i).getMax());
                    switchStatusSoilMoisture4.setChecked(objGetLimitSettingData.get(i).getStatus());
                    break;
            }
        }
    }


    private ArrayList<objPostLimitSettingData> getDataSettings(){
        objPostLimitSettingData dataSoilMoisture1 = new objPostLimitSettingData(Integer.parseInt(editTextMin1.getText().toString()), Integer.parseInt(editTextMax1.getText().toString()), switchStatusSoilMoisture1.isChecked(), "soil_moisture1");
        objPostLimitSettingData dataSoilMoisture2 = new objPostLimitSettingData(Integer.parseInt(editTextMin2.getText().toString()), Integer.parseInt(editTextMax2.getText().toString()), switchStatusSoilMoisture2.isChecked(), "soil_moisture2");
        objPostLimitSettingData dataSoilMoisture3 = new objPostLimitSettingData(Integer.parseInt(editTextMin3.getText().toString()), Integer.parseInt(editTextMax3.getText().toString()), switchStatusSoilMoisture3.isChecked(), "soil_moisture3");
        objPostLimitSettingData dataSoilMoisture4 = new objPostLimitSettingData(Integer.parseInt(editTextMin4.getText().toString()), Integer.parseInt(editTextMax4.getText().toString()), switchStatusSoilMoisture4.isChecked(), "soil_moisture4");
        ArrayList<objPostLimitSettingData> data = new ArrayList<>();
        data.add(dataSoilMoisture1);
        data.add(dataSoilMoisture2);
        data.add(dataSoilMoisture3);
        data.add(dataSoilMoisture4);
        return data;
    }

    private void updateUIButtonMode(){
        buttonLimitSetting.setVisibility(View.VISIBLE);
    }

    private void updateUIButtonSaveChange(){
        fabSaveChange.extend();
        fabSaveChange.setBackgroundTintList(cslFABSaveChange);
        flatCheckChangeDataLimitSettings = true;
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
        updateUIButtonMode();
        Log.e("gh", "limit destroy");
    }
}