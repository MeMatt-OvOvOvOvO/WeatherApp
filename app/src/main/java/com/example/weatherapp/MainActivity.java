package com.example.weatherapp;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String APIkey = "fc7254b2e53711e5c3e16d549302857b";
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

        buttonGoToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondScreen.class));
            }
        });
    }
}