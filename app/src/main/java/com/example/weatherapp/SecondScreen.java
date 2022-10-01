package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondScreen extends AppCompatActivity {
    EditText editText;
    Button buttonSearch;
    TextView textViewCityName;
    ImageView imageViewImage;
    TextView textViewTemp;
    TextView textViewWeather;
    TextView textViewHumidity;
    TextView textViewMaxTemp;
    TextView textViewMinTemp;
    TextView textViewPressure;
    TextView textViewWindSpeed;

    public String city;
    public String nameandcouuntry;
    public String temp;
    public String weatherDesc;
    public String humidity;
    public String maxTemp;
    public String minTemp;
    public String pressure;
    public String windSpeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        editText = findViewById(R.id.editText);
        buttonSearch = findViewById(R.id.buttonSearch);
        textViewCityName = findViewById(R.id.textViewCityName);
        imageViewImage = findViewById(R.id.imageViewImage);
        textViewTemp = findViewById(R.id.textViewTemp);
        textViewWeather = findViewById(R.id.textViewWeather);
        textViewHumidity = findViewById(R.id.textViewHumidity);
        textViewMaxTemp = findViewById(R.id.textViewMaxTemp);
        textViewMinTemp = findViewById(R.id.textViewMinTemp);
        textViewPressure = findViewById(R.id.textViewPressure);
        textViewWindSpeed = findViewById(R.id.textViewWindSpeed);

        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = editText.getText().toString();
                if (city.isEmpty()) {
                    city = "miami";
                }
                weatherMethod();

            }
        });

        city = editText.getText().toString();
        if (city.isEmpty()){
            city = "miami";
        }
        weatherMethod();
    }

    public void weatherMethod(){
        WeatherAPI weatherAPI = RetrofitWeather.getClient().create(WeatherAPI.class);
        Call<OpenWeatherMap> call = weatherAPI.getWeatherWithName(city);
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

                if (weatherDesc.equals("broken clouds") || weatherDesc.equals("overcast clouds")) {
                    imageViewImage.setImageResource(R.drawable.moreclouds);
                } else if (weatherDesc.equals("scattered clouds")) {
                    imageViewImage.setImageResource(R.drawable.onecloud);
                } else if (weatherDesc.equals("sun") || weatherDesc.equals("clear sky")) {
                    imageViewImage.setImageResource(R.drawable.sun);
                } else if (weatherDesc.equals("mist") || weatherDesc.equals("fog")) {
                    imageViewImage.setImageResource(R.drawable.fog);
                } else if (weatherDesc.equals("few clouds")) {
                    imageViewImage.setImageResource(R.drawable.suncloud);
                } else if (weatherDesc.equals("rain") || weatherDesc.equals("light rain") || weatherDesc.equals("moderate rain")) {
                    imageViewImage.setImageResource(R.drawable.rain);
                } else {
                    imageViewImage.setImageResource(R.drawable.weather);
                }


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
                startActivity(new Intent(SecondScreen.this, MainActivity.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}