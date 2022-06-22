package com.quangdau.greenhouse.modelsAPI.post_limitSettings;

import java.util.ArrayList;

public class limitSettingsPost {
    private ArrayList<objPostLimitSettingData> data;
    private String houseID, token, request;

    public limitSettingsPost(ArrayList<objPostLimitSettingData> data, String houseID, String token, String request) {
        this.data = data;
        this.houseID = houseID;
        this.token = token;
        this.request = request;
    }

    public void setData(ArrayList<objPostLimitSettingData> data) {
        this.data = data;
    }
}
