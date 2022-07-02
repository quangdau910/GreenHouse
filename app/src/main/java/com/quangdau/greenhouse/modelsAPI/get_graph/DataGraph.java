package com.quangdau.greenhouse.modelsAPI.get_graph;

import java.util.List;

public class DataGraph {
    private String houseID;
    private String response;
    List<Data> data;

    public DataGraph(String houseID, String response, List<Data> data) {
        this.houseID = houseID;
        this.response = response;
        this.data = data;
    }

    public String getHouseID() {
        return houseID;
    }

    public void setHouseID(String houseID) {
        this.houseID = houseID;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}