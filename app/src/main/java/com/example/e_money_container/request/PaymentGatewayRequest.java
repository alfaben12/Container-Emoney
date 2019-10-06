package com.example.e_money_container.request;

import com.example.e_money_container.models.PaymentDecreaseApi.PaymentDecreaseApiModel;
import com.example.e_money_container.models.PaymentGateway.PaymentGatewayModel;
import com.example.e_money_container.models.PaymentGatewayContainer.PaymentGatewayContainerModel;
import com.example.e_money_container.models.PaymentHistory.PaymentHistoryModel;
import com.example.e_money_container.models.PaymentThirdParty.PaymentThirdPartyModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PaymentGatewayRequest {
    @GET("apis/gateways")
    Call<PaymentGatewayModel> getPaymentGateways();

    @GET("paymentgateways/containers")
    Call<PaymentGatewayContainerModel> getPaymentGatewaysContainer();

    @GET("emoneycontainers/integrations")
    Call<PaymentThirdPartyModel> paymentapi(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("paymentgateways")
    Call<PaymentThirdPartyModel> add_thirdparty(
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("payment_gateway_name") String payment_gateway_name,
            @Field("api_key") String api_key,
            @Field("balance") int balance
    );
}
