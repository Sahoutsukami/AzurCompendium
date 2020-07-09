package com.sakyo.azurcompendium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import java.util.ArrayList;

public class ShipSelection extends AppCompatActivity {

    public RequestQueue mQueue;
    private TextView txt;
    String name;
    ArrayList<CardsShip> shipArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_selection);
        mQueue = Volley.newRequestQueue(this);

        txt = findViewById(R.id.textView);


        jsonParseNames();
    }
    private void jsonParseNames(){

        Toast.makeText(this, "Ejecuto", Toast.LENGTH_LONG).show();
        String url = getIntent().getStringExtra("link");
        txt.setText(url);



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Ships");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject ship = jsonArray.getJSONObject(i);

                                name = ship.getString("Nombre");

                                shipArrayList.add(new CardsShip(R.drawable.ic_terrain, name));


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
    } //Obtain only one time names of Ships
}
