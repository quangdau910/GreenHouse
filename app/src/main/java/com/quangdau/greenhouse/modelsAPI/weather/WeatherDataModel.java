package com.quangdau.greenhouse.modelsAPI.weather;

public class WeatherDataModel {
    private ArrWeather[] weather;
    private String name;
    private ObjMain main;
    private ObjCoord coord;



    public ObjCoord getCoord() {
        return coord;
    }
    public ObjMain getMain() {
        return main;
    }
    public ArrWeather[] getWeather() {
        return weather;
    }
    public String getName() {
        return name;
    }



}
