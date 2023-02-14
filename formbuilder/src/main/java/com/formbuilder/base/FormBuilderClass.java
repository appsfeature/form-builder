package com.formbuilder.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.formbuilder.FormBuilder;
import com.formbuilder.R;
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

public class FormBuilderClass implements FormBuilder {

    private static volatile FormBuilderClass sSoleInstance;
    private boolean isDebugModeEnabled = false;
    public String appVersion;
    public boolean isEnableJsonEncode = true;
    private FormResponse.SyncSignupForm syncSignupFormListener;
    private LocationProperties mLocationProperties;

    private FormBuilderClass() {

    }

    public static FormBuilderClass Builder() {
        if (sSoleInstance == null) {
            synchronized (FormBuilderClass.class) {
                if (sSoleInstance == null) sSoleInstance = new FormBuilderClass();
            }
        }
        return sSoleInstance;
    }

    @Override
    public boolean isDebugModeEnabled() {
        return isDebugModeEnabled;
    }

    @Override
    public FormBuilderClass setDebugModeEnabled(boolean debugModeEnabled) {
        isDebugModeEnabled = debugModeEnabled;
        return this;
    }

    @Override
    public String getAppVersion() {
        return appVersion;
    }

    @Override
    public FormBuilderClass setAppVersion(String appVersion) {
        this.appVersion = appVersion;
        return this;
    }

    @Override
    public boolean isFormSubmitted(Context context, int formId) {
        return FBPreferences.isFormSubmitted(context, formId);
    }

    @Override
    public void openDynamicFormActivity(Context context, int formId, String json, FormResponse.FormSubmitListener formSubmitListener) {
        FormBuilderModel property = GsonParser.getGson().fromJson(json, FormBuilderModel.class);
        if (property != null) {
            openDynamicFormActivity(context, formId, property, formSubmitListener);
        } else {
            FBUtility.showToastCentre(context, FBConstant.Error.INVALID_FORM_DATA);
        }
    }

    @Override
    public void openDynamicFormActivity(Context context, int formId, FormBuilderModel property, FormResponse.FormSubmitListener formSubmitListener) {
        setFormSubmitListener(formSubmitListener);
        if (!isFormSubmitted(context, formId)) {
            context.startActivity(new Intent(context, FormBuilderActivity.class)
                    .putExtra(FBConstant.CATEGORY_PROPERTY, property));
        } else {
            FBUtility.showToastCentre(context, context.getString(R.string.error_form_already_submitted));
        }
    }

    @Override
    public Fragment getFragment(FormBuilderModel property, FormResponse.FormSubmitListener formSubmitListener) {
        setFormSubmitListener(formSubmitListener);
        Fragment fragment = new FormBuilderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FBConstant.CATEGORY_PROPERTY, property);
        fragment.setArguments(bundle);
        return fragment;
    }

    private FormResponse.FormSubmitListener mFormSubmitListener;

    @Override
    public void setFormSubmitListener(FormResponse.FormSubmitListener mFormSubmitListener) {
        this.mFormSubmitListener = null;
        this.mFormSubmitListener = mFormSubmitListener;
    }

    @Override
    public void dispatchOnFormSubmit(String data) {
        if(mFormSubmitListener != null){
            mFormSubmitListener.onFormSubmitted(data);
        }
    }

    @Override
    public void syncSignupForm(){
        if(syncSignupFormListener != null){
            syncSignupFormListener.onSyncSignupForm();
        }
    }

    @Override
    public void setSyncSignupFormListener(FormResponse.SyncSignupForm syncSignupFormListener) {
        this.syncSignupFormListener = null;
        this.syncSignupFormListener = syncSignupFormListener;
    }

    @Override
    public FormBuilderClass setEnableJsonEncode(boolean isEnableJsonEncode) {
        this.isEnableJsonEncode = isEnableJsonEncode;
        return this;
    }

    @Override
    public boolean isEnableJsonEncode() {
        return isEnableJsonEncode;
    }

    @Override
    public FormBuilderClass setLocationProperty(FormLocationProperties properties) {
        this.mLocationProperties = FBUtility.getLocationProperty(properties);
        LocationPicker.getInstance()
                .setProperty(mLocationProperties);
        return this;
    }
}
