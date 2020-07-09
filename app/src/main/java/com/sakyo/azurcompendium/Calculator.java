package com.sakyo.azurcompendium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Calculator extends AppCompatActivity {

    private TextView lblDebugDB;
    private RequestQueue mQueue;
    private RecyclerView ShipSelector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        lblDebugDB = findViewById(R.id.viewLblDownload);
        ShipSelector = findViewById(R.id.viewShipSelector);
        mQueue = Volley.newRequestQueue(this);

        jsonParseNames();
        String ye = getIntent().getStringExtra("Hull");
        lblDebugDB.setText(ye);
    }

    public void testButton(View view){
        jsonParseNames();
    }

    private void jsonParseNames(){                                                                        //Parse JSON

        String url = getIntent().getStringExtra("Hull");


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Ships");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject ship = jsonArray.getJSONObject(i);

                                String name = ship.getString("Nombre");
                                lblDebugDB.append(name);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
    });
        mQueue.add(request);
    }


}
