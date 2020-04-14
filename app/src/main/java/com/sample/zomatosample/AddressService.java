package com.sample.zomatosample;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

import com.sample.zomatosample.constant.AppConstants;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddressService extends IntentService {


    private final String TAG = "AddressService";
    protected ResultReceiver mReceiver;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public AddressService() {
        super("AddressService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        if (intent == null) {
            return;
        }
        String errorMessage = "";

        // Get the location passed to this service through an extra.
        mReceiver = intent.getParcelableExtra(AppConstants.RECEIVER);
        Location location = intent.getParcelableExtra(AppConstants.LOCATION_DATA_EXTRA);
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = "Service not available";
            Log.e(TAG, errorMessage, ioException);

        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "Invalid lat lng used";
            Log.e(TAG, errorMessage + ". " + "Latitude = " + location.getLatitude() + ", Longitude = " + location.getLongitude(), illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "No address found";
                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(AppConstants.FAILURE_RESULT, errorMessage, null);
        } else {
            Address address = addresses.get(0);
            String addr = address.getAddressLine(0);
            deliverResultToReceiver(AppConstants.SUCCESS_RESULT, "Success", addr);
        }
    }


    private void deliverResultToReceiver(int resultCode, String message, String address) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.RECEIVER, message);
        if (address != null) {
            bundle.putString(AppConstants.CURRENT_LOCATION, address);
        }
        mReceiver.send(resultCode, bundle);
    }

}