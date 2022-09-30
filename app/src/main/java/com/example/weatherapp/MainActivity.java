package com.example.weatherapp;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView textViewCityName;
    ImageView imageViewImage;
    TextView textViewTemp;
    TextView textViewWeather;
    TextView textViewHumidity;
    TextView textViewMaxTemp;
    TextView textViewMinTemp;
    TextView textViewPressure;
    TextView textViewWindSpeed;
    Button buttonGoToSearch;

    public String nameandcouuntry;
    public String temp;
    public String weatherDesc;
    public String humidity;
    public String maxTemp;
    public String minTemp;
    public String pressure;
    public String windSpeed;

    protected LocationManager locationManager;
    protected LocationListener locationListener;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCityName = findViewById(R.id.textViewCityName);
        imageViewImage = findViewById(R.id.imageViewImage);
        textViewTemp = findViewById(R.id.textViewTemp);
        textViewWeather = findViewById(R.id.textViewWeather);
        textViewHumidity = findViewById(R.id.textViewHumidity);
        textViewMaxTemp = findViewById(R.id.textViewMaxTemp);
        textViewMinTemp = findViewById(R.id.textViewMinTemp);
        textViewPressure = findViewById(R.id.textViewPressure);
        textViewWindSpeed = findViewById(R.id.textViewWindSpeed);
        buttonGoToSearch = findViewById(R.id.buttonSearch);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

            }
        };
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,50,locationListener);
        }


        WeatherAPI weatherAPI = RetrofitWeather.getClient().create(WeatherAPI.class);
        Call<OpenWeatherMap> call = weatherAPI.getWeatherWithName("paris");
        call.enqueue(new Callback<OpenWeatherMap>() {
            @Override
            public void onResponse(Call<OpenWeatherMap> call, Response<OpenWeatherMap> response) {
                OpenWeatherMap res = response.body();

                nameandcouuntry = res.getName() + ", " + res.getSys().getCountry();
                textViewCityName.setText(nameandcouuntry);

                temp = res.getMain().getTemp() + " C";
                textViewTemp.setText(temp);

                weatherDesc = res.getWeather().get(0).getDescription();
                textViewWeather.setText(weatherDesc);

                humidity = ": " + res.getMain().getHumidity() + " %";
                textViewHumidity.setText(humidity);

                maxTemp = ": " + res.getMain().getTempMax() + " C";
                textViewMaxTemp.setText(maxTemp);

                minTemp = ": " + res.getMain().getTempMin() + " C";
                textViewMinTemp.setText(minTemp);

                pressure = ": " + res.getMain().getPressure().toString();
                textViewPressure.setText(pressure);

                windSpeed = ": " + res.getWind().getSpeed();
                textViewWindSpeed.setText(windSpeed);
            }

            @Override
            public void onFailure(Call<OpenWeatherMap> call, Throwable t) {

            }
        });

        buttonGoToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondScreen.class));
            }
        });
    }
}