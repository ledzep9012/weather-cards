package com.mindfire.weather;

import android.graphics.Bitmap;

/**
 * Created by Vyom on 4/14/2016.
 */
public class WeatherObject {
    private String city;
    private String currentTemp;
    private String mintemp;
    private String maxtemp;
    private Bitmap icon;
    //private String imgUrl;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String date;

    public String getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = String.valueOf(Double.parseDouble(currentTemp) - 273.15).substring(0,4);
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMintemp() {
        return mintemp;
    }

    public void setMintemp(String mintemp) {
        this.mintemp =  String.valueOf(Double.parseDouble(mintemp) - 273.15).substring(0,4);
    }

    public String getMaxtemp() {
        return maxtemp;
    }

    public void setMaxtemp(String maxtemp) {
        this.maxtemp =  String.valueOf(Double.parseDouble(maxtemp)  - 273.15).substring(0, 4);
    }



    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
}
