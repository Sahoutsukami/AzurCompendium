package com.sakyo.azurcompendium;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
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

    private RequestQueue mQueue;
    private static final int REQUEST_GET_SHIP_LOCATION = 0;
    private static final int REQUEST_GET_WEAPON_LOCATION = 0;
    private int shipId;
    private int weaponId;

    private TextView lblEnhance;
    private SeekBar sldEnhance;
    private Button btnShip;
    private Button btnWeapon;

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
    private int enhanceLvl = 0;

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

    private void Formating(){
        lblEnhance.setText(getResources().getString
                (R.string.lblEnhance, enhanceLvl));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        mQueue = Volley.newRequestQueue(this);

        lblEnhance = findViewById(R.id.viewLblEnhance);
        sldEnhance = findViewById(R.id.viewSldEnhance);
        btnShip = findViewById(R.id.btnSelectSip);
        btnWeapon = findViewById(R.id.btnSelectWeapon);

        Formating();

        sldEnhance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                enhanceLvl = progress;
                lblEnhance.setText(getResources().getString
                        (R.string.lblEnhance, enhanceLvl));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {            }
        });
    }


    public void selectButton(View view){

        String link = getIntent().getStringExtra("Ship");
        Intent i = new Intent(this, ShipSelection.class);
        i.putExtra("link", link).putExtra("from", "Ship").putExtra
                ("CurrentIdS", shipId).putExtra("CurrentIdW", weaponId);
        startActivityForResult(i, REQUEST_GET_SHIP_LOCATION);
    }

    public void selectWeapon(View view){
        String link = getIntent().getStringExtra("MainGun");
        Intent i = new Intent(this, ShipSelection.class);
        i.putExtra("link", link).putExtra("from", "Weapon").putExtra
                ("CurrentIdS", shipId).putExtra("CurrentIdW", weaponId);
        startActivityForResult(i, REQUEST_GET_WEAPON_LOCATION);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GET_SHIP_LOCATION && resultCode == Activity.RESULT_OK){
            shipId = data.getIntExtra("idShip",0);
            weaponId = data.getIntExtra("idWeapon",0);
            jsonParseStats();
            jsonParseMainGuns();
        }
    }    //Return
                                                                                                     //Position


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

                            setTexts();

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

                            setTexts();

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

    private void setTexts(){
        btnShip.setText(name);
        btnWeapon.setText(wName);
    }

}
