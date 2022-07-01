package com.quangdau.greenhouse.FragmentChild;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.quangdau.greenhouse.Adapter.RecycleView.HistoryLoginAdapter;
import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.Other.NetworkConnection;
import com.quangdau.greenhouse.Other.ToastError;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.modelsAPI.get_history.historyLoginData;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_child_history_login extends Fragment {
    //Declare variables
    RecyclerView recyclerView;
    UserPreferences userPreferences;
    NetworkConnection networkConnection;
    ToastError toastError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_child_history_login, container, false);
        //Assign variables
        recyclerView = view.findViewById(R.id.recycleViewHistoryLogin);
        userPreferences = new UserPreferences(getActivity());
        networkConnection = new NetworkConnection(getActivity());
        toastError = new ToastError(getActivity(), getActivity());
        getDataHistoryLogin();
        return view;
    }


    private void getDataHistoryLogin(){
        if (networkConnection.isNetworkConnected()){
            ApiServer getData = ApiServer.retrofit.create(ApiServer.class);
            Call<ArrayList<historyLoginData>> call = getData.getHistoryLogin(userPreferences.getToken(), "GetHistoryLogin");
            call.enqueue(new Callback<ArrayList<historyLoginData>>() {
                @Override
                public void onResponse(Call<ArrayList<historyLoginData>> call, Response<ArrayList<historyLoginData>> response) {
                    if (response.body() != null){
                        settingRecycleView(response);
                    }else {
                        networkConnection.checkStatusCode(response.code());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<historyLoginData>> call, Throwable t) {
                    Log.e("gh", t.toString());
                }
            });
        }else {
            toastError.makeText("No connection!");
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    private void settingRecycleView(Response<ArrayList<historyLoginData>> response){
        ArrayList<historyLoginData> data = response.body();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HistoryLoginAdapter historyLoginAdapter = new HistoryLoginAdapter(getActivity(), data);
        recyclerView.setAdapter(historyLoginAdapter);
        historyLoginAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("gh", "HistoryLogin: resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.e("gh", "HistoryLogin: paused");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("gh", "HistoryLogin: destroy");
    }
}