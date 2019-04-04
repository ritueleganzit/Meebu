package com.meebu.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by eleganz on 1/11/18.
 */

public interface ApiInterface {




    @FormUrlEncoded
    @POST("/createuser")
    public void createUser(
            @Field("mobile") String mobile,
            Callback<Response> callback
    );

@FormUrlEncoded
    @POST("/verifycode")
    public void verifyCode(
            @Field("mobile") String mobile,
            @Field("code") String code,
            @Field("user_type") String user_type ,
            Callback<Response> callback
    );

@FormUrlEncoded
    @POST("/login")
    public void login(
            @Field("email") String mobile,
            @Field("password") String code,
            @Field("user_type") String user_type ,
            Callback<Response> callback
    );


@FormUrlEncoded
    @POST("/updateuserdata")
    public void updateUserData(
            @Field("fullname") String fullname,
            @Field("email") String email,
            @Field("password") String password ,
            @Field("mobile") String mobile ,
            Callback<Response> callback
    );

@FormUrlEncoded
    @POST("/forgetpassword")
    public void forgotPassword(
            @Field("email") String email,
            @Field("user_type") String user_type,

            Callback<Response> callback
    );

}
