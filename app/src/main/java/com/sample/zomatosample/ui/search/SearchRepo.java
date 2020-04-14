package com.sample.zomatosample.ui.search;

import androidx.lifecycle.MutableLiveData;

import com.sample.zomatosample.data.DataManager;
import com.sample.zomatosample.data.model.api.FailureResponse;
import com.sample.zomatosample.data.model.api.restuarantsresponse.GroupedRestuarants;
import com.sample.zomatosample.data.model.api.restuarantsresponse.Restaurant;
import com.sample.zomatosample.data.model.api.restuarantsresponse.Restaurant_;
import com.sample.zomatosample.data.model.api.restuarantsresponse.RestuarantListResponse;
import com.sample.zomatosample.ui.base.NetworkCallback;
import com.sample.zomatosample.ui.base.RichMediatorLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchRepo {

    /**
     * This method is used to hit searchRestaurants api
     *
     * @param restuarantsLiveData live data object
     */
    public void searchRestaurants(final RichMediatorLiveData<ArrayList<GroupedRestuarants>>
                                          restuarantsLiveData, String query) {

        //---- forming search queries for api call
        final HashMap<String, Object> params = new HashMap<>();
        params.put("q", query);
        params.put("lat", Double.valueOf(DataManager.getInstance().getLatitude()));
        params.put("lon", Double.valueOf(DataManager.getInstance().getLongitude()));

        DataManager.getInstance().hitSearchRestaurantsApi(params).enqueue(new NetworkCallback<RestuarantListResponse>() {

            @Override
            public void onSuccess(RestuarantListResponse result) {
                //--- Data parsing, we can create
                HashMap<String, List<Restaurant_>> restuarantsByCuisine = new HashMap<>();
                for (Restaurant restaurant : result.getRestaurants()) {
                    if (!restuarantsByCuisine.containsKey(restaurant.getRestaurant().getCuisines())) {
                        restuarantsByCuisine.put(restaurant.getRestaurant().getCuisines(), new ArrayList<>());
                    }
                    restuarantsByCuisine.get(restaurant.getRestaurant().getCuisines()).add(restaurant.getRestaurant());
                }
                ArrayList<GroupedRestuarants> groupedRestuarants = new ArrayList<>();
                for (Map.Entry<String, List<Restaurant_>> mapElement : restuarantsByCuisine.entrySet()) {
                        GroupedRestuarants resturants = new GroupedRestuarants();
                        resturants.setCuisineType(mapElement.getKey());
                        resturants.setRestaurants(mapElement.getValue());
                        groupedRestuarants.add(resturants);
                    }
                restuarantsLiveData.setValue(groupedRestuarants);
            }

            @Override
            public void onFailure(FailureResponse failureResponse) {
                restuarantsLiveData.setFailure(failureResponse);
            }

            @Override
            public void onError(Throwable t) {
                restuarantsLiveData.setError(t);

            }

        });
    }

    public void updateUserAddress(MutableLiveData<String> mUserAddress) {
        String address = DataManager.getInstance().getUserAddress();
        mUserAddress.setValue(address);
    }
}
