package com.formbuilder.model;

import com.formbuilder.util.GsonParser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class PRSubmitModel {
    @Expose
    @SerializedName(value="form_id")
    private int formId ;
    @Expose
    @SerializedName(value="version")
    private int version ;
    @Expose
    @SerializedName(value="application_id")
    private String applicationId ;
    @Expose
    @SerializedName(value="app_version")
    private String appVersion ;
    @Expose
    @SerializedName(value="timestamp")
    private String timestamp ;
    @Expose
    @SerializedName(value="app_name")
    private String appName ;
    @Expose
    @SerializedName(value="input_field_data")
    private List<DynamicInputModel> inputFieldData;

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<DynamicInputModel> getInputFieldData() {
        return inputFieldData;
    }

    public void setInputFieldData(List<DynamicInputModel> inputFieldData) {
        this.inputFieldData = inputFieldData;
    }

    public String toJson() {
        return GsonParser.toJson(this, new TypeToken<PRSubmitModel>(){});
    }
}
