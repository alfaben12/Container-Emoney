package com.example.e_money_container.request;

import com.example.e_money_container.models.Payment.MutationtModel;
import com.example.e_money_container.models.Payment.PaymentMoveModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PaymentRequest {

    @FormUrlEncoded
    @POST("apis/mutations")
    Call<MutationtModel> mutation(
            @Field("nominal") int nominal,
            @Field("payment_gateway_name") String payment_gateway_name
    );

    @FormUrlEncoded
    @POST("emoneycontainers/move/")
    Call<PaymentMoveModel> pay_move(
            @Field("payment_gateway_name") String payment_gateway_name,
            @Field("nominal") int nominal,
            @Field("uuid") String uuid,
            @Field("accountid") String accountid
    );
}