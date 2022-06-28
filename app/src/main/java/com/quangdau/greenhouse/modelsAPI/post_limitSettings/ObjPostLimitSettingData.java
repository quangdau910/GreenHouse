package com.quangdau.greenhouse.modelsAPI.post_limitSettings;

public class ObjPostLimitSettingData {
    private Integer min, max;
    private Boolean status;
    private String sensor;


    public ObjPostLimitSettingData(Integer min, Integer max, Boolean status, String sensor) {
        this.min = min;
        this.max = max;
        this.status = status;
        this.sensor = sensor;
    }
}
