package com.sample.zomatosample.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

import com.sample.zomatosample.constant.AppConstants;
import com.sample.zomatosample.ui.location.LocationActivity;
import com.sample.zomatosample.ui.search.SearchActivity;

public class AddressResultReceiver extends ResultReceiver {

    AddressFetchListener mAddressFetchListener;

    public interface AddressFetchListener {
      public void onAddressReceived(String address);
    }


    public AddressResultReceiver(Handler handler, Context context) {
        super(handler);
        mAddressFetchListener = (AddressFetchListener)context;

    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultData == null) {
            return;
        }
        String jobAddress = resultData.getString(AppConstants.CURRENT_LOCATION);
        mAddressFetchListener.onAddressReceived(jobAddress);
    }
}