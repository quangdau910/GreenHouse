package com.quangdau.greenhouse.modelsAPI.get_graph;

public class Data {
    private String time;
    private float first;

    public Data(String time, float first) {
        this.time = time;
        this.first = first;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getFirst() {
        return first;
    }

    public void setFirst(float first) {
        this.first = first;
    }
}
