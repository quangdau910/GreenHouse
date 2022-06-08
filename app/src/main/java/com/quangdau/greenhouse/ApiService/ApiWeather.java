package com.quangdau.greenhouse.ApiService;

import com.quangdau.greenhouse.modelsAPI.weather.weatherDataModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiWeather {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("weather")
    Call<weatherDataModel> getWeather(@Query("lat") double lat,
                                      @Query("lon") double lon,
                                      @Query("appid") String appid);


}
