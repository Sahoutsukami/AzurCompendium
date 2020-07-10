package com.sakyo.azurcompendium;

public class CardsShip {
    private int imgShip;
    private String lblShip;

    public CardsShip(int icon, String nameShip){
        imgShip = icon;
        lblShip = nameShip;
    }

    public int getIcon (){
        return imgShip;
    }
    public String getName(){
        return lblShip;
    }

}
