package com.formbuilder.model;

public class FormLocationProperties {

    public String apiKey = "";
    public boolean isEnableSearchBar = true;
    public boolean isEnableAddressLine1 = true;
    public boolean isEnableAddressLine2 = true;
    public boolean isEnableCityDetails = true;
    public boolean isEnableButtonMap = true;
    public boolean isEnableButtonDirection = true;
    public boolean isEnableTranslucentStatus = true;
    public String hintAddressLine1;

    public static FormLocationProperties Builder() {
        return new FormLocationProperties();
    }

    public String getApiKey() {
        return apiKey;
    }

    public FormLocationProperties setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public FormLocationProperties setEnableSearchBar(boolean enableSearchBar) {
        isEnableSearchBar = enableSearchBar;
        return this;
    }

    public FormLocationProperties setEnableAddressLine1(boolean enableAddressLine1) {
        isEnableAddressLine1 = enableAddressLine1;
        return this;
    }

    public FormLocationProperties setEnableAddressLine2(boolean enableAddressLine2) {
        isEnableAddressLine2 = enableAddressLine2;
        return this;
    }

    public FormLocationProperties setEnableCityDetails(boolean enableCityDetails) {
        isEnableCityDetails = enableCityDetails;
        return this;
    }

    public FormLocationProperties setEnableButtonMap(boolean enableButtonMap) {
        isEnableButtonMap = enableButtonMap;
        return this;
    }

    public FormLocationProperties setEnableButtonDirection(boolean enableButtonDirection) {
        isEnableButtonDirection = enableButtonDirection;
        return this;
    }

    public FormLocationProperties setEnableTranslucentStatus(boolean enableTranslucentStatus) {
        isEnableTranslucentStatus = enableTranslucentStatus;
        return this;
    }

    public FormLocationProperties setHintAddressLine1(String hintAddressLine1) {
        this.hintAddressLine1 = hintAddressLine1;
        return this;
    }
}
