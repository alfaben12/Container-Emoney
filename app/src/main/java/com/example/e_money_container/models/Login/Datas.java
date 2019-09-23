
package com.example.e_money_container.models.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datas {

    @SerializedName("accountData")
    @Expose
    private AccountData accountData;
    @SerializedName("jwtTokenData")
    @Expose
    private String jwtTokenData;

    public AccountData getAccountData() {
        return accountData;
    }

    public void setAccountData(AccountData accountData) {
        this.accountData = accountData;
    }

    public String getJwtTokenData() {
        return jwtTokenData;
    }

    public void setJwtTokenData(String jwtTokenData) {
        this.jwtTokenData = jwtTokenData;
    }

}
