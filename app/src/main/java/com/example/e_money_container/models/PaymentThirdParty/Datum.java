package com.example.e_money_container.models.PaymentThirdParty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("accountid")
    @Expose
    private Integer accountid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("payment_gateway_name")
    @Expose
    private String paymentGatewayName;
    @SerializedName("api_key")
    @Expose
    private String apiKey;
    @SerializedName("balance")
    @Expose
    private Integer balance;

    public Integer getAccountid() {
        return accountid;
    }

    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaymentGatewayName() {
        return paymentGatewayName;
    }

    public void setPaymentGatewayName(String paymentGatewayName) {
        this.paymentGatewayName = paymentGatewayName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
