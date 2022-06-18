package com.quangdau.greenhouse.ApiService;

import com.quangdau.greenhouse.modelsAPI.post_writeDigital.writeDigitalPost;
import com.quangdau.greenhouse.modelsAPI.get_RSSI.RSSIData;
import com.quangdau.greenhouse.modelsAPI.get_history.historyLoginData;
import com.quangdau.greenhouse.modelsAPI.post_authen.authenPost;
import com.quangdau.greenhouse.modelsAPI.res_authority.authority;
import com.quangdau.greenhouse.modelsAPI.get_data.data;
import com.quangdau.greenhouse.modelsAPI.get_graph.graphData;
import com.quangdau.greenhouse.modelsAPI.res_writeDigital.resWriteDigital;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServer {
    OkHttpClient httpClient = new OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.169:1880/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build();

    @POST("pi4Server")
    Call <authority> postAuth(@Body authenPost authenPost);


    @GET("pi4Server")
    Call <data> getData(@Query("token") String token,
                        @Query("cmd") String cmd,
                        @Query("houseID") String houseID);

    @GET("pi4Server")
    Call <graphData> getGraphData(@Query("token") String token,
                                  @Query("cmd") String cmd,
                                  @Query("houseID") String houseID,
                                  @Query("key") String key,
                                  @Query("time") Array[] time);
    @GET("pi4Server")
    Call <ArrayList<historyLoginData>> getHistoryLogin(@Query("token") String token,
                                                  @Query("cmd") String cmd);

    @GET("pi4Server")
    Call <RSSIData> getRSSIData(@Query("token") String token,
                                @Query("cmd") String cmd,
                                @Query("houseID") String houseID);


    @POST("pi4Server")
    Call <resWriteDigital> postWriteDigital(@Body writeDigitalPost writeDigitalPost);










}
