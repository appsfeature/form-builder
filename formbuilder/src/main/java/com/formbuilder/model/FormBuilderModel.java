package com.formbuilder.model;

import androidx.annotation.NonNull;

import com.formbuilder.interfaces.RequestType;
import com.formbuilder.interfaces.SubmissionType;
import com.formbuilder.model.entity.PopupEntity;
import com.formbuilder.util.FBConstant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FormBuilderModel implements Serializable, Cloneable {

    @Expose
    @SerializedName(value="formId", alternate={"id"})
    private int formId;

    @Expose
    @SerializedName(value="title")
    private String title;

    @Expose
    @SerializedName(value="subTitle")
    private String subTitle;

    @Expose
    @SerializedName(value="baseUrl")
    private String baseUrl;

    @Expose
    @SerializedName(value="requestApi")
    private String requestApi;

    @Expose
    @SerializedName(value="requestType")
    private int requestType;

    @Expose
    @SerializedName(value="submissionType")
    @SubmissionType
    private int submissionType;

    @Expose
    @SerializedName(value="buttonText")
    private String buttonText = FBConstant.DEFAULT_BUTTON_TEXT;

    @Expose
    @SerializedName(value="popup")
    private PopupEntity popup;

    @Expose
    @SerializedName(value="fieldList")
    private List<DynamicInputModel> inputList;

    @Expose
    @SerializedName(value="isShowActionbar")
    private boolean isShowActionbar;

    private Map<String, String> extraParams;

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<DynamicInputModel> getInputList() {
        return inputList;
    }

    public void setInputList(List<DynamicInputModel> inputList) {
        this.inputList = inputList;
    }

    public String getRequestApi() {
        return requestApi;
    }

    public void setRequestApi(String requestApi) {
        this.requestApi = requestApi;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @RequestType
    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(@RequestType int requestType) {
        this.requestType = requestType;
    }
    @SubmissionType
    public int getSubmissionType() {
        return submissionType;
    }

    public void setSubmissionType(int submissionType) {
        this.submissionType = submissionType;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public PopupEntity getPopup() {
        return popup;
    }

    public void setPopup(PopupEntity popup) {
        this.popup = popup;
    }

    public Map<String, String> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Map<String, String> extraParams) {
        this.extraParams = extraParams;
    }

    public boolean isShowActionbar() {
        return isShowActionbar;
    }

    public void setShowActionbar(boolean showActionbar) {
        isShowActionbar = showActionbar;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public FormBuilderModel getClone() {
        try {
            return (FormBuilderModel) clone();
        } catch (CloneNotSupportedException e) {
            return new FormBuilderModel();
        }
    }
}

