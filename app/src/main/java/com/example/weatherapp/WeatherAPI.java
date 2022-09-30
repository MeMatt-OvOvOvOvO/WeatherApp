package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {
    @GET("weather?appid=fc7254b2e53711e5c3e16d549302857b&units=metric")
    Call<OpenWeatherMap> getWeatherWithLocation(
            @Query("lat")double lat,
            @Query("lon")double lon
    );

    @GET("weather?appid=fc7254b2e53711e5c3e16d549302857b&units=metric")
    Call<OpenWeatherMap> getWeatherWithName(@Query("q")String name);
}
