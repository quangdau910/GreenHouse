package com.quangdau.greenhouse.FragmentChild;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.Other.NetworkConnection;
import com.quangdau.greenhouse.Other.ToastError;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.Spinner.CategorySpinner;
import com.quangdau.greenhouse.Spinner.CategorySpinnerAdapter;
import com.quangdau.greenhouse.modelsAPI.get_limitSettings.LimitSettingsData;
import com.quangdau.greenhouse.modelsAPI.get_limitSettings.ObjGetLimitSettingData;
import com.quangdau.greenhouse.modelsAPI.post_limitSettings.LimitSettingsPost;
import com.quangdau.greenhouse.modelsAPI.post_limitSettings.ObjPostLimitSettingData;
import com.quangdau.greenhouse.modelsAPI.res_limitSettingsPost.resLimitSettingsPost;

import java.util.ArrayList;
import java.util.List;

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
    View buttonLimitSetting, tabLayoutSettings, textViewTabLayoutSettings;
    //Communicate fragment
    final String REQUEST_KEY = "fragmentLimitSetting";
    Bundle bundle;
    //Spinner
    List<CategorySpinner> listSpinner;
    CategorySpinnerAdapter categorySpinnerAdapter;
    //Other
    Boolean flagCheckChangeDataLimitSettings;
    ColorStateList cslFABSaveChangeOn, cslFABSaveChangeOff;
    AlertDialog.Builder builder;
    ArrayList<String> arrAuthority;
    UserPreferences userPreferences;
    ToastError toastError;
    NetworkConnection networkConnection;

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
        flagCheckChangeDataLimitSettings = false;
        bundle = new Bundle();
        userPreferences = new UserPreferences(getActivity());
        toastError = new ToastError(getActivity());
        networkConnection = new NetworkConnection(getActivity());
        //View fragmentSettings
        buttonLimitSetting = getActivity().findViewById(R.id.buttonLimitSetting);
        tabLayoutSettings = getActivity().findViewById(R.id.tabLayoutSetting);
        textViewTabLayoutSettings = getActivity().findViewById(R.id.textViewTabLayoutSettings);
        //Setting adapter spinner
        listSpinner = new ArrayList<>();
        for (int i = 0; i < arrAuthority.size(); i++){
            listSpinner.add(new CategorySpinner(getResources().getString(R.string.house_setting)+" "+(i+1)));
        }
        categorySpinnerAdapter = new CategorySpinnerAdapter(getActivity(), R.layout.spinner_item_selected, listSpinner);
        spinnerHouseID.setAdapter(categorySpinnerAdapter);
        spinnerHouseID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getLimitSettingsData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Setting fabSaveChange
        cslFABSaveChangeOn = ColorStateList.valueOf(getResources().getColor(R.color.green_10));
        cslFABSaveChangeOff = ColorStateList.valueOf(getResources().getColor(R.color.white_60));
        updateUIButtonSaveChange(false);
        //Setting button listener
        editTextMin1.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId== EditorInfo.IME_ACTION_DONE){
                //Clear focus here from edittext
                editTextMin1.clearFocus();
            }
            if(!flagCheckChangeDataLimitSettings) updateUIButtonSaveChange(true);
            return false;
        });
        editTextMin2.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId== EditorInfo.IME_ACTION_DONE){
                //Clear focus here from edittext
                editTextMin2.clearFocus();
            }
            if(!flagCheckChangeDataLimitSettings) updateUIButtonSaveChange(true);
            return false;
        });
        editTextMin3.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId== EditorInfo.IME_ACTION_DONE){
                //Clear focus here from edittext
                editTextMin3.clearFocus();
            }
            if(!flagCheckChangeDataLimitSettings) updateUIButtonSaveChange(true);
            return false;
        });
        editTextMin4.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId== EditorInfo.IME_ACTION_DONE){
                //Clear focus here from edittext
                editTextMin4.clearFocus();
            }
            if(!flagCheckChangeDataLimitSettings) updateUIButtonSaveChange(true);
            return false;
        });
        editTextMax1.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId== EditorInfo.IME_ACTION_DONE){
                //Clear focus here from edittext
                editTextMax1.clearFocus();
            }
            if(!flagCheckChangeDataLimitSettings) updateUIButtonSaveChange(true);
            return false;
        });
        editTextMax2.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId== EditorInfo.IME_ACTION_DONE){
                //Clear focus here from edittext
                editTextMax2.clearFocus();
            }
            if(!flagCheckChangeDataLimitSettings) updateUIButtonSaveChange(true);
            return false;
        });
        editTextMax3.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId== EditorInfo.IME_ACTION_DONE){
                //Clear focus here from edittext
                editTextMax3.clearFocus();
            }
            if(!flagCheckChangeDataLimitSettings) updateUIButtonSaveChange(true);
            return false;
        });
        editTextMax4.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId== EditorInfo.IME_ACTION_DONE){
                //Clear focus here from edittext
                editTextMax4.clearFocus();
            }
            if(!flagCheckChangeDataLimitSettings) updateUIButtonSaveChange(true);
            return false;
        });
        switchStatusSoilMoisture1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchStatusSoilMoisture1.isPressed()){
                if (!flagCheckChangeDataLimitSettings) updateUIButtonSaveChange(true);
            }
        });
        switchStatusSoilMoisture2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchStatusSoilMoisture2.isPressed()){
                if (!flagCheckChangeDataLimitSettings) updateUIButtonSaveChange(true);
            }
        });
        switchStatusSoilMoisture3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchStatusSoilMoisture3.isPressed()){
                if (!flagCheckChangeDataLimitSettings) updateUIButtonSaveChange(true);
            }
        });
        switchStatusSoilMoisture4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchStatusSoilMoisture4.isPressed()){
                if (!flagCheckChangeDataLimitSettings) updateUIButtonSaveChange(true);
            }
        });

        buttonLimitBack.setOnClickListener(v -> {
            bundle.putString("fragmentLimitSetting", "buttonLimitBack Pressed");
            getActivity().getSupportFragmentManager().setFragmentResult(REQUEST_KEY, bundle);
        });
        //Setting alert dialog
        builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to save change?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (checkNonNullEditText() && checkMinMaxSettings() && networkConnection.isNetworkConnected()){
                        LimitSettingsPost mLimitSettingsPost = new LimitSettingsPost(getDataSettingsChange(), getHouseID(categorySpinnerAdapter.getItemSelected()),userPreferences.getToken(), "SetLimitSettingsData");
                        ApiServer post = ApiServer.retrofit.create(ApiServer.class);
                        Call <resLimitSettingsPost> postLimitSettings = post.postLimitSettings(mLimitSettingsPost);
                        postLimitSettings.enqueue(new Callback<resLimitSettingsPost>() {
                            @Override
                            public void onResponse(Call<resLimitSettingsPost> call, Response<resLimitSettingsPost> response) {
                                if (response.body()!= null && response.body().getResponse().equals("SetLimitSettingsData")){
                                    updateUIButtonSaveChange(false);
                                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.data_saved), Toast.LENGTH_SHORT).show();
                                }else {
                                    networkConnection.checkStatusCode(response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<resLimitSettingsPost> call, Throwable t) {
                                Log.e("gh", "LimitSetting SetLimitSettingsData: "+ t);
                                toastError.makeText(getActivity().getResources().getString(R.string.no_response_from_server));
                            }
                        });

                    }else if(networkConnection.isNetworkConnected()){
                        if (!checkNonNullEditText()){
                            toastError.makeText(getString(R.string.empty_edit_text_limit_setting));
                            dialog.dismiss();

                        }else {
                            if (!checkMinMaxSettings()){
                                toastError.makeText(getString(R.string.min_larger_max));
                                dialog.dismiss();
                            }
                        }
                    }else {
                        toastError.makeText(getString(R.string.network_offline));
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", (dialog, id) -> dialog.dismiss());
        fabSaveChange.setOnClickListener(v -> {
            if (fabSaveChange.isExtended()){
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Alert");
                alert.show();
            }
        });
        //Announce fragmentLimitSetting is created
        bundle.putString("fragmentLimitSetting", "fragmentLimitSettingCreatedView");
        getActivity().getSupportFragmentManager().setFragmentResult(REQUEST_KEY, bundle);

        return view;
    }

    private void getLimitSettingsData(){
        ApiServer get = ApiServer.retrofit.create(ApiServer.class);
        Call<LimitSettingsData> call = get.getLimitSettingsData(userPreferences.getToken(), "GetLimitSettingsData", getHouseID(categorySpinnerAdapter.getItemSelected()));
        call.enqueue(new Callback<LimitSettingsData>() {
            @Override
            public void onResponse(Call<LimitSettingsData> call, Response<LimitSettingsData> response) {
                if (response.body().getResponse().equals("GetLimitSettingsData")){
                    updateUILimitSettings(response.body().getData());
                    updateUIButtonSaveChange(false);
                }else {
                    networkConnection.checkStatusCode(response.code());
                }
            }

            @Override
            public void onFailure(Call<LimitSettingsData> call, Throwable t) {
                toastError.makeText(getActivity().getResources().getString(R.string.no_response_from_server));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateUILimitSettings(ArrayList<ObjGetLimitSettingData> objGetLimitSettingData){
        for (int i = 0; i < objGetLimitSettingData.size(); i++){
            switch(objGetLimitSettingData.get(i).getSensor()){
                case "soil_moisture1":
                    editTextMin1.setText(objGetLimitSettingData.get(i).getMin().toString());
                    editTextMax1.setText(objGetLimitSettingData.get(i).getMax().toString());
                    switchStatusSoilMoisture1.setChecked(objGetLimitSettingData.get(i).getStatus());
                    break;
                case "soil_moisture2":
                    editTextMin2.setText(objGetLimitSettingData.get(i).getMin().toString());
                    editTextMax2.setText(objGetLimitSettingData.get(i).getMax().toString());
                    switchStatusSoilMoisture2.setChecked(objGetLimitSettingData.get(i).getStatus());
                    break;
                case "soil_moisture3":
                    editTextMin3.setText(objGetLimitSettingData.get(i).getMin().toString());
                    editTextMax3.setText(objGetLimitSettingData.get(i).getMax().toString());
                    switchStatusSoilMoisture3.setChecked(objGetLimitSettingData.get(i).getStatus());
                    break;
                case "soil_moisture4":
                    editTextMin4.setText(objGetLimitSettingData.get(i).getMin().toString());
                    editTextMax4.setText(objGetLimitSettingData.get(i).getMax().toString());
                    switchStatusSoilMoisture4.setChecked(objGetLimitSettingData.get(i).getStatus());
                    break;
            }
        }
    }

    private ArrayList<ObjPostLimitSettingData> getDataSettingsChange(){
        ObjPostLimitSettingData dataSoilMoisture1 = new ObjPostLimitSettingData(Integer.parseInt(editTextMin1.getText().toString()), Integer.parseInt(editTextMax1.getText().toString()), switchStatusSoilMoisture1.isChecked(), "soil_moisture1");
        ObjPostLimitSettingData dataSoilMoisture2 = new ObjPostLimitSettingData(Integer.parseInt(editTextMin2.getText().toString()), Integer.parseInt(editTextMax2.getText().toString()), switchStatusSoilMoisture2.isChecked(), "soil_moisture2");
        ObjPostLimitSettingData dataSoilMoisture3 = new ObjPostLimitSettingData(Integer.parseInt(editTextMin3.getText().toString()), Integer.parseInt(editTextMax3.getText().toString()), switchStatusSoilMoisture3.isChecked(), "soil_moisture3");
        ObjPostLimitSettingData dataSoilMoisture4 = new ObjPostLimitSettingData(Integer.parseInt(editTextMin4.getText().toString()), Integer.parseInt(editTextMax4.getText().toString()), switchStatusSoilMoisture4.isChecked(), "soil_moisture4");
        ArrayList<ObjPostLimitSettingData> data = new ArrayList<>();
        data.add(dataSoilMoisture1);
        data.add(dataSoilMoisture2);
        data.add(dataSoilMoisture3);
        data.add(dataSoilMoisture4);
        return data;
    }

    private boolean checkMinMaxSettings(){
        try {
            boolean temp1, temp2, temp3, temp4;
            temp1 = Integer.parseInt(editTextMin1.getText().toString()) < Integer.parseInt(editTextMax1.getText().toString());
            temp2 = Integer.parseInt(editTextMin2.getText().toString()) < Integer.parseInt(editTextMax2.getText().toString());
            temp3 = Integer.parseInt(editTextMin3.getText().toString()) < Integer.parseInt(editTextMax3.getText().toString());
            temp4 = Integer.parseInt(editTextMin4.getText().toString()) < Integer.parseInt(editTextMax4.getText().toString());
            return temp1 && temp2 && temp3 && temp4;
        }catch (Exception e){
            Log.e("gh", "LimitSetting checkMinMaxSetting: "+ e);
            return false;
        }
    }

    private boolean checkNonNullEditText(){
        return editTextMin1.length()!=0 && editTextMin2.length()!=0 && editTextMin3.length()!=0 && editTextMin4.length()!=0
                && editTextMax1.length()!=0 && editTextMax2.length()!=0 && editTextMax3.length()!=0 && editTextMax4.length()!=0;
    }

    
    private String getHouseID(String spinnerSelectedItem){
        switch (spinnerSelectedItem){
            case "House 1":
            case "Nhà kính 1":
                spinnerSelectedItem = "house1";
                break;
            case "House 2":
            case "Nhà kính 2":
                spinnerSelectedItem = "house2";
                break;
        }
        return spinnerSelectedItem;
    }

    private void updateUIFragmentSettings(){
        buttonLimitSetting.setVisibility(View.VISIBLE);
        tabLayoutSettings.setVisibility(View.VISIBLE);
        textViewTabLayoutSettings.setVisibility(View.VISIBLE);
    }

    private void updateUIButtonSaveChange(Boolean mode){
        if (mode){
            fabSaveChange.extend();
            fabSaveChange.setBackgroundTintList(cslFABSaveChangeOn);
            flagCheckChangeDataLimitSettings = true;
        }else{
            fabSaveChange.shrink();
            fabSaveChange.setBackgroundTintList(cslFABSaveChangeOff);
            flagCheckChangeDataLimitSettings = false;
        }
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
        //Log.e("gh", "limit pause");
        spinnerHouseID.setSelection(0);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("gh", "limit destroy");
        updateUIFragmentSettings();
    }
}