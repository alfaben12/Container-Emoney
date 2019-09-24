
package com.example.e_money_container.models.PaymentHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("uuid")
    @Expose
    private Object uuid;
    @SerializedName("from_payment_gateway_name")
    @Expose
    private String fromPaymentGatewayName;
    @SerializedName("to_payment_gateway_name")
    @Expose
    private String toPaymentGatewayName;
    @SerializedName("nominal")
    @Expose
    private Integer nominal;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getUuid() {
        return uuid;
    }

    public void setUuid(Object uuid) {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
