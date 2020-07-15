package com.sakyo.azurcompendium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    private RecyclerView recyclerSelector;
    private SelectorAdapter adapterSelector;
    private RecyclerView.LayoutManager layoutSelector;

    private String name;
    private int wdmg;
    private int wrld;
    private int wfp;
    private int waa;
    private int timeVolley;
    private int nVolleys;
    private int enhanceDmg;
    private int enhanceRld;
    private int coeff;
    private int range;
    private int rangeShell;
    private int spread;

    private String wName;
    private String wFaction;
    private String ammo;


    private int selection1;
    private int selection2;
    private ArrayList<CardsShip> shipArrayList = new ArrayList<>();
    private String origin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_selection);
        mQueue = Volley.newRequestQueue(this);

        recyclerSelector = findViewById(R.id.viewReciclerShips);
        recyclerSelector.setHasFixedSize(true);
        layoutSelector = new LinearLayoutManager(this);
        recyclerSelector.setLayoutManager(layoutSelector);

        origin = getIntent().getStringExtra("from");
        selection1 = getIntent().getIntExtra("CurrentIdS", 0);
        selection2 = getIntent().getIntExtra("CurrentIdW", 0);

        if (origin.equals("Ship")) {
            jsonParseNames();
        } else if (origin.equals("Weapon")){
            jsonParseWeapons();
        }
    }
    private void jsonParseNames(){

        String url = getIntent().getStringExtra("link");

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
                            adapterSelector = new SelectorAdapter(shipArrayList);
                            recyclerSelector.setAdapter(adapterSelector);

                            adapterSelector.setOnItemClickListener(
                                    new SelectorAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    selection1 = position;
                                    debug();
                                    previous();
                                }
                            });

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

    private void jsonParseWeapons(){

        String url = getIntent().getStringExtra("link");


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Weapons");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject ship = jsonArray.getJSONObject(i);

                                name = ship.getString("Nombre:");

                                shipArrayList.add(new CardsShip(R.drawable.ic_terrain, name));


                            }

                            adapterSelector = new SelectorAdapter(shipArrayList);
                            recyclerSelector.setAdapter(adapterSelector);

                            adapterSelector.setOnItemClickListener(
                                    new SelectorAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(int position) {
                                            selection2 = position;
                                            debug();
                                            previous();
                                        }
                                    });

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
    } //Obtain only one time names of Weapons


    public void debug(){
        Toast.makeText(this,Integer.toString(selection1), Toast.LENGTH_SHORT).show();
        Toast.makeText(this,Integer.toString(selection2), Toast.LENGTH_SHORT).show();

    }
    public void previous(){
        setResult(Activity.RESULT_OK,
                new Intent().putExtra("idShip", selection1).putExtra("idWeapon", selection2));
        finish();
    }
}
