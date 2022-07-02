package com.quangdau.greenhouse.FragmentChild;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quangdau.greenhouse.Adapter.RecycleView.HistoryLoginAdapter;
import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.Other.NetworkConnection;
import com.quangdau.greenhouse.Other.ToastError;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.modelsAPI.get_history.ObjHistoryLoginData;
import com.quangdau.greenhouse.modelsAPI.get_history.HistoryLoginData;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_child_history_login extends Fragment {
    //Declare variables
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
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
        swipeRefreshLayout = view.findViewById(R.id.swipeLayoutHistoryLogin);
        userPreferences = new UserPreferences(getActivity());
        networkConnection = new NetworkConnection(getActivity());
        toastError = new ToastError(getActivity());
        //Get data
        getDataHistoryLogin();

        //Setting swipe layout
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue_30));
        swipeRefreshLayout.setOnRefreshListener(this::getDataHistoryLogin);

        return view;
    }


    private void getDataHistoryLogin(){
        if (networkConnection.isNetworkConnected()){
            ApiServer getData = ApiServer.retrofit.create(ApiServer.class);
            Call<HistoryLoginData> call = getData.getHistoryLogin(userPreferences.getToken(), "GetHistoryLogin");
            call.enqueue(new Callback<HistoryLoginData>() {
                @Override
                public void onResponse(Call<HistoryLoginData> call, Response<HistoryLoginData> response) {
                    if (response.body() != null && response.body().getResponse().equals("GetHistoryLogin")){
                        settingRecycleView(response.body().getData());
                    }else {
                        networkConnection.checkStatusCode(response.code());
                    }
                    turnOffRefresh();
                }

                @Override
                public void onFailure(Call<HistoryLoginData> call, Throwable t) {
                    Log.e("gh", t.toString());
                    toastError.makeText(getResources().getString(R.string.no_response_from_server));
                    turnOffRefresh();
                }
            });
        }else {
            toastError.makeText(getResources().getString(R.string.network_offline));
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    private void settingRecycleView(ArrayList<ObjHistoryLoginData> data){
        //ArrayList<ObjHistoryLoginData> data = response.body();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HistoryLoginAdapter historyLoginAdapter = new HistoryLoginAdapter(getActivity(), data);
        recyclerView.setAdapter(historyLoginAdapter);
        historyLoginAdapter.notifyDataSetChanged();
    }


    private void turnOffRefresh(){
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
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