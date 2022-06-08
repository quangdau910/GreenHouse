package com.quangdau.greenhouse.modelsAPI.weather;

public class weatherDataModel {
    private arrWeather[] weather;
    private String name;
    private objMain main;
    private objCoord coord;



    public objCoord getCoord() {
        return coord;
    }
    public objMain getMain() {
        return main;
    }
    public arrWeather[] getWeather() {
        return weather;
    }
    public String getName() {
        return name;
    }



}
