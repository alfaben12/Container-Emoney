package com.example.e_money_container.request;

import com.example.e_money_container.activities.PaymentGateway;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PaymentGatewayRequest {
    @GET("/accounts/")
    Call<PaymentGateway> getPaymentGateways();
}
