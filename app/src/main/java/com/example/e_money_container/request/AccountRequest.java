package com.example.e_money_container.request;

import com.example.e_money_container.models.Account.AccountModel;
import com.example.e_money_container.models.AccountData.AccountDataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccountRequest {
    @GET("accounts/{code}")
    Call<AccountModel> getAccountByCode(@Path("code") String code);

    @GET("accounts")
    Call<AccountDataModel> account(@Header("Authorization") String token);
}
