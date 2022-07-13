package com.quangdau.greenhouse.ApiService;

import com.quangdau.greenhouse.modelsAPI.get_graph.DataGraph;
import com.quangdau.greenhouse.modelsAPI.get_history.HistoryLoginData;
import com.quangdau.greenhouse.modelsAPI.get_limitSettings.LimitSettingsData;
import com.quangdau.greenhouse.modelsAPI.get_userInformation.UserInformation;
import com.quangdau.greenhouse.modelsAPI.post_limitSettings.LimitSettingsPost;
import com.quangdau.greenhouse.modelsAPI.post_removeToken.RemoveTokenPost;
import com.quangdau.greenhouse.modelsAPI.post_renewToken.RenewTokenPost;
import com.quangdau.greenhouse.modelsAPI.post_writeDigital.WriteDigitalPost;
import com.quangdau.greenhouse.modelsAPI.get_RSSI.RSSIData;
import com.quangdau.greenhouse.modelsAPI.post_authen.AuthenPost;
import com.quangdau.greenhouse.modelsAPI.res_authenPost.resAuthorityPost;
import com.quangdau.greenhouse.modelsAPI.get_data.Data;
import com.quangdau.greenhouse.modelsAPI.res_limitSettingsPost.resLimitSettingsPost;
import com.quangdau.greenhouse.modelsAPI.res_removeTokenPost.resRemoveTokenPost;
import com.quangdau.greenhouse.modelsAPI.res_renewTokenPost.resRenewTokenPost;
import com.quangdau.greenhouse.modelsAPI.res_writeDigitalPost.resWriteDigitalPost;

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
    Call <Data> getData(@Query("token") String token,
                        @Query("request") String request,
                        @Query("houseID") String houseID);

    @GET("pi4Server")
    Call <DataGraph> getGraphData(@Query("token") String token,
                                  @Query("request") String request,
                                  @Query("houseID") String houseID,
                                  @Query("sensor") String sensor,
                                  @Query("time") String time);
    @GET("pi4Server")
    Call <HistoryLoginData> getHistoryLogin(@Query("token") String token,
                                            @Query("request") String request);

    @GET("pi4Server")
    Call <RSSIData> getRSSIData(@Query("token") String token,
                                @Query("request") String request,
                                @Query("houseID") String houseID);

    @GET("pi4Server")
    Call <LimitSettingsData> getLimitSettingsData(@Query("token") String token,
                                                  @Query("request") String request,
                                                  @Query("houseID") String houseID);

    @GET("pi4Server")
    Call <UserInformation> getUserInformation(@Query("token") String token,
                                              @Query("request") String request);


    @POST("pi4Server")
    Call <resWriteDigitalPost> postWriteDigital(@Body WriteDigitalPost writeDigitalPost);

    @POST("pi4Server")
    Call <resAuthorityPost> postAuth(@Body AuthenPost authenPost);

    @POST("pi4Server")
    Call <resLimitSettingsPost> postLimitSettings(@Body LimitSettingsPost limitSettingsPost);

    @POST("pi4Server")
    Call  <resRenewTokenPost> postRenewToken(@Body RenewTokenPost renewTokenPost);

    @POST("pi4Server")
    Call <resRemoveTokenPost> postLogout(@Body RemoveTokenPost removeTokenPost);

}
