package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class WeatherAPI extends AppCompatActivity {

    // we"ll make HTTP request to this URL to retrieve weather conditions
    String weatherWebserviceURL = "http://api.openweathermap.org/data/2.5/weather?q=ariana,tn&appid=XXXX&units=metric";
    ImageView img1;
    // Textview to show temperature and description
    TextView temperature, description, humidity, feelsLike;
    EditText editCity;
    String city = "berlin";


    // JSON object that contains weather information
    JSONObject jsonObj;

    String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_api);


        //link graphical items to variables
        temperature = (TextView) findViewById(R.id.temperature);
        description = (TextView) findViewById(R.id.description);
        humidity = (TextView) findViewById(R.id.humidity);
        editCity = (EditText) findViewById(R.id.editCity);
        img1 = (ImageView) findViewById(R.id.img1);

        Button bttnWeather = (Button) findViewById(R.id.bttnWeather);
        Button bttnToMain = (Button) findViewById(R.id.bttnToMain);


        url = "https://api.openweathermap.org/data/2.5/weather?q="+ city +"&appid=a6db16468be1247fe3c436fe88426379&units=metric";
        weather(url);

        bttnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = editCity.getText().toString();

                if (city.isEmpty())
                {
                    Toasty.info(getBaseContext(), "Please write a city", Toasty.LENGTH_LONG,true).show();
                    city = "berlin";

                }
                    city.toLowerCase();
                    url = "https://api.openweathermap.org/data/2.5/weather?q="+ city +"&appid=a6db16468be1247fe3c436fe88426379&units=metric";
                    weather(url);

            }
        });

        bttnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeatherAPI.this, MainActivity.class));
            }
        });

    }

    public void weather(String url)
    {
        JsonObjectRequest jsonObj =
                new JsonObjectRequest(Request.Method.GET,
                        url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Meteb", "Response received");
                        Log.d("Meteb",  response.toString());
                        try {
                            JSONObject jsonMain = response.getJSONObject("main");
                            JSONObject jsonSystem = response.getJSONObject("sys");

                            double temp = jsonMain.getDouble("temp");
                            Log.d("Meteb-temp", String.valueOf(temp));
                            temperature.setText("Temperature: " + String.valueOf(temp) + " C");



                            double hum = jsonMain.getDouble("humidity");
                            Log.d("Meteb-hum", String.valueOf(hum));
                            humidity.setText("Humidity: " + String.valueOf(hum));

                            String town = response.getString("name");
                            Log.d("Meteb-town", town);
                            description.setText(town);

                            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(WeatherAPI.this);
                            SharedPreferences.Editor editor = sp.edit();

                            /* sub categories as JSON arrays */
                            JSONArray jsonArray = response.getJSONArray("weather");
                            for (int i=0; i<jsonArray.length();i++) {
                                Log.d("Meteb-array", jsonArray.getString(i));
                                JSONObject oneObject = jsonArray.getJSONObject(i);
                                String weather = oneObject.getString("main");
                                Log.d("Meteb-w", weather);

                                String icon = oneObject.getString("icon");
                                Log.d("Meteb-w", weather);
                                String iconLink = "https://openweathermap.org/img/w/" + icon + ".png";

                                Glide.with(WeatherAPI.this).load(iconLink).into(img1);
                                description.setText(town + "\n"+ weather);

                                editor.putString("city", city);
                                editor.putString("link", iconLink);
                                editor.putString("weather", weather);
                                editor.commit();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Receive Error", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Meteb", "Error retrieving URL");
                    }


                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);

    }
}