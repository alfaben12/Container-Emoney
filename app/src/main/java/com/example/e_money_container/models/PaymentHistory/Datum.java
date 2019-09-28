
package com.example.e_money_container.models.PaymentHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("from_payment_gateway_name")
    @Expose
    private String fromPaymentGatewayName;
    @SerializedName("to_payment_gateway_name")
    @Expose
    private String toPaymentGatewayName;
    @SerializedName("nominal")
    @Expose
    private Integer nominal;
    @SerializedName("is_transferred")
    @Expose
    private Integer isTransferred;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFromPaymentGatewayName() {
        return fromPaymentGatewayName;
    }

    public void setFromPaymentGatewayName(String fromPaymentGatewayName) {
        this.fromPaymentGatewayName = fromPaymentGatewayName;
    }

    public String getToPaymentGatewayName() {
        return toPaymentGatewayName;
    }

    public void setToPaymentGatewayName(String toPaymentGatewayName) {
        this.toPaymentGatewayName = toPaymentGatewayName;
    }

    public Integer getNominal() {
        return nominal;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    public Integer getIsTransferred() {
        return isTransferred;
    }

    public void setIsTransferred(Integer isTransferred) {
        this.isTransferred = isTransferred;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}