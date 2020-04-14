package com.sample.zomatosample.data.remote;


import com.sample.zomatosample.BuildConfig;
import com.sample.zomatosample.data.model.api.restuarantsresponse.RestuarantListResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shahid on 11/04/20.
 */

public class ApiManager {

    private static ApiManager INSTANCE = null;
    private static OkHttpClient httpClient;
    private ApiInterface apiClient;

    private ApiManager() {
        httpClient = getHttpClient();
        apiClient = getRetrofitService();
    }

    public static ApiManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApiManager();
        }
        return INSTANCE;
    }

    private static ApiInterface getRetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiInterface.class);
    }

    /**
     * Method to create {@link OkHttpClient} builder by adding required headers in the {@link Request}
     *
     * @return OkHttpClient object
     */
    private OkHttpClient getHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder;
                        requestBuilder = original.newBuilder()
                                .header("user-key", BuildConfig.API_KEY)
                                .method(original.method(), original.body());
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .writeTimeout(20000, TimeUnit.MILLISECONDS)
                .build();
    }


    public Call<RestuarantListResponse> hitSearchRestaurantsApi(HashMap<String,Object> params) {
        return apiClient.searchRestaurants(params);
    }
}
