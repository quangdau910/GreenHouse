package com.quangdau.greenhouse.ApiService;

import com.quangdau.greenhouse.modelsAPI.get_graph.dataGraph;
import com.quangdau.greenhouse.modelsAPI.get_limitSettings.limitSettingsData;
import com.quangdau.greenhouse.modelsAPI.post_limitSettings.limitSettingsPost;
import com.quangdau.greenhouse.modelsAPI.post_writeDigital.writeDigitalPost;
import com.quangdau.greenhouse.modelsAPI.get_RSSI.RSSIData;
import com.quangdau.greenhouse.modelsAPI.get_history.historyLoginData;
import com.quangdau.greenhouse.modelsAPI.post_authen.authenPost;
import com.quangdau.greenhouse.modelsAPI.res_authenPost.resAuthorityPost;
import com.quangdau.greenhouse.modelsAPI.get_data.data;
import com.quangdau.greenhouse.modelsAPI.res_limitSettingsPost.resLimitSettingsPost;
import com.quangdau.greenhouse.modelsAPI.res_writeDigitalPost.resWriteDigitalPost;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServer {
    OkHttpClient httpsClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://192.168.0.169:1880/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpsClient)
            .build();

    @GET("pi4Server")
    Call <data> getData(@Query("token") String token,
                        @Query("request") String request,
                        @Query("houseID") String houseID);

    @GET("pi4Server")
    Call <dataGraph> getGraphData(@Query("token") String token,
                                  @Query("request") String request,
                                  @Query("houseID") String houseID,
                                  @Query("sensor") String sensor,
                                  @Query("time") String time);
    @GET("pi4Server")
    Call <ArrayList<historyLoginData>> getHistoryLogin(@Query("token") String token,
                                                       @Query("request") String request);

    @GET("pi4Server")
    Call <RSSIData> getRSSIData(@Query("token") String token,
                                @Query("request") String request,
                                @Query("houseID") String houseID);

    @GET("pi4Server")
    Call <limitSettingsData> getLimitSettingsData(@Query("token") String token,
                                                  @Query("request") String request,
                                                  @Query("houseID") String houseID);
    @POST("pi4Server")
    Call <resWriteDigitalPost> postWriteDigital(@Body writeDigitalPost writeDigitalPost);

    @POST("pi4Server")
    Call <resAuthorityPost> postAuth(@Body authenPost authenPost);

    @POST("pi4Server")
    Call <resLimitSettingsPost> postLimitSettings(@Body limitSettingsPost limitSettingsPost);








}
