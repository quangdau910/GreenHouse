package com.quangdau.greenhouse.modelsAPI.get_graph;

public class DataReal {
    private long time;
    private float value;

    public DataReal(long time, float value) {
        this.time = time;
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "dataReal{" +
                "time=" + time +
                ", value=" + value +
                '}';
    }
}
