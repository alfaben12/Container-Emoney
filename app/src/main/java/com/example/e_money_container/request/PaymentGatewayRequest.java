package com.example.e_money_container.request;

import com.example.e_money_container.models.PaymentGateway.PaymentGatewayModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PaymentGatewayRequest {
    @GET("apis/gateways")
    Call<PaymentGatewayModel> getPaymentGateways();
}
