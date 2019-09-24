
package com.example.e_money_container.models.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedAt {

    @SerializedName("val")
    @Expose
    private String val;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

}
