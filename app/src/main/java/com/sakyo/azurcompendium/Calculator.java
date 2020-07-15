package com.sakyo.azurcompendium;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
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

public class Calculator extends AppCompatActivity {

    private RequestQueue mQueue;
    private static final int REQUEST_GET_SHIP_LOCATION = 0;
    private static final int REQUEST_GET_WEAPON_LOCATION = 0;
    private int shipId;
    private int weaponId;

    private TextView lblEnhance;
    private SeekBar sldEnhance;
    private TextView lblBaseDmg;
    private TextView lblRoF;
    private TextView lblCoeff;
    private TextView lblAmmo;
    private Button btnShip;
    private Button btnWeapon;

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

    private double FPBonus = 0;

    //region Weapon Stats
    private int enhanceLvl = 0;

    private double wdmg = 0;
    private double wrld = 0;
    private double enhancedDmg = 0;
    private double enhancedRld = 0;
    private double finalDmg = 0;
    private double finalRld = 0;
    private double timeVolley = 0;
    private int nVolleys = 0;
    private double enhanceDmg;
    private double enhanceRld;
    private double coeff = 0;
    private int wfp;
    private int waa;
    private int range;
    private int rangeShell;
    private int spread;
    private double totalCd = 0.0;

    private String wName = "None";
    private String ammo;
    private String wFaction;
    //endregion Weapon Stats

    private void Formatting(){

        MainDPS();

        lblEnhance.setText(getResources().getString
                (R.string.lblEnhance, enhanceLvl));

        lblBaseDmg.setText(getResources().getString
                (R.string.lblMainDMG, finalDmg, nVolleys));
        lblRoF.setText(getResources().getString
            (R.string.lblMainRoF, totalCd));
        lblCoeff.setText(getResources().getString
                (R.string.lblMainCoeff, (100*coeff), "%"));
        lblAmmo.setText(getResources().getString
                (R.string.lblMainAmmo, ammo));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        mQueue = Volley.newRequestQueue(this);

        lblEnhance = findViewById(R.id.viewLblEnhance);
        sldEnhance = findViewById(R.id.viewSldEnhance);

        lblBaseDmg = findViewById(R.id.viewLblBaseDmg);
        lblRoF = findViewById(R.id.viewLblBaseRoF);
        lblCoeff = findViewById(R.id.viewLblCoeff);
        lblAmmo = findViewById(R.id.viewLblAmmo);

        u = findViewById(R.id.textView);

        btnShip = findViewById(R.id.btnSelectSip);
        btnWeapon = findViewById(R.id.btnSelectWeapon);

        Formatting();

        sldEnhance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                enhanceLvl = progress;
                enhancedDmg = enhanceDmg*enhanceLvl;
                enhancedRld = enhanceRld*enhanceLvl;
                finalDmg = wdmg+enhancedDmg;
                finalRld = wrld+enhancedRld;
                totalCd = finalRld+timeVolley;

                lblEnhance.setText(getResources().getString
                        (R.string.lblEnhance, enhanceLvl));

                lblBaseDmg.setText(getResources().getString
                        (R.string.lblMainDMG, finalDmg, nVolleys));
                lblRoF.setText(getResources().getString
                        (R.string.lblMainRoF, totalCd));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {            }
        });
    }


    public void selectButton(View view){
        btnWeapon.setEnabled(true);

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
                            wrld = ship.getDouble("RLD");
                            timeVolley = ship.getDouble("Volley Time");
                            nVolleys = ship.getInt("N. Volleys");
                            enhanceDmg = ship.getDouble("EnhanceDMG");
                            enhanceRld = ship.getDouble("EnhanceRLD");
                            coeff = ship.getDouble("Coefficient");
                            wfp = ship.getInt("FP");
                            waa = ship.getInt("AA");
                            range = ship.getInt("Range");
                            rangeShell = ship.getInt("Range Shell");
                            spread = ship.getInt("Spread");

                            wName = ship.getString("Nombre:");
                            ammo = ship.getString("Ammo");
                            wFaction = ship.getString("Faction");

                            //setTexts();
                            Formatting();

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

    private void MainDPS(){
        //Apply enhances to Main gun damage and reload
        enhancedDmg = enhanceDmg*enhanceLvl;
        enhancedRld = enhanceRld*enhancedRld;
        finalDmg = wdmg+enhancedDmg;
        finalRld = wrld+enhancedRld;
        totalCd = finalRld+timeVolley;
        totalCd = wrld+timeVolley;

        //Firepower Bonus
        double totalFP = wfp+fp;
        FPBonus = 1+(totalFP/100);

        //Determine Ammo Modifiers
        String hull = getIntent().getStringExtra("Hull");

        double lightModifier = 0;
        double mediumModifier = 0;
        double heavyModifier = 0;


    }

}
