package com.mindfire.weather;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.lusfold.spinnerloading.SpinnerLoading;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    private TextView city;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    String x,y;
    Listener listener;
    SpinnerLoading spinnerLoading;
    public Context mContext;
    private static String LOG_TAG = "CardViewActivity";
    GoogleApiClient mGoogleApiClient;
    private Location mLastLocation=null;

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        x = String.valueOf(mLastLocation.getLatitude());
        y = String.valueOf(mLastLocation.getLongitude());
        weatherService(x, y, listener,spinnerLoading);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city =(TextView) findViewById(R.id.cityText);
        spinnerLoading = (SpinnerLoading) findViewById(R.id.spinner);
        mContext = getApplicationContext();
        mRecyclerView=(RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(LocationServices.API)
                .build();



        listener = new Listener() {

            @Override
            public void ReturnToMainThread(ArrayList<WeatherObject> weatherObject) {
                city.setText(weatherObject.get(0).getCity());
                mAdapter = new MyAdapter(getApplicationContext(),weatherObject);
                mRecyclerView.setAdapter(mAdapter);

            }
        };

    }

    public void weatherService(String lat, String lng, Listener listener,SpinnerLoading spinnerLoading){
        WeatherService weatherService = new WeatherService(lat, lng ,  listener,spinnerLoading);
        weatherService.execute();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
