package com.quangdau.greenhouse.ApiService;

import com.quangdau.greenhouse.digitalPost;
import com.quangdau.greenhouse.modelsAPI.get_history.historyLoginData;
import com.quangdau.greenhouse.modelsAPI.post_authen.authenPost;
import com.quangdau.greenhouse.modelsAPI.res_authority.authority;
import com.quangdau.greenhouse.modelsAPI.get_data.data;
import com.quangdau.greenhouse.modelsAPI.get_graph.graphData;
import com.quangdau.greenhouse.modelsAPI.res_write.resWriteDigital;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServer {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.137.238:1880/")
            .addConverterFactory(GsonConverterFactory.create())
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



    @POST("pi4Server")
    Call <resWriteDigital> postDigital(@Body digitalPost digitalPost);










}
