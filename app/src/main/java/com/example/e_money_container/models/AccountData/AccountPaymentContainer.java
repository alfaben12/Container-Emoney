
package com.example.e_money_container.models.AccountData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountPaymentContainer {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("payment_gateway_containerid")
    @Expose
    private Integer paymentGatewayContainerid;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaymentGatewayContainerid() {
        return paymentGatewayContainerid;
    }

    public void setPaymentGatewayContainerid(Integer paymentGatewayContainerid) {
        this.paymentGatewayContainerid = paymentGatewayContainerid;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
