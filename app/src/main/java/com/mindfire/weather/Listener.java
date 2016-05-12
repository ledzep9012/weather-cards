package com.mindfire.weather;

import java.util.ArrayList;

/**
 * Created by Vyom on 4/14/2016.
 */
public interface Listener {
    public void ReturnToMainThread(ArrayList<WeatherObject> weatherObject);
}
