package com.formbuilder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.formbuilder.activity.FormBuilderActivity;
import com.formbuilder.fragment.FormBuilderFragment;
import com.formbuilder.interfaces.FormResponse;
import com.formbuilder.model.FormBuilderModel;
import com.formbuilder.model.FormLocationProperties;
import com.formbuilder.util.FBConstant;
import com.formbuilder.util.FBPreferences;
import com.formbuilder.util.FBUtility;
import com.formbuilder.util.GsonParser;
import com.location.picker.LocationPicker;
import com.location.picker.interfaces.LocationProperties;

public class FormBuilder {

    public static final int LIBRARY_VERSION = 1;

    private static volatile FormBuilder sSoleInstance;
    private boolean isDebugModeEnabled = false;
    public String appVersion;
    public boolean isEnableJsonEncode = true;
    private FormResponse.SyncSignupForm syncSignupFormListener;
    private LocationProperties mLocationProperties;

    private FormBuilder() {

    }

    public static FormBuilder getInstance() {
        if (sSoleInstance == null) {
            synchronized (FormBuilder.class) {
                if (sSoleInstance == null) sSoleInstance = new FormBuilder();
            }
        }
        return sSoleInstance;
    }

    public boolean isDebugModeEnabled() {
        return isDebugModeEnabled;
    }

    public FormBuilder setDebugModeEnabled(boolean debugModeEnabled) {
        isDebugModeEnabled = debugModeEnabled;
        return this;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public FormBuilder setAppVersion(String appVersion) {
        this.appVersion = appVersion;
        return this;
    }

    public boolean isFormSubmitted(Context context, int formId) {
        return FBPreferences.isFormSubmitted(context, formId);
    }

    public void openDynamicFormActivity(Context context, int formId, String json, FormResponse.FormSubmitListener formSubmitListener) {
        FormBuilderModel property = GsonParser.getGson().fromJson(json, FormBuilderModel.class);
        if (property != null) {
            openDynamicFormActivity(context, formId, property, formSubmitListener);
        } else {
            FBUtility.showToastCentre(context, FBConstant.Error.INVALID_FORM_DATA);
        }
    }


    public void openDynamicFormActivity(Context context, int formId, FormBuilderModel property, FormResponse.FormSubmitListener formSubmitListener) {
        setFormSubmitListener(formSubmitListener);
        if (!isFormSubmitted(context, formId)) {
            context.startActivity(new Intent(context, FormBuilderActivity.class)
                    .putExtra(FBConstant.CATEGORY_PROPERTY, property));
        } else {
            FBUtility.showToastCentre(context, context.getString(R.string.error_form_already_submitted));
        }
    }

    public Fragment getFragment(FormBuilderModel property, FormResponse.FormSubmitListener formSubmitListener) {
        setFormSubmitListener(formSubmitListener);
        Fragment fragment = new FormBuilderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FBConstant.CATEGORY_PROPERTY, property);
        fragment.setArguments(bundle);
        return fragment;
    }

    private FormResponse.FormSubmitListener mFormSubmitListener;

    public void setFormSubmitListener(FormResponse.FormSubmitListener mFormSubmitListener) {
        this.mFormSubmitListener = null;
        this.mFormSubmitListener = mFormSubmitListener;
    }

    public void dispatchOnFormSubmit(String data) {
        if(mFormSubmitListener != null){
            mFormSubmitListener.onFormSubmitted(data);
        }
    }

    public void syncSignupForm(){
        if(syncSignupFormListener != null){
            syncSignupFormListener.onSyncSignupForm();
        }
    }

    public void setSyncSignupFormListener(FormResponse.SyncSignupForm syncSignupFormListener) {
        this.syncSignupFormListener = null;
        this.syncSignupFormListener = syncSignupFormListener;
    }

    public FormBuilder setEnableJsonEncode(boolean isEnableJsonEncode) {
        this.isEnableJsonEncode = isEnableJsonEncode;
        return this;
    }

    public boolean isEnableJsonEncode() {
        return isEnableJsonEncode;
    }

    public FormBuilder setLocationProperty(FormLocationProperties properties) {
        this.mLocationProperties = FBUtility.getLocationProperty(properties);
        LocationPicker.getInstance()
                .setProperty(mLocationProperties);
        return this;
    }
}
