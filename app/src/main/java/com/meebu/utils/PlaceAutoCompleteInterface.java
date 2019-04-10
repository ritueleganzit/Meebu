package com.meebu.utils;

import com.meebu.model.PlaceLatLong;
import com.meebu.model.PredictionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceAutoCompleteInterface
{
    /*
     * @Query() appends the parameter to the HTTP request.
     * In this case, the request made by retrofit looks like
     * BASEURL/api/place/autocomplete/json?types=address&key=YOUR-KEY&input=addressFromUser
     * */
    @GET("api/place/autocomplete/json?types=address&key=AIzaSyCZJDD1Osg2A3bYeAQG6UtTD9fll8t5-IU")
    Call<PredictionResponse> loadPredictions(@Query("input") String address);


    @GET("api/place/details/json?fields=geometry,name&key=AIzaSyCZJDD1Osg2A3bYeAQG6UtTD9fll8t5-IU")
    Call<PlaceLatLong> getLatLong(@Query("placeid") String placeid);
}


//https://maps.googleapis.com/maps/
