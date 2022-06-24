package com.quangdau.greenhouse.modelsAPI.get_graph;

import java.util.List;

public class dataGraph {
    private String houseID;
    private String response;
    List<data> data;

    public dataGraph(String houseID, String response, List<com.quangdau.greenhouse.modelsAPI.get_graph.data> data) {
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

    public List<com.quangdau.greenhouse.modelsAPI.get_graph.data> getData() {
        return data;
    }

    public void setData(List<com.quangdau.greenhouse.modelsAPI.get_graph.data> data) {
        this.data = data;
    }
}