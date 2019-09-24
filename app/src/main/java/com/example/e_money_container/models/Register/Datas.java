
package com.example.e_money_container.models.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datas {

    @SerializedName("createdAt")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("updatedAt")
    @Expose
    private UpdatedAt updatedAt;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("roleid")
    @Expose
    private String roleid;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;

    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(CreatedAt createdAt) {
        this.createdAt = createdAt;
    }

    public UpdatedAt getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(UpdatedAt updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

}
