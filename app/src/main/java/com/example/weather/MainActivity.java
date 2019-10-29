package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    TextView type,temperature;
    EditText cityName;
    Button button;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        type=findViewById(R.id.type);
        cityName=findViewById(R.id.cityName);
        button=findViewById(R.id.button);
        temperature=findViewById(R.id.temp);

        requestQueue= Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String city=cityName.getText().toString();
                sendApiRequest(city);
            }
        });
    }

    public void sendApiRequest(String string){
        String key="cf12d89f5f044b0556e09cfdae7576ce";
        String url="https://api.openweathermap.org/data/2.5/weather?q="+string+"&appid="+key+"&units=metric";

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String weather=response.getString("weather");

                    JSONArray jsonArray=new JSONArray(weather);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String typeInfo=jsonObject.getString("main");
                        type.setText(typeInfo);
                    }

                    // getting temperature
                    String main=response.getString("main");
                    JSONObject jsonObject1=new JSONObject(main);

                    temperature.setText(String.valueOf(jsonObject1.getDouble("temp")));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }


}
