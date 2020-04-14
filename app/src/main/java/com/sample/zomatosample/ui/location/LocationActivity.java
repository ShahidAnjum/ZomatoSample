package com.sample.zomatosample.ui.location;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.sample.zomatosample.AddressService;
import com.sample.zomatosample.BaseActivity;
import com.sample.zomatosample.R;
import com.sample.zomatosample.constant.AppConstants;
import com.sample.zomatosample.databinding.ActivityLocationBinding;
import com.sample.zomatosample.dialogs.LocationDialogFragment;
import com.sample.zomatosample.ui.search.SearchActivity;
import com.sample.zomatosample.util.AddressResultReceiver;

public class LocationActivity extends BaseActivity
        implements LocationDialogFragment.LocationDialogListener,
        AddressResultReceiver.AddressFetchListener {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 44;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private ActivityLocationBinding mBinding;
    private LocationViewModel mViewModel;
    private AddressResultReceiver mResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        mViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        mBinding.setViewModel(mViewModel);
        initialPageSetup();
    }

    private void initialPageSetup() {
        observerSetup();
        mResultReceiver = new AddressResultReceiver(new Handler(), this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                mViewModel.saveLatLng(location);
                startIntentService(location);
            }
        };
    }

    private void observerSetup() {
        mViewModel.shouldFetchLocation().observe(this, shouldFetch -> {
            if (shouldFetch) {
                if (checkPermissions()) getLastLocation();
                else requestPermissions();
            }
        });
    }

    private void getLastLocation() {
        if (isLocationEnabled()) {
            mFusedLocationClient.getLastLocation().addOnCompleteListener(
                    task -> {
                        Location location = task.getResult();
                        if (location == null) requestNewLocationData();
                        else {
                            mViewModel.saveLatLng(location);
                            startIntentService(location);
                        }
                    }
            );
        } else {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }


    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(0)
                .setFastestInterval(0)
                .setNumUpdates(1);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(LocationActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    DialogFragment dialog = new LocationDialogFragment();
                    dialog.show(getSupportFragmentManager(), "LocationDialogFragment");
                }
            }
        }
    }

    private void startIntentService(Location location) {
        Intent intent = new Intent(LocationActivity.this, AddressService.class);
        intent.putExtra(AppConstants.RECEIVER, mResultReceiver);
        intent.putExtra(AppConstants.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    @Override
    public void onGoToSettingsClick(DialogFragment dialog) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + this.getPackageName()));
        startActivity(intent);
    }

    @Override
    public void onAddressReceived(String address) {
        mViewModel.saveAddress(address);
        startActivity(new Intent(LocationActivity.this, SearchActivity.class));
        finish();
    }
}
