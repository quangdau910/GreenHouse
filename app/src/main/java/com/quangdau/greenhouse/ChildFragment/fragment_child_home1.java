package com.quangdau.greenhouse.ChildFragment;

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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


import com.quangdau.greenhouse.ApiService.ApiServer;
import com.quangdau.greenhouse.ApiService.ApiWeather;
import com.quangdau.greenhouse.R;
import com.quangdau.greenhouse.digitalPost;
import com.quangdau.greenhouse.modelsAPI.get_data.data;
import com.quangdau.greenhouse.modelsAPI.res_write.resWriteDigital;
import com.quangdau.greenhouse.modelsAPI.weather.weatherDataModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_child_home1 extends Fragment {
    private static final int REQUEST_PERMISSION_CODE = 123;
    //Declare variables

    //Location variables
    LocationManager locationManager;
    LocationListener locationListener;
    double latitude, longitude;
    ImageView imageViewWeatherIcon;
    CardView cardViewWeather;
    TextView textViewWeatherName, textViewWeatherTemp, textViewWeatherHumidity;
    TextView textViewTemperatureSensor, textViewLightSensor, textViewHumiditySensor, textViewSoilMoistureSensor1, textViewSoilMoistureSensor2, textViewSoilMoistureSensor3, textViewSoilMoistureSensor4;    //Token
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchLight1, switchFan1, switchValve1, switchValve2, switchValve3, switchValve4;
    Bundle bundle;
    String token, houseID;
    final Handler handler = new Handler(Looper.getMainLooper());
    String data = "11110000";


    final String APPID = "b6b29c45335b731aa126df23c00085e3";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = this.getArguments();
        parseData(bundle);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_home1, container, false);
        //link
        imageViewWeatherIcon = view.findViewById(R.id.imageViewWeatherIcon);
        textViewWeatherName = view.findViewById(R.id.textViewWeatherName);
        textViewWeatherTemp = view.findViewById(R.id.textViewWeatherTemp);
        textViewWeatherHumidity = view.findViewById(R.id.textViewWeatherHumidity);
        cardViewWeather = view.findViewById(R.id.cardViewWeather);
        textViewTemperatureSensor = view.findViewById(R.id.textViewTemperatureSensor);
        textViewLightSensor = view.findViewById(R.id.textViewLightSensor);
        textViewHumiditySensor = view.findViewById(R.id.textViewHumiditySensor);
        textViewSoilMoistureSensor1 = view.findViewById(R.id.textViewSoilMoistureSensor1);
        textViewSoilMoistureSensor2 = view.findViewById(R.id.textViewSoilMoistureSensor2);
        textViewSoilMoistureSensor3 = view.findViewById(R.id.textViewSoilMoistureSensor3);
        textViewSoilMoistureSensor4 = view.findViewById(R.id.textViewSoilMoistureSensor4);

        switchLight1 = view.findViewById(R.id.switchLight1);
        //Main
        cardViewWeather.setVisibility(View.GONE);
        getDataApi(token);
        //Update data from server
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 15000);
                getDataApi(token);

            }
        }, 15000);

        switchLight1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchLight1.isChecked()){
                    writeDigital(houseID, "port1", 0, '1');
                }else writeDigital(houseID, "port1", 0, '0');
            }
        });


        Log.e("gh", "houseID: " + houseID);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        getCurrentLocation();
    }

    //Get data
    private void getDataApi(String token) {
        ApiServer get = ApiServer.retrofit.create(ApiServer.class);
        Call<data> call = get.getData(token, "GetData", houseID);
        call.enqueue(new Callback<data>() {
            @Override
            public void onResponse(Call<data> call, Response<data> response) {
                updateUISensor(response);
            }

            @Override
            public void onFailure(Call<data> call, Throwable t) {
                Log.e("gh", "Error: " + t);
            }
        });
    }

    private void updateUISensor(Response<data> response) {
        //Temperature
        textViewTemperatureSensor.setText(response.body().getTemperature() + "");
        textViewLightSensor.setText(response.body().getLight() + "");
        textViewHumiditySensor.setText(response.body().getHumidity() + "");
        textViewSoilMoistureSensor1.setText(response.body().getSoil_moisture1() + "");
        textViewSoilMoistureSensor2.setText(response.body().getSoil_moisture2() + "");
        textViewSoilMoistureSensor3.setText(response.body().getSoil_moisture3() + "");
        textViewSoilMoistureSensor4.setText(response.body().getSoil_moisture4() + "");
    }


    private void parseData(Bundle bundle) {
        if (bundle != null) {
            token = bundle.getString("token");
            houseID = bundle.getString("houseID");
        } else Log.e("gh", "bundle is null!");
    }



    private void writeDigital(String houseID, String port, Integer locationBit, Character value){
        StringBuilder tempValue = new StringBuilder(data);
        tempValue.setCharAt(7 - locationBit, value);

        ApiServer post = ApiServer.retrofit.create(ApiServer.class);
        Call<resWriteDigital> call = post.postDigital(new digitalPost(token, "WriteDigital", houseID, port, value));
        call.enqueue(new Callback<resWriteDigital>() {
            @Override
            public void onResponse(Call<resWriteDigital> call, Response<resWriteDigital> response) {
                if (response.body().getResponse() == "Write completed!"){

                }
            }

            @Override
            public void onFailure(Call<resWriteDigital> call, Throwable t) {

            }
        });
    }






    //Weather
    private void getCurrentLocation() {
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
        ApiWeather getData = ApiWeather.retrofit.create(ApiWeather.class);
        Call<weatherDataModel> call = getData.getWeather(latitude, longitude, APPID);
        call.enqueue(new Callback<weatherDataModel>() {
            @Override
            public void onResponse(Call<weatherDataModel> call, Response<weatherDataModel> response) {
                updateUIWeather(response);
            }
            @Override
            public void onFailure(Call<weatherDataModel> call, Throwable t) {
                Log.e("gh", "Error get api: " + t);
            }
        });
    }

    private void updateUIWeather(Response<weatherDataModel> response){
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
        textViewWeatherTemp.setText("Temperature: " + tempWeather + " \u2103");
        //Humidity
        int humidityWeather = response.body().getMain().getHumidity();
        textViewWeatherHumidity.setText("Humidity: " + humidityWeather + "%");
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
}