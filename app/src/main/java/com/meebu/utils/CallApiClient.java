package com.meebu.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CallApiClient
{
    public static String BASE_URL="https://maps.googleapis.com/maps/";
    public static Retrofit retrofit=null;

    public static Retrofit getRetrofit()
    {
        if(retrofit==null)
        {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
