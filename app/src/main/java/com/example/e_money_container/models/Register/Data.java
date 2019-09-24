
package com.example.e_money_container.models.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("datas")
    @Expose
    private Datas datas;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Datas getDatas() {
        return datas;
    }

    public void setDatas(Datas datas) {
        this.datas = datas;
    }

}
