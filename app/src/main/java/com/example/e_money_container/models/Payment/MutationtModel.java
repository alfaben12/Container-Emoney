package com.example.e_money_container.models.Payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MutationtModel {
    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("nominal")
    @Expose
    private String nominal;
    @SerializedName("payment_gateway_name")
    @Expose
    private String paymentGatewayName;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("accountid")
    @Expose
    private String accountid;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getPaymentGatewayName() {
        return paymentGatewayName;
    }

    public void setPaymentGatewayName(String paymentGatewayName) {
        this.paymentGatewayName = paymentGatewayName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }
}