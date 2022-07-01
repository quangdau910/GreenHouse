package com.quangdau.greenhouse.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.Other.NetworkConnection;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.modelsAPI.post_removeToken.RemoveTokenPost;
import com.quangdau.greenhouse.modelsAPI.res_removeTokenPost.resRemoveTokenPost;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceRemoveToken extends Service {
    UserPreferences userPreferences;
    NetworkConnection networkConnection;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        userPreferences = new UserPreferences(this);
        networkConnection = new NetworkConnection(this);
        removeToken(userPreferences.getToken());
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("gh", "Service destroy");
    }

    private void removeToken(String token) {
        if (networkConnection.isNetworkConnected()){
            userPreferences.setToken(null);
            ApiServer post = ApiServer.retrofit.create(ApiServer.class);
            RemoveTokenPost removeTokenPost = new RemoveTokenPost(token);
            Call<resRemoveTokenPost> postLogout = post.postLogout(removeTokenPost);
            postLogout.enqueue(new Callback<resRemoveTokenPost>() {
                @Override
                public void onResponse(Call<resRemoveTokenPost> call, Response<resRemoveTokenPost> response) {
                    if (response.body() != null && response.body().getResponse().equals("RemoveToken")){
                        Log.e("gh", "Account: response code " + response.code());
                    }
                    stopSelf();
                }

                @Override
                public void onFailure(Call<resRemoveTokenPost> call, Throwable t) {
                    Log.e("gh", "Account: " + t);
                    stopSelf();
                }
            });
        }else{
            stopSelf();
        }
    }
}
