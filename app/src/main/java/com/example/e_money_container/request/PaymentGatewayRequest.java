package com.example.e_money_container.request;

import com.example.e_money_container.models.PaymentGateway.PaymentGatewayModel;
import com.example.e_money_container.models.PaymentGatewayContainer.PaymentGatewayContainerModel;
import com.example.e_money_container.models.PaymentHistory.PaymentHistoryModel;
import com.example.e_money_container.models.PaymentThirdParty.PaymentThirdPartyModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface PaymentGatewayRequest {
    @GET("apis/gateways")
    Call<PaymentGatewayModel> getPaymentGateways();

    @GET("paymentgateways/containers")
    Call<PaymentGatewayContainerModel> getPaymentGatewaysContainer();

    @GET("emoneycontainers/integrations")
    Call<PaymentThirdPartyModel> paymentapi(@Header("Authorization") String token);
}
