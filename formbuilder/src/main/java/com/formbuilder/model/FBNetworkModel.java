package com.formbuilder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amit on 3/29/2018.
 */

public class FBNetworkModel {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private String data;

    public FBNetworkModel() {

    }

    public FBNetworkModel(String status, String data) {
        this.status = status;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
