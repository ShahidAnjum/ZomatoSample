package com.sample.zomatosample.ui.location;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {

    private MutableLiveData<Boolean> shouldFetchLocation = new MutableLiveData<>();
    private LocationRepo  mLocationRepo = new LocationRepo();
    public void onClickUpdateLocation() {
        shouldFetchLocation.setValue(true);
    }

    public MutableLiveData<Boolean> shouldFetchLocation() {
        return shouldFetchLocation;
    }

    public void saveAddress(String jobAddress) {
      mLocationRepo.saveUserAddress(jobAddress);
    }

    public void saveLatLng(Location location) {
      mLocationRepo.saveUserLatLng(location);
    }
}
