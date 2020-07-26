package com.sakyo.azurcompendium;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
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

public class Calculator extends AppCompatActivity  {

    private RequestQueue mQueue;
    private static final int REQUEST_GET_SHIP_LOCATION = 0;
    private static final int REQUEST_GET_WEAPON_LOCATION = 0;
    private int shipId;
    private int weaponId;


    private RadioButton rdDD;
    private RadioButton rdCL;
    private TextView lblEnhance;
    private SeekBar sldEnhance;

    private TextView lblBaseDmg;
    private TextView lblRoF;
    private TextView lblCoeff;
    private TextView lblAmmo;

    private TextView lblDPSTitle;
    private TextView lblDPS;
    private Button btnShip;
    private Button btnWeapon;

    private TextView u;

    // region Ship Stats
    private int hp;
    private int eva;
    private int fp;
    private int trp;
    private double rld;
    private int avi;
    private int aa;
    private int lck;
    private int acc;
    private int asw;
    private int speed;
    private int oil;
    private double eff1 = 0;
    private double eff2 = 0;
    private double eff3 = 0;
    private int mgm;
    private String name = "-- Select your Shipfu";
    private String armor;
    private String faction;
    private String slot1 = "Nothing";
    private String slot2;
    // endregion Sip Stats

    private double totalFP = 0;
    private double FPBonus = 0;
    private double absCd = 0;

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
    private double lightModifier = 0;
    private double mediumModifier = 0;
    private double heavyModifier = 0;
    private int wfp;
    private int waa;
    private int range;
    private int rangeShell;
    private int spread;
    private double totalCd = 0.0;

    private String wName = "-- Select your weapon --";
    private String ammo = "None";
    private String wFaction;
    //endregion Weapon Stats

    //Update Labels
    private void Formatting(){
        Toast.makeText(this, slot1, Toast.LENGTH_SHORT).show();
        if (slot1.equals("DD/CL Gun")) {
            rdDD.setEnabled(true);
            rdCL.setEnabled(true);
        } else {
            rdDD.setEnabled(false);
            rdCL.setEnabled(false);
        }

        MainDPS();

        btnShip.setText(name);
        btnWeapon.setText(wName);

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

        rdDD = findViewById(R.id.viewRdDD);
        rdCL = findViewById(R.id.viewRdCL);

        lblEnhance = findViewById(R.id.viewLblEnhance);
        sldEnhance = findViewById(R.id.viewSldEnhance);

        lblDPSTitle = findViewById(R.id.viewLblDPS);
        absCd = getIntent().getDoubleExtra("absCd",0); //get absolute cooldown
        if (absCd == 0){
            lblDPSTitle.setText(getResources().getString(R.string.lblMainGunBB));
        }

        lblBaseDmg = findViewById(R.id.viewLblBaseDmg);
        lblRoF = findViewById(R.id.viewLblBaseRoF);
        lblCoeff = findViewById(R.id.viewLblCoeff);
        lblAmmo = findViewById(R.id.viewLblAmmo);
        lblDPS = findViewById(R.id.viewLblDpsLight);

        u = findViewById(R.id.textView);

        btnShip = findViewById(R.id.btnSelectSip);
        btnWeapon = findViewById(R.id.btnSelectWeapon);

        Formatting();

        //Enhance is updated
        sldEnhance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                enhanceLvl = progress;
                MainDPS();
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

    //go to selection activity (Ships)
    public void selectButton(View view){
        btnWeapon.setEnabled(true);

        String link = getIntent().getStringExtra("Ship");
        Intent i = new Intent(this, ShipSelection.class);
        i.putExtra("link", link).putExtra("from", "Ship").putExtra
                ("CurrentIdS", shipId).putExtra("CurrentIdW", weaponId);
        startActivityForResult(i, REQUEST_GET_SHIP_LOCATION);
    }

    //go to selection activity (Weapons)
    public void selectWeapon(View view){
        String link = getIntent().getStringExtra("MainGun");
        Intent i = new Intent(this, ShipSelection.class);
        i.putExtra("link", link).putExtra("from", "Weapon").putExtra
                ("CurrentIdS", shipId).putExtra("CurrentIdW", weaponId);
        startActivityForResult(i, REQUEST_GET_WEAPON_LOCATION);
    }

    //return from activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GET_SHIP_LOCATION && resultCode == Activity.RESULT_OK){
            shipId = data.getIntExtra("idShip",0);
            weaponId = data.getIntExtra("idWeapon",0);
            jsonParseStats();
            jsonParseMainGuns();


        }
    }

    //Parse Ship stats (online)
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
                            eff1 = ship.getDouble("Eff1");
                            eff2 = ship.getDouble("Eff2");
                            eff3 = ship.getDouble("Eff3");
                            mgm = ship.getInt("#MainGun");
                            name = ship.getString("Nombre");
                            armor = ship.getString("Armor");
                            faction = ship.getString("Faction");
                            slot1 = ship.getString("Slot 1");
                            slot2 = ship.getString("Slot 2");

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
    }


    public void onClickListener (View view) {
        jsonParseMainGuns();
    }

    //Parse main gun stats (online)
    private void jsonParseMainGuns(){

        String url;
        switch ((int) (absCd*100)) {
            case 26:
                if (rdCL.isChecked()) {
                    url = getIntent().getStringExtra("MainGun2");
                } else {
                    url = getIntent().getStringExtra("MainGun");
                }
                break;
            case 28:
                if (rdDD.isChecked()) {
                    url = getIntent().getStringExtra("MainGun2");
            } else {
                    url = getIntent().getStringExtra("MainGun");
                }
                break;
            default: url = getIntent().getStringExtra("MainGun");
        }

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
                            lightModifier = ship.getDouble("Light");
                            mediumModifier = ship.getDouble("Medium");
                            heavyModifier = ship.getDouble("Heavy");
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

    }

    //Calculate DPS main gun
    private void MainDPS(){

        //Apply enhances to Main gun damage and reload
        enhancedDmg = enhanceDmg*enhanceLvl;
        enhancedRld = enhanceRld*enhanceLvl;
        finalDmg = wdmg+enhancedDmg;
        finalRld = wrld+enhancedRld;
        totalCd = finalRld+timeVolley+absCd;

        //Firepower Bonus
        totalFP = fp+wfp;
        FPBonus = 1+(totalFP/100);

        //Calculate DPS
        double cd = (finalRld*(Math.sqrt(200/(rld+100))))+timeVolley+absCd; //Real DPS
        //double cd = totalCd;                                                  //Only Weapon
        double dps = (finalDmg*coeff*eff1*FPBonus*nVolleys);                //Real DPS
        //double dps = (finalDmg*coeff*nVolleys);                               //Only Weapon

        if (absCd == 0) {
            lblDPS.setText(getResources().getString(R.string.lblMainGunDpsNum,(dps * lightModifier),
                    (dps * mediumModifier), (dps * heavyModifier)));
        }
        else {
            lblDPS.setText(getResources().getString(R.string.lblMainGunDpsNum, ((dps * lightModifier) / cd),
                    ((dps * mediumModifier) / cd), ((dps * heavyModifier) / cd)));
        }

    }


}
