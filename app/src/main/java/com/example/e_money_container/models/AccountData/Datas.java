
package com.example.e_money_container.models.AccountData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datas {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("saving_balance")
    @Expose
    private Integer savingBalance;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("account_role")
    @Expose
    private AccountRole accountRole;
    @SerializedName("account_payment_container")
    @Expose
    private AccountPaymentContainer accountPaymentContainer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getSavingBalance() {
        return savingBalance;
    }

    public void setSavingBalance(Integer savingBalance) {
        this.savingBalance = savingBalance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public AccountRole getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(AccountRole accountRole) {
        this.accountRole = accountRole;
    }

    public AccountPaymentContainer getAccountPaymentContainer() {
        return accountPaymentContainer;
    }

    public void setAccountPaymentContainer(AccountPaymentContainer accountPaymentContainer) {
        this.accountPaymentContainer = accountPaymentContainer;
    }

}