package com.quangdau.greenhouse.ChildFragment;

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
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.modelsAPI.get_history.historyLoginData;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_child_history_login extends Fragment {
    RecyclerView recyclerView;
    String token;
    Bundle bundle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = this.getArguments();
        parseData(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_child_history_login, container, false);

        recyclerView = view.findViewById(R.id.recycleViewHistoryLogin);

        getDataHistoryLogin();
        return view;
    }

    private void parseData(Bundle bundle) {
        if (bundle != null) {
            token = bundle.getString("token");
        } else Log.e("gh", "bundle is null!");
    }


    private void getDataHistoryLogin(){
        ApiServer getData = ApiServer.retrofit.create(ApiServer.class);
        Call<ArrayList<historyLoginData>> call = getData.getHistoryLogin(token, "GetHistoryLogin");
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
    private void settingRecycleView(Response<ArrayList<historyLoginData>> response){
        ArrayList<historyLoginData> data = response.body();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HistoryLoginAdapter historyLoginAdapter = new HistoryLoginAdapter(getActivity(), data);
        recyclerView.setAdapter(historyLoginAdapter);
        historyLoginAdapter.notifyDataSetChanged();
    }
}