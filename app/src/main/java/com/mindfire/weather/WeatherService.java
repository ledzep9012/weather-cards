package com.mindfire.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.lusfold.spinnerloading.SpinnerLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vyom on 4/13/2016.
 */
public class WeatherService extends AsyncTask<Void, Void, ArrayList<WeatherObject>> {
    private static final String TAG ="WeatherService" ;
    private String apiKey= "ec2d7002083aec3884e9ad313c30df8d";
    SpinnerLoading spinnerLoading;
    StringBuilder stringUrl=new StringBuilder("");
    String lng;
    String lat;
    Listener asyncListener=null;
    WeatherService(String lat, String lng, Listener listener, SpinnerLoading spinnerLoading){
        stringUrl.append("http://api.openweathermap.org/data/2.5/forecast/daily?");
        this.lat = lat;
        this.lng = lng;
        asyncListener=listener;
        this.spinnerLoading=spinnerLoading;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        spinnerLoading.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<WeatherObject> doInBackground(Void... params) {
        Log.d(TAG, "doInBackground: "+lat+" "+lng);
        stringUrl.append("lat="+lat).append("&lon="+lng).append("&cnt=15").append("&appid=").append(apiKey);
        Log.d("doInBackground: ",stringUrl.toString());

        ArrayList<WeatherObject> weatherList=new ArrayList<>();
        try {
            URL url = new URL(String.valueOf(stringUrl).replaceAll("\\s+", ""));
            HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.connect();
            Log.d(TAG, "doInBackground: "+url.toString());
            InputStream inputFeed=urlConnection.getInputStream();
            StringBuffer buffer=new StringBuffer();
            if(inputFeed==null){
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputFeed));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            String JsonFeed=String.valueOf(buffer);
            jsonToObject(JsonFeed,weatherList);
        } catch (MalformedURLException e) {
            Log.d(TAG, "doInBackground: INVALID URL ");
        } catch (IOException e) {
            Log.d(TAG, "doInBackground: Connection Problem");
        } catch (JSONException e) {
            Log.d(TAG, "doInBackground: Invalid JSON");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return weatherList;
    }

    @Override
    protected void onPostExecute(final ArrayList<WeatherObject> inWeather) {
        super.onPostExecute(inWeather);
        spinnerLoading.setVisibility(View.INVISIBLE);
        asyncListener.ReturnToMainThread(inWeather);


    }


    public void jsonToObject(String JsonFeed, ArrayList<WeatherObject> weatherList) throws JSONException, ParseException, IOException {


        JSONObject forecastJson=new JSONObject(JsonFeed);
        JSONObject city=forecastJson.getJSONObject("city");
        JSONObject coordinates=city.getJSONObject("coord");
        JSONArray list = forecastJson.getJSONArray("list");
        int i=0;
        while(i<list.length()){
            Log.d(TAG, "jsonToObject: "+i);
            WeatherObject weatherObject=new WeatherObject();
            JSONObject object=list.getJSONObject(i);
            String date = unixToDate(object.getString("dt"));
            JSONArray weather_forecast = object.getJSONArray("weather");
            JSONObject weather_icon_url = weather_forecast.getJSONObject(i-i);
            Log.d(TAG, "jsonToObject: "+weather_icon_url.getString("icon"));
            String icon_url = weather_icon_url.getString("icon");
            URL url = new URL("http://openweathermap.org/img/w/"+icon_url+".png");
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            weatherObject.setIcon(bmp);
            weatherObject.setDate(date);
            JSONObject temp=object.getJSONObject("temp");
            weatherObject.setMintemp(temp.getString("min"));
            weatherObject.setMaxtemp(temp.getString("max"));
            weatherObject.setCurrentTemp(temp.getString("day"));
            weatherObject.setCity(city.getString("name"));
            weatherList.add(weatherObject);

            i++;
        }

      //  weatherObject.setImgUrl(weather.getString("icon"));

    }

    private String unixToDate(String unix_timestamp) throws ParseException {
        long timestamp = Long.parseLong(unix_timestamp) * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d", Locale.UK);
        String date = sdf.format(timestamp);
        return date.toString();
    }

}
