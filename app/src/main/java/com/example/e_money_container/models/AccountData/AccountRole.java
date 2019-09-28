
package com.example.e_money_container.models.AccountData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountRole {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("transaction_limit")
    @Expose
    private Integer transactionLimit;
    @SerializedName("transaction_limit_count")
    @Expose
    private Integer transactionLimitCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(Integer transactionLimit) {
        this.transactionLimit = transactionLimit;
    }

    public Integer getTransactionLimitCount() {
        return transactionLimitCount;
    }

    public void setTransactionLimitCount(Integer transactionLimitCount) {
        this.transactionLimitCount = transactionLimitCount;
    }

}
