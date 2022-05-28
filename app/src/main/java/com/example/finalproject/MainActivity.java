package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bttnToInsert = (Button) findViewById(R.id.bttnToInsert);
        Button bttnToInsert2 = (Button) findViewById(R.id.bttnToInsert2);
        Button bttnToWeather = (Button) findViewById(R.id.bttnToWeather);
        ImageView imgMain = (ImageView) findViewById(R.id.imgMain);
        TextView descMain = (TextView) findViewById(R.id.descMain);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        String city = sp.getString("city", String.valueOf(0));
        String link = sp.getString("link", String.valueOf(0));
        String weather = sp.getString("weather", String.valueOf(0));

        Glide.with(MainActivity.this).load(link).into(imgMain);

        descMain.setText(city + "\n" + weather);


        bttnToInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InsertDataFirebase.class));
            }
        });

        bttnToInsert2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InsertDataSQL.class));
            }
        });

        bttnToWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WeatherAPI.class));
            }
        });



    }
}