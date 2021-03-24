package com.formbuilder.model;

import androidx.annotation.NonNull;

import com.formbuilder.model.entity.PopupEntity;
import com.formbuilder.util.FBConstant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

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
    @SerializedName(value="requestApi")
    private String requestApi;

    @Expose
    @SerializedName(value="methodType")
    private String methodType;

    @Expose
    @SerializedName(value="buttonText")
    private String buttonText = FBConstant.DEFAULT_BUTTON_TEXT;

    @Expose
    @SerializedName(value="popup")
    private PopupEntity popup;

    @Expose
    @SerializedName(value="fieldList")
    private List<DynamicInputModel> inputList;

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

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
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

