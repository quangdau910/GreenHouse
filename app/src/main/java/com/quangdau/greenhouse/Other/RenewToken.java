package com.quangdau.greenhouse.Other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.Services.ServiceRemoveToken;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.activity_pages.activity_login;
import com.quangdau.greenhouse.modelsAPI.post_renewToken.RenewTokenPost;
import com.quangdau.greenhouse.modelsAPI.res_renewTokenPost.resRenewTokenPost;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RenewToken{
    private final Context context;
    private NetworkConnection networkConnection;
    private UserPreferences userPreferences;


    public RenewToken(Context context) {
        this.context = context;
    }

    public void renewToken(){
        networkConnection = new NetworkConnection(context);
        userPreferences = new UserPreferences(context);
        if (networkConnection.isNetworkConnected()){
            ApiServer post = ApiServer.retrofit.create(ApiServer.class);
            RenewTokenPost renewTokenPost = new RenewTokenPost(userPreferences.getToken(), "RenewToken");
            Call<resRenewTokenPost> postRenew = post.postRenewToken(renewTokenPost);
            postRenew.enqueue(new Callback<resRenewTokenPost>() {
                @Override
                public void onResponse(Call<resRenewTokenPost> call, Response<resRenewTokenPost> response) {
                    if (response.body() != null && response.body().getResponse().equals("ResponseNewToken")) {
                        Log.e("gh", "Renew Token");
                        userPreferences.setToken(response.body().getToken());
                    }else {
                        networkConnection.checkStatusCode(response.code());
                    }
                }

                @Override
                public void onFailure(Call<resRenewTokenPost> call, Throwable t) {
                    Log.e("gh", "Renew Token: "+ t);
                    //Remove token
                    Intent intentRemoveToken = new Intent(context, ServiceRemoveToken.class);
                    context.startService(intentRemoveToken);
                    //Move to activity_login
                    Intent nextPage = new Intent(context, activity_login.class);
                    context.startActivity(nextPage);
                    Activity activity = (Activity) context;
                    activity.finish();
                }
            });
        }
    }

}
