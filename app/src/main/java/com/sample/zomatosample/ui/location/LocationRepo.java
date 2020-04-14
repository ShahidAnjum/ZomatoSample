package com.sample.zomatosample.ui.location;

import android.location.Location;

import com.sample.zomatosample.data.DataManager;

public class LocationRepo {

    public void saveUserAddress(String jobAddress) {
            DataManager.getInstance().saveUserAddress(jobAddress);
    }

    public void saveUserLatLng(Location location) {
           DataManager.getInstance().saveUserLatLng(location);
    }
}
