package com.quangdau.greenhouse.modelsAPI.get_limitSettings;

import java.util.ArrayList;

public class LimitSettingsData {
    private String houseID, response;
    private ArrayList<ObjGetLimitSettingData> data;


    public String getHouseID() {
        return houseID;
    }

    public String getResponse() {
        return response;
    }

    public ArrayList<ObjGetLimitSettingData> getData() {
        return data;
    }
}
