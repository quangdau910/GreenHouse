package com.quangdau.greenhouse.ChildFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quangdau.greenhouse.Adapter.RecycleView.HistoryLoginAdapter;
import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.Preferences.UserPreferences;
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
        getDataHistoryLogin();
        return view;
    }


    private void getDataHistoryLogin(){
        ApiServer getData = ApiServer.retrofit.create(ApiServer.class);
        Call<ArrayList<historyLoginData>> call = getData.getHistoryLogin(userPreferences.getToken(), "GetHistoryLogin");
        call.enqueue(new Callback<ArrayList<historyLoginData>>() {
            @Override
            public void onResponse(Call<ArrayList<historyLoginData>> call, Response<ArrayList<historyLoginData>> response) {
                settingRecycleView(response);
            }

            @Override
            public void onFailure(Call<ArrayList<historyLoginData>> call, Throwable t) {
                Log.e("gh", t.toString());
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    private void settingRecycleView(Response<ArrayList<historyLoginData>> response){
        ArrayList<historyLoginData> data = response.body();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HistoryLoginAdapter historyLoginAdapter = new HistoryLoginAdapter(getActivity(), data);
        recyclerView.setAdapter(historyLoginAdapter);
        historyLoginAdapter.notifyDataSetChanged();
    }
}