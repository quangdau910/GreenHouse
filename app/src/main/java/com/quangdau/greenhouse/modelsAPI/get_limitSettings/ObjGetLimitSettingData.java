package com.quangdau.greenhouse.modelsAPI.get_limitSettings;

public class ObjGetLimitSettingData {
    private String house_id, sensor;
    private Integer min, max;
    private Boolean status;

    public String getHouse_id() {
        return house_id;
    }

    public String getSensor() {
        return sensor;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public Boolean getStatus() {
        return status;
    }
}
