package com.sample.zomatosample.data.model.api.restuarantsresponse;

import java.util.List;

public class GroupedRestuarants {
    private String cuisineType;
    private List<Restaurant_> restaurants = null;

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public List<Restaurant_> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant_> restaurants) {
        this.restaurants = restaurants;
    }
}
