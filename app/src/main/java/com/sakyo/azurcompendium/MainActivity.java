package com.sakyo.azurcompendium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;




public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            }

//Assign hull type to Calculator

    public void destroyer_method(View view) {
        Intent i = new Intent(this, Calculator.class);
        i.putExtra("Ship","https://api.npoint.io/a296aa5cd2c25677387e");
        i.putExtra("MainGun","https://api.npoint.io/35bb066e41213e92dd02");
        i.putExtra("MainGun2", "https://api.npoint.io/b7caf4091a0dbbb7230e");
        i.putExtra("absCd", 0.26);
        startActivity(i);
    }
    public void lcruiser_method(View view) {
        Intent i = new Intent(this, Calculator.class);
        i.putExtra("Ship","https://api.npoint.io/17208540d3462e810aed");
        i.putExtra("MainGun", "https://api.npoint.io/b7caf4091a0dbbb7230e");
        i.putExtra("MainGun2", "https://api.npoint.io/35bb066e41213e92dd02");
        i.putExtra("absCd", 0.28);
        startActivity(i);
    }
    public void hcruiser_method(View view) {
        Intent i = new Intent(this, Calculator.class);
        i.putExtra("Ship","https://api.npoint.io/c21f85dc0dd09197d9b6");
        i.putExtra("MainGun", "https://api.npoint.io/3a176a725efde2880ef3");
        i.putExtra("absCd",0.3);
        startActivity(i);
    }
    public void battleship_method(View view) {
        Intent i = new Intent(this, Calculator.class);
        i.putExtra("Ship","https://api.npoint.io/bb80bfdd1243b58ca84d");
        i.putExtra("MainGun", "https://api.npoint.io/5c6585926945c5f1c65e");
        i.putExtra("absCd",0);
        startActivity(i);
    }
    public void carrier_method(View view) {
        Intent i = new Intent(this, Calculator.class);
        i.putExtra("Hull","CV");
        startActivity(i);
    }

    public void updateDatabase(View view){
    }


}


//And this is where my life went down :(

