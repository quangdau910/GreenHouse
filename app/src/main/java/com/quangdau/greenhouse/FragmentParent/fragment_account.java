package com.quangdau.greenhouse.FragmentParent;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.Other.NetworkConnection;
import com.quangdau.greenhouse.Other.ToastError;
import com.quangdau.greenhouse.Services.ServiceRemoveToken;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.activity_pages.activity_login;
import com.quangdau.greenhouse.modelsAPI.get_userInformation.ObjUserInformation;
import com.quangdau.greenhouse.modelsAPI.get_userInformation.UserInformation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_account extends Fragment {
    //Declare variables
    TextView tvUserName, tvEmail, tvCountry, tvDateOfBirth;
    UserPreferences userPreferences;
    NetworkConnection networkConnection;
    ToastError toastError;
    final String STATE_FRAGMENT = "ACCOUNT_FRAGMENT";
    final String NULL_STATE_FRAGMENT = "NULL";
    AppCompatButton buttonLogout;
    Intent intentRemoveToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        //Assign variables
        tvUserName = view.findViewById(R.id.tvUSerName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvCountry = view.findViewById(R.id.tvCountry);
        tvDateOfBirth = view.findViewById(R.id.tvDateOfBirth);

        buttonLogout = view.findViewById(R.id.buttonLogout);
        userPreferences = new UserPreferences(getActivity());
        intentRemoveToken = new Intent(getActivity(), ServiceRemoveToken.class);
        networkConnection = new NetworkConnection(getActivity());
        toastError = new ToastError(getActivity());




        buttonLogout.setOnClickListener(v -> {
            //Remove token
            getActivity().startService(intentRemoveToken);
            //Move to activity_login
            Intent nextPage = new Intent(getActivity(), activity_login.class);
            startActivity(nextPage);
            getActivity().finish();

        });

        return view;
    }


    private void getUserInformation(String token){
        if(networkConnection.isNetworkConnected()){
            ApiServer post = ApiServer.retrofit.create(ApiServer.class);
            Call<UserInformation> call = post.getUserInformation(token, "GetUserInformation");
            call.enqueue(new Callback<UserInformation>() {
                @Override
                public void onResponse(Call<UserInformation> call, Response<UserInformation> response) {
                    if (response.body() != null && response.body().getResponse().equals("GetUserInformation")){
                        updateUITextView(response.body().getData());
                    }else networkConnection.checkStatusCode(response.code());
                }

                @Override
                public void onFailure(Call<UserInformation> call, Throwable t) {
                    Log.e("gh", "Account: "+ t);
                    toastError.makeText(getString(R.string.no_response_from_server));
                }
            });


        }else toastError.makeText(getString(R.string.network_offline));

    }

    private void updateUITextView(ObjUserInformation objUserInformation){
        try{
            tvUserName.setText(objUserInformation.getUserName());
        }catch (NullPointerException e){
            Log.e("gh", "Account UpdateUITextView: "+ e);
            tvUserName.setText("");
        }
        try{
            tvDateOfBirth.setText(objUserInformation.getDateOfBirth());
        }catch (NullPointerException e){
            Log.e("gh", "Account UpdateUITextView: "+ e);
            tvDateOfBirth.setText("");
        }
        try{
            tvEmail.setText(objUserInformation.getEmail());
        }catch (NullPointerException e){
            Log.e("gh", "Account UpdateUITextView: "+ e);
            tvEmail.setText("");
        }
        try{
            tvCountry.setText(objUserInformation.getCountry());
        }catch (NullPointerException e){
            Log.e("gh", "Account UpdateUITextView: "+ e);
            tvCountry.setText("");
        }




    }

    @Override
    public void onResume() {
        super.onResume();
        userPreferences.setStateFragment(STATE_FRAGMENT);
        getUserInformation(userPreferences.getToken());
        Log.e("gh", "Account: resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("gh", "Account: pause");
        userPreferences.setStateFragment(NULL_STATE_FRAGMENT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("gh", "Account: destroy");
    }
}