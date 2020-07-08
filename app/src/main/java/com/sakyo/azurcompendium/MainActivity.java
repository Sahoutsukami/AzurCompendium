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
        i.putExtra("Hull","DD");
        startActivity(i);
    }
    public void lcruiser_method(View view) {
        Intent i = new Intent(this, Calculator.class);
        i.putExtra("Hull","CL");
        startActivity(i);
    }
    public void hcruiser_method(View view) {
        Intent i = new Intent(this, Calculator.class);
        i.putExtra("Hull","CA");
        startActivity(i);
    }
    public void battleship_method(View view) {
        Intent i = new Intent(this, Calculator.class);
        i.putExtra("Hull","BB");
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

