package com.sample.zomatosample.data;

import android.content.Context;
import android.location.Location;

import com.sample.zomatosample.constant.AppConstants;
import com.sample.zomatosample.data.local.prefs.PreferenceManager;
import com.sample.zomatosample.data.model.api.restuarantsresponse.RestuarantListResponse;
import com.sample.zomatosample.data.remote.ApiManager;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;


public class DataManager {

    private static DataManager instance;
    private ApiManager apiManager;
    private PreferenceManager mPrefManager;
    private Context context;

    private DataManager(Context context) {
        this.context = context;
        mPrefManager = PreferenceManager.getInstance();
        apiManager = ApiManager.getInstance();
    }

    /**
     * Returns the single instance of {@link DataManager} if
     * {@link #init(Context)} is called first
     *
     * @return instance
     */
    public static DataManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Call init() before getInstance()");
        }
        return instance;
    }

    /**
     * Method used to create an instance of {@link DataManager}
     *
     * @param context of the application passed from the {@link com.sample.zomatosample.application.SampleZomatoApplication}
     * @return instance if it is null
     */
    public synchronized static DataManager init(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }


    public Call<RestuarantListResponse> hitSearchRestaurantsApi(HashMap<String, Object> params) {
        return apiManager.hitSearchRestaurantsApi(params);
    }

    public void clearPreferences() {
        mPrefManager.clearAllPrefs(context);
    }

    public void saveUserAddress(String jobAddress) {
        mPrefManager.putString(context, AppConstants.PreferenceConstants.CURRENT_LOCATION, jobAddress);
    }

    public void saveUserLatLng(Location location) {
        mPrefManager.putString(context, AppConstants.PreferenceConstants.CURRENT_LATITUDE,
                String.valueOf(location.getLatitude()));
        mPrefManager.putString(context, AppConstants.PreferenceConstants.CURRENT_LONGITUDE,
                String.valueOf(location.getLongitude()));
    }

    public String getUserAddress() {
        return mPrefManager.getString(context, AppConstants.PreferenceConstants.CURRENT_LOCATION);
    }

    public String getLatitude() {
        return mPrefManager.getString(context, AppConstants.PreferenceConstants.CURRENT_LATITUDE);
    }

    public String getLongitude() {
        return mPrefManager.getString(context, AppConstants.PreferenceConstants.CURRENT_LONGITUDE);
    }
}
