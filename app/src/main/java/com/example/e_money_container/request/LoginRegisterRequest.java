package com.example.e_money_container.request;

import com.example.e_money_container.models.Login.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginRegisterRequest {
    @FormUrlEncoded
    @POST("logins")
    Call<LoginModel> login(
            @Field("username") String username,
            @Field("password") String password
    );
}
