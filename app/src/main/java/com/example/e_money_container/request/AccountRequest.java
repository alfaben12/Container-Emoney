package com.example.e_money_container.request;

import com.example.e_money_container.models.Account.AccountModel;
import com.example.e_money_container.models.AccountData.AccountDataModel;
import com.example.e_money_container.models.Login.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccountRequest {
    @GET("accounts/{code}")
    Call<AccountModel> getAccountByCode(@Path("code") String code);

    @GET("accounts")
    Call<AccountDataModel> account(@Header("Authorization") String token);

    @FormUrlEncoded
    @PUT("accounts")
    Call<AccountModel> updateAccount(
            @Header("Authorization") String token,
            @Field("username") String username,
            @Field("password") String password,
            @Field("full_name") String full_name,
            @Field("email") String email,
            @Field("address") String address);

    @FormUrlEncoded
    @PUT("accounts/savings")
    Call<AccountModel> updateAccountSaving(
            @Header("Authorization") String token,
            @Field("balance_saving") String balance_saving);

    @FormUrlEncoded
    @POST("emoneycontainers")
    Call<AccountModel> updatePaymentContainer(
            @Header("Authorization") String token,
            @Field("payment_gateway_containerid") String payment_gateway_containerid,
            @Field("payment_gateway_account_apikey") String payment_gateway_account_apikey);
}
