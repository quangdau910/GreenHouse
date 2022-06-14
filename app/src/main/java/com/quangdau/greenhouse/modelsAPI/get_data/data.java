package com.quangdau.greenhouse.modelsAPI.get_data;

public class data {
    private double temperature, humidity, soil_moisture1, light, soil_moisture2, soil_moisture3, soil_moisture4;
    private String houseID;
    private objDigitalData digitalData;

    public objDigitalData getDigitalData() {
        return digitalData;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getSoil_moisture1() {
        return soil_moisture1;
    }

    public double getLight() {
        return light;
    }

    public String getHouseID() {
        return houseID;
    }

    public double getSoil_moisture2() {
        return soil_moisture2;
    }

    public double getSoil_moisture3() {
        return soil_moisture3;
    }

    public double getSoil_moisture4() {
        return soil_moisture4;
    }
}
