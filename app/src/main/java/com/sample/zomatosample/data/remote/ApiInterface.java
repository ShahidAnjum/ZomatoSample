package com.sample.zomatosample.data.remote;


import com.sample.zomatosample.data.model.api.restuarantsresponse.RestuarantListResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Shahid on 11/04/20.
 */

public interface ApiInterface {

    @GET("search")
    Call<RestuarantListResponse> searchRestaurants(@QueryMap HashMap<String, Object> params);
}
