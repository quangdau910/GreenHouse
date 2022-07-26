package com.quangdau.greenhouse.FragmentChild;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.ApiService.ApiWeather;
import com.quangdau.greenhouse.Other.NetworkConnection;
import com.quangdau.greenhouse.Other.ToastError;
import com.quangdau.greenhouse.SharedPreferences.UserPreferences;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.modelsAPI.post_writeDigital.WriteDigitalPost;
import com.quangdau.greenhouse.modelsAPI.get_data.Data;
import com.quangdau.greenhouse.modelsAPI.res_writeDigitalPost.resWriteDigitalPost;
import com.quangdau.greenhouse.modelsAPI.weather.WeatherDataModel;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_child_home2 extends Fragment {
    private static final int REQUEST_PERMISSION_CODE = 123;
    //Declare variables
    //Location variables
    LocationManager locationManager;
    LocationListener locationListener;
    double latitude, longitude;
    final String weatherAPPID = "b6b29c45335b731aa126df23c00085e3";
    ImageView imageViewWeatherIcon;
    CardView cardViewWeather;
    TextView textViewWeatherName, textViewWeatherTemp, textViewWeatherHumidity;
    //Sensor variables
    TextView textViewTemperatureSensor, textViewLightSensor, textViewHumiditySensor, textViewSoilMoistureSensor1, textViewSoilMoistureSensor2, textViewSoilMoistureSensor3, textViewSoilMoistureSensor4;    //Token
    //Device variables
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchLight1, switchFan1, switchValve1, switchValve2, switchValve3, switchValve4;
    ImageView imageViewLight1, imageViewFan1, imageViewValve1, imageViewValve2, imageViewValve3, imageViewValve4;
    boolean flagUIDevice;
    //Get data through fragment
    Bundle bundle;
    String houseID;
    //Handler post delay
    Handler handler;
    Runnable runnable;
    //Other
    String dataPort0;
    NetworkConnection networkConnection;
    ToastError toastError;
    UserPreferences userPreferences;
    final String STATE_FRAGMENT = "HOME_FRAGMENT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("gh", "home2 create");
        runnable = new Runnable() {
            @Override
            public void run() {
                if (userPreferences.getStateFragment().equals(STATE_FRAGMENT) && networkConnection.isNetworkConnected()){
                    //Update data from server
                    getData(userPreferences.getToken());
                    //Log.e("gh", "Home2 runnable: ");
                }
                handler.postDelayed(this, 500);
            }
        };
        handler = new Handler(Looper.getMainLooper());
        bundle = this.getArguments();
        parseData(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_home2, container, false);
        //Assign variables
        userPreferences = new UserPreferences(getActivity());
        networkConnection = new NetworkConnection(getActivity());
        //Location
        imageViewWeatherIcon = view.findViewById(R.id.imageViewWeatherIcon);
        textViewWeatherName = view.findViewById(R.id.textViewWeatherName);
        textViewWeatherTemp = view.findViewById(R.id.textViewWeatherTemp);
        textViewWeatherHumidity = view.findViewById(R.id.textViewWeatherHumidity);
        cardViewWeather = view.findViewById(R.id.cardViewWeather);
        //Sensor
        textViewTemperatureSensor = view.findViewById(R.id.textViewTemperatureSensor);
        textViewLightSensor = view.findViewById(R.id.textViewLightSensor);
        textViewHumiditySensor = view.findViewById(R.id.textViewHumiditySensor);
        textViewSoilMoistureSensor1 = view.findViewById(R.id.textViewSoilMoistureSensor1);
        textViewSoilMoistureSensor2 = view.findViewById(R.id.textViewSoilMoistureSensor2);
        textViewSoilMoistureSensor3 = view.findViewById(R.id.textViewSoilMoistureSensor3);
        textViewSoilMoistureSensor4 = view.findViewById(R.id.textViewSoilMoistureSensor4);
        //Device
        switchLight1 = view.findViewById(R.id.switchLight1);
        switchFan1 = view.findViewById(R.id.switchFan1);
        switchValve1 = view.findViewById(R.id.switchValve1);
        switchValve2 = view.findViewById(R.id.switchValve2);
        switchValve3 = view.findViewById(R.id.switchValve3);
        switchValve4 = view.findViewById(R.id.switchValve4);
        imageViewLight1 = view.findViewById(R.id.imageViewDeviceLight1);
        imageViewFan1 = view.findViewById(R.id.imageViewDeviceFan1);
        imageViewValve1 = view.findViewById(R.id.imageViewDeviceValve1);
        imageViewValve2 = view.findViewById(R.id.imageViewDeviceValve2);
        imageViewValve3 = view.findViewById(R.id.imageViewDeviceValve3);
        imageViewValve4 = view.findViewById(R.id.imageViewDeviceValve4);
        //Other
        toastError = new ToastError(getActivity());
        //DefaultUI
        cardViewWeather.setVisibility(View.GONE);
        //Get data
        getData(userPreferences.getToken());

        switchLight1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchLight1.isPressed()){
                if (switchLight1.isChecked()){
                    writeDigital(houseID, "0", 0, '0');
                }else {
                    writeDigital(houseID, "0", 0, '1');
                }
            }
        });
        switchFan1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchFan1.isPressed()){
                if (switchFan1.isChecked()){
                    writeDigital(houseID, "0", 1, '0');
                }else {
                    writeDigital(houseID, "0", 1, '1');
                }
            }
        });
        switchValve1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchValve1.isPressed()){
                if (switchValve1.isChecked()){
                    writeDigital(houseID, "0", 2, '0');
                }else{
                    writeDigital(houseID, "0", 2, '1');
                }
            }
        });
        switchValve2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchValve2.isPressed()){
                if (switchValve2.isChecked()){
                    writeDigital(houseID, "0", 3, '0');
                }else{
                    writeDigital(houseID, "0", 3, '1');
                }
            }
        });
        switchValve3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchValve3.isPressed()){
                if (switchValve3.isChecked()){
                    writeDigital(houseID, "0", 4, '0');
                }else{
                    writeDigital(houseID, "0", 4, '1');
                }
            }
        });
        switchValve4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchValve4.isPressed()){
                if (switchValve4.isChecked()){
                    writeDigital(houseID, "0", 5, '0');
                }else{
                    writeDigital(houseID, "0", 5, '1');
                }
            }
        });

        return view;
    }

    //Get data
    private void getData(String token) {
        if (networkConnection.isNetworkConnected()){
            Switch[] port0 = {switchLight1, switchFan1, switchValve1, switchValve2, switchValve3, switchValve4};
            ApiServer get = ApiServer.retrofit.create(ApiServer.class);
            Call<Data> call = get.getData(token, "GetData", houseID);
            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {
                    if (response.body() != null && response.body().getResponse().equals("GetData")){
                        //Assign dataPort
                        dataPort0 = response.body().getDigitalData().getPort0();
                        //UpdateUI
                        updateUISensor(response);
                        updateUIDevice(dataPort0, port0);
                    }else {
                        networkConnection.checkStatusCode(response.code());
                    }
                }

                @Override
                public void onFailure(Call<Data> call, Throwable t) {
                    Log.e("gh", "Home2 GetData: " + t);
                }
            });
        }
    }

    private void writeDigital(String houseID, String port, Integer locationBit, Character value){
        //Unable switch
        setSwitchMode(false);
        flagUIDevice = true;
        if (networkConnection.isNetworkConnected()) {
            Switch[] port0 = {switchLight1, switchFan1, switchValve1, switchValve2, switchValve3, switchValve4};
            StringBuilder tempData = new StringBuilder(dataPort0);
            tempData.setCharAt(7 - locationBit, value);
            //Check pump
            String dataSend = tempData.toString();
            dataSend = checkPump(dataSend, 2, 5, 6);
            Log.e("gh", "dataSend: " + dataSend);
            //Call API
            ApiServer post = ApiServer.retrofit.create(ApiServer.class);
            Call<resWriteDigitalPost> call = post.postWriteDigital(new WriteDigitalPost(userPreferences.getToken(), "WriteDigital", houseID, port, dataSend));
            call.enqueue(new Callback<resWriteDigitalPost>() {
                @Override
                public void onResponse(Call<resWriteDigitalPost> call, Response<resWriteDigitalPost> response) {
                    if (response.body() != null && response.body().getResponse().equals("WriteDigital")){
                        Log.e("gh", "response: ");
                        switch (response.body().getPort()) {
                            case "0":
                                Log.e("gh", "data port0: "+ response.body().getPort());
                                Log.e("gh", "value: "+ response.body().getValue());
                                dataPort0 = response.body().getValue();
                                break;
                            case "1":
                                //Do something
                                break;
                        }
                    }else networkConnection.checkStatusCode(response.code());
                    //Update UI Devices
                    flagUIDevice = false;
                    updateUIDevice(dataPort0, port0);
                    setSwitchMode(true);
                }

                @Override
                public void onFailure(Call<resWriteDigitalPost> call, Throwable t) {
                    Log.e("gh", "Home2 WriteDigital: " + t);
                    if (t.getMessage().equals("timeout")){
                        toastError.makeText(getResources().getString(R.string.no_response_from_server));
                    }
                    //Update UI Devices
                    setSwitchMode(true);
                    flagUIDevice = false;
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateUISensor(Response<Data> response) {
        //Log.e("gh", "update ui sensor");
        textViewTemperatureSensor.setText(response.body().getTemperature() + "");
        textViewLightSensor.setText(response.body().getLight() + "");
        textViewHumiditySensor.setText(response.body().getHumidity() + "");
        textViewSoilMoistureSensor1.setText(response.body().getSoil_moisture1() + "");
        textViewSoilMoistureSensor2.setText(response.body().getSoil_moisture2() + "");
        textViewSoilMoistureSensor3.setText(response.body().getSoil_moisture3() + "");
        textViewSoilMoistureSensor4.setText(response.body().getSoil_moisture4() + "");
    }

    private void updateUIDevice(String value, Switch[] portDevice){
        if (!flagUIDevice){
            //Log.e("gh", "update ui device");
            for (int i = 0; i < portDevice.length; i++){
                if (value.charAt(7 - i) == '0') portDevice[i].setChecked(true); else portDevice[i].setChecked(false);
            }
            if (switchLight1.isChecked())imageViewLight1.setImageResource(R.drawable.ic_device_light_on); else imageViewLight1.setImageResource(R.drawable.ic_device_light_off);
            if (switchFan1.isChecked()) imageViewFan1.setImageResource(R.drawable.ic_device_fan_on); else imageViewFan1.setImageResource(R.drawable.ic_device_fan_off);
            if (switchValve1.isChecked()) imageViewValve1.setImageResource(R.drawable.ic_device_valve_on); else imageViewValve1.setImageResource(R.drawable.ic_device_valve_off);
            if (switchValve2.isChecked()) imageViewValve2.setImageResource(R.drawable.ic_device_valve_on); else imageViewValve2.setImageResource(R.drawable.ic_device_valve_off);
            if (switchValve3.isChecked()) imageViewValve3.setImageResource(R.drawable.ic_device_valve_on); else imageViewValve3.setImageResource(R.drawable.ic_device_valve_off);
            if (switchValve4.isChecked()) imageViewValve4.setImageResource(R.drawable.ic_device_valve_on); else imageViewValve4.setImageResource(R.drawable.ic_device_valve_off);
        }
    }
    private String checkPump(String dataPort, int valveStart, int valveEnd, int locationPump){
        StringBuilder newDataPort = new StringBuilder(dataPort);
        for (int i = valveStart; i <= valveEnd; i++){
            if (dataPort.charAt(7 - i) == '0'){
                newDataPort.setCharAt(7- locationPump, '0');
            }
        }
        if (dataPort.substring(7 - valveEnd, 7 - valveStart + 1).equals("1111")) newDataPort.setCharAt(7 - locationPump, '1');
        //Bit 7 always 1
        newDataPort.setCharAt(0, '1');
        return  newDataPort.toString();
    }

    private void parseData(Bundle bundle) {
        if (bundle != null){
            houseID = bundle.getString("houseID");
        }
    }

    //Weather
    private void getCurrentLocation() {
        assert getActivity().getSystemService(Context.LOCATION_SERVICE) != null;
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                getWeatherApi();
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                getCurrentLocation();
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
            }
        };
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_CODE);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5000, locationListener);
        getWeatherApi();
    }

    private void getWeatherApi() {
        if (networkConnection.isNetworkConnected()){
            ApiWeather getData = ApiWeather.retrofit.create(ApiWeather.class);
            Call<WeatherDataModel> call = getData.getWeather(latitude, longitude, weatherAPPID);
            call.enqueue(new Callback<WeatherDataModel>() {
                @Override
                public void onResponse(Call<WeatherDataModel> call, Response<WeatherDataModel> response) {
                    if (response.body() != null){
                        updateUIWeather(response);
                    }
                }
                @Override
                public void onFailure(Call<WeatherDataModel> call, Throwable t) {
                    Log.e("gh", "Home2 Weather: " + t);
                    //toastError.makeText(getResources().getString(R.string.no_response_from_server));
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateUIWeather(Response<WeatherDataModel> response){
        assert response.body() != null;
        //Check Location
        if (response.body().getCoord().getLat() == 0 && response.body().getCoord().getLon() == 0){
            cardViewWeather.setVisibility(View.GONE);
        }else{
            cardViewWeather.setVisibility(View.VISIBLE);
        }
        //Weather Icon
        String weatherIconId = getWeatherIcon(response.body().getWeather()[0].getId());
        int resourceId = getActivity().getResources().getIdentifier(weatherIconId, "drawable", getActivity().getPackageName());
        imageViewWeatherIcon.setImageResource(resourceId);
        //Weather Name
        textViewWeatherName.setText(response.body().getName());
        //Weather Temp
        int tempWeather = (int) Math.rint(response.body().getMain().getTemp() - 273.15);
        textViewWeatherTemp.setText(getResources().getString(R.string.temperature)+": " + tempWeather + "\u2103");
        //Humidity
        int humidityWeather = response.body().getMain().getHumidity();
        textViewWeatherHumidity.setText(getResources().getString(R.string.humidity)+": " + humidityWeather + "%");
    }

    private void setSwitchMode(boolean mode){
        switchLight1.setEnabled(mode);
        switchFan1.setEnabled(mode);
        switchValve1.setEnabled(mode);
        switchValve2.setEnabled(mode);
        switchValve3.setEnabled(mode);
        switchValve4.setEnabled(mode);
    }


    private static String getWeatherIcon(int id){
        if (id >= 0 && id < 300) {
            return "weather_ic_tstorm1";
        } else if (id >= 300 && id < 500) {
            return "weather_ic_light_rain";
        } else if (id >= 500 && id < 600) {
            return "weather_ic_shower3";
        } else if (id >= 600 && id <= 700) {
            return "snow4";
        } else if (id >= 701 && id <= 771) {
            return "weather_ic_fog";
        } else if (id >= 772 && id < 800) {
            return "weather_ic_tstorm3";
        } else if (id == 800) {
            return "weather_ic_sunny";
        } else if (id >= 801 && id <= 804) {
            return "weather_ic_cloudy2";
        } else if (id >= 900 && id <= 902) {
            return "weather_ic_tstorm3";
        } else if (id == 903) {
            return "weather_ic_snow5";
        } else if (id == 904) {
            return "weather_ic_sunny";
        } else if (id >= 905 && id <= 1000) {
            return "weather_ic_tstorm3";
        }
        return "weather_ic_dunno";
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("gh", "home2 resume");
        getCurrentLocation();
        handler.post(runnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.e("gh", "home2 pause");
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("gh", "home2 destroy");
    }
}