package com.formbuilder;

import android.content.Context;
import android.content.Intent;

import com.formbuilder.activity.FormBuilderActivity;
import com.formbuilder.interfaces.FormResponse;
import com.formbuilder.model.FormBuilderModel;
import com.formbuilder.util.FBConstant;
import com.formbuilder.util.FBPreferences;
import com.formbuilder.util.FBUtility;
import com.formbuilder.util.GsonParser;

import java.util.ArrayList;

public class FormBuilder {

    public static final int LIBRARY_VERSION = 1;

    private static volatile FormBuilder sSoleInstance;
    public boolean isDebugModeEnabled = false;
    public String appVersion;

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

    public boolean isRegistrationCompleted(Context context, int formId) {
        return FBPreferences.isRegistrationCompleted(context, formId);
    }

    public void openRegistrationActivity(Context context, int formId, String json) {
        FormBuilderModel property = GsonParser.getGson().fromJson(json, FormBuilderModel.class);
        if (property != null) {
            openRegistrationActivity(context, formId, property);
        } else {
            FBUtility.showToastCentre(context, FBConstant.Error.INVALID_FORM_DATA);
        }
    }


    public void openRegistrationActivity(Context context, int formId, FormBuilderModel property) {
        if (!isRegistrationCompleted(context, formId)) {
            context.startActivity(new Intent(context, FormBuilderActivity.class)
                    .putExtra(FBConstant.CATEGORY_PROPERTY, property));
        } else {
            FBUtility.showToastCentre(context, context.getString(R.string.error_form_already_submitted));
        }


    }

    private final ArrayList<FormResponse.FormSubmitListener> mFormSubmitListener = new ArrayList<>();

    public FormBuilder addFormSubmitListener(FormResponse.FormSubmitListener callback) {
        synchronized (mFormSubmitListener) {
            mFormSubmitListener.add(callback);
        }
        return this;
    }

    public void removeFormSubmitListeners(FormResponse.FormSubmitListener callback) {
        synchronized (mFormSubmitListener) {
            mFormSubmitListener.remove(callback);
        }
    }

    public void dispatchOnFormSubmit(String data) {
        for (FormResponse.FormSubmitListener callback : mFormSubmitListener) {
            callback.onFormSubmitted(data);
        }
    }
}
