package com.sakyo.azurcompendium;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.util.Objects;

public class Calculator extends AppCompatActivity {

    private RequestQueue mQueue;
    private static final int REQUEST_GET_SHIP_LOCATION = 0;
    private static final int REQUEST_GET_WEAPON_LOCATION = 0;
    private int shipId;
    private int weaponId;
    private TextView u;

    // region Ship Stats
    private int hp;
    private int eva;
    private int fp;
    private int trp;
    private int rld;
    private int avi;
    private int aa;
    private int lck;
    private int acc;
    private int asw;
    private int speed;
    private int oil;
    private int eff1;
    private int eff2;
    private int eff3;
    private int mgm;
    private String name;
    private String armor;
    private String faction;
    private String slot1;
    private String slot2;
    // endregion Sip Stats

    //region Weapon Stats
    private int wdmg;
    private int wrld;
    private int timeVolley;
    private int nVolleys;
    private int enhanceDmg;
    private int enhanceRld;
    private int coeff;
    private int wfp;
    private int waa;
    private int range;
    private int rangeShell;
    private int spread;
    private String wName;
    private String ammo;
    private String wFaction;
    //endregion Weapon Stats

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        mQueue = Volley.newRequestQueue(this);
        u = findViewById(R.id.textView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //determineMainGun("DD");

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void selectButton(View view){

        String link = getIntent().getStringExtra("Ship");
        Intent i = new Intent(this, ShipSelection.class);
        i.putExtra("link", link).putExtra("from", "Ship");
        startActivityForResult(i, REQUEST_GET_SHIP_LOCATION);
    }

    public void selectWeapon(View view){
        String link = getIntent().getStringExtra("MainGun");
        Intent i = new Intent(this, ShipSelection.class);
        i.putExtra("link", link).putExtra("from", "Weapon");
        startActivityForResult(i, REQUEST_GET_SHIP_LOCATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GET_SHIP_LOCATION && resultCode == Activity.RESULT_OK){
            shipId = data.getIntExtra("idShip",0);
            weaponId = data.getIntExtra("idWeapon",0);
            jsonParseStats();
            jsonParseMainGuns();
            //setVariables();
        }
    }



    private void jsonParseStats(){

        String url = getIntent().getStringExtra("Ship");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Ships");

                            JSONObject ship = jsonArray.getJSONObject(shipId);

                            hp = ship.getInt("HP");
                            eva = ship.getInt("EVA");
                            fp = ship.getInt("FP");
                            trp = ship.getInt("TRP");
                            rld = ship.getInt("RLD");
                            avi = ship.getInt("AVI");
                            aa = ship.getInt("AA");
                            lck = ship.getInt("LUCK");
                            acc = ship.getInt("ACC");
                            asw = ship.getInt("ASW");
                            speed = ship.getInt("Speed");
                            oil = ship.getInt("Oil");
                            eff1 = ship.getInt("Eff1");
                            eff2 = ship.getInt("Eff2");
                            eff3 = ship.getInt("Eff3");
                            mgm = ship.getInt("#MainGun");
                            name = ship.getString("Nombre");
                            armor = ship.getString("Armor");
                            faction = ship.getString("Faction");
                            slot1 = ship.getString("Slot 1");
                            slot2 = ship.getString("Slot 2");

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
    } //Parse when Ship change only

    private void jsonParseMainGuns(){

       // if (){}
        String url = getIntent().getStringExtra("MainGun");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Weapons");

                            JSONObject ship = jsonArray.getJSONObject(weaponId);

                            wdmg = ship.getInt("DMG");
                            wrld = ship.getInt("RLD");
                            timeVolley = ship.getInt("Volley Time");
                            nVolleys = ship.getInt("N. Volleys");
                            enhanceDmg = ship.getInt("EnhanceDMG");
                            enhanceRld = ship.getInt("EnhanceRLD");
                            coeff = ship.getInt("Coefficient");
                            wfp = ship.getInt("FP");
                            waa = ship.getInt("AA");
                            range = ship.getInt("Range");
                            rangeShell = ship.getInt("Range Shell");
                            spread = ship.getInt("Spread");

                            wName = ship.getString("Nombre:");
                            ammo = ship.getString("Ammo");
                            wFaction = ship.getString("Faction");

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
    } //Parse weapons

    private void setVariables(){

    }
}
