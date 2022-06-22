package com.quangdau.greenhouse.modelsAPI.get_limitSettings;

import java.util.ArrayList;

public class limitSettingsData {
    private String houseID, response;
    private ArrayList<objGetLimitSettingData> data;


    public String getHouseID() {
        return houseID;
    }

    public String getResponse() {
        return response;
    }

    public ArrayList<objGetLimitSettingData> getData() {
        return data;
    }
}
