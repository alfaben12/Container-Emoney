package com.example.e_money_container.models.Payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentApiModel {
    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("nominal")
    @Expose
    private String nominal;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("accountid")
    @Expose
    private Integer accountid;
    @SerializedName("from_accountid")
    @Expose
    private String fromAccountid;
    @SerializedName("from_payment_gateway_name")
    @Expose
    private String fromPaymentGatewayName;
    @SerializedName("message")
    @Expose
    private String message;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getAccountid() {
        return accountid;
    }

    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }

    public String getFromAccountid() {
        return fromAccountid;
    }

    public void setFromAccountid(String fromAccountid) {
        this.fromAccountid = fromAccountid;
    }

    public String getFromPaymentGatewayName() {
        return fromPaymentGatewayName;
    }

    public void setFromPaymentGatewayName(String fromPaymentGatewayName) {
        this.fromPaymentGatewayName = fromPaymentGatewayName;
    }

    public String getMessageData() {
        return message;
    }

    public void setMessageData(String message) {
        this.message = message;
    }
}
