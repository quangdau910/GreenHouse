package com.quangdau.greenhouse.modelsAPI.get_history;

import java.util.ArrayList;

public class HistoryLoginData {
    private String response;
    private ArrayList<ObjHistoryLoginData> data;

    public ArrayList<ObjHistoryLoginData> getData() {
        return data;
    }

    public String getResponse() {
        return response;
    }
}
