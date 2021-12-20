package com.formbuilder.model;

import com.formbuilder.interfaces.FieldInputType;
import com.formbuilder.interfaces.FieldType;
import com.formbuilder.interfaces.ValidationCheck;
import com.formbuilder.util.GsonParser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;

public class DynamicInputModel implements Serializable {

    @Expose
    @SerializedName(value="fieldName")
    private String fieldName;
    @Expose
    @SerializedName(value="inputType")
    private String inputType = FieldInputType.text;
    @Expose
    @SerializedName(value="fieldType")
    private int fieldType = FieldType.EDIT_TEXT;
    @Expose
    @SerializedName(value="validation")
    private String validation = ValidationCheck.NOT_REQUIRED;
    @Expose
    @SerializedName(value="fieldData")
    private String fieldData;
    @Expose
    @SerializedName(value="fieldSuggestions")
    private String fieldSuggestions;
    @Expose
    @SerializedName(value="inputData")
    private String inputData;
    @Expose
    @SerializedName(value="maxLength")
    private int maxLength = 0;
    @Expose
    @SerializedName(value="paramKey")
    private String paramKey;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(@FieldInputType String inputType) {
        this.inputType = inputType;
    }

    public int getFieldType() {
        return fieldType;
    }

    public void setFieldType(@FieldType int fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldData() {
        return fieldData;
    }

    public void setFieldData(String fieldData) {
        this.fieldData = fieldData;
    }

    public String getFieldSuggestions() {
        return fieldSuggestions;
    }

    public void setFieldSuggestions(String fieldSuggestions) {
        this.fieldSuggestions = fieldSuggestions;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(@ValidationCheck String validation) {
        this.validation = validation;
    }

    public void setValidationRegex(String validationRegex) {
        this.validation = validationRegex;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String toJson(boolean excludeFieldsWithoutExposeAnnotation) {
        if(excludeFieldsWithoutExposeAnnotation){
            return GsonParser.toJson(this, new TypeToken<DynamicInputModel>() {});
        }else {
            return GsonParser.getGson().toJson(this, DynamicInputModel.class);
        }
    }
}
