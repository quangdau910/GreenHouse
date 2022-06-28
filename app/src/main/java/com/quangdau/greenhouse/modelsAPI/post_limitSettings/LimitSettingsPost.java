package com.quangdau.greenhouse.modelsAPI.post_limitSettings;

import java.util.ArrayList;

public class LimitSettingsPost {
    private ArrayList<ObjPostLimitSettingData> data;
    private String houseID, token, request;

    public LimitSettingsPost(ArrayList<ObjPostLimitSettingData> data, String houseID, String token, String request) {
        this.data = data;
        this.houseID = houseID;
        this.token = token;
        this.request = request;
    }

    public void setData(ArrayList<ObjPostLimitSettingData> data) {
        this.data = data;
    }
}
