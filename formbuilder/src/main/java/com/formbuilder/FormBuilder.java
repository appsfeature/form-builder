package com.formbuilder;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.formbuilder.base.FormBuilderClass;
import com.formbuilder.interfaces.FormResponse;
import com.formbuilder.model.FormBuilderModel;
import com.formbuilder.model.FormLocationProperties;

public interface FormBuilder {

    int LIBRARY_VERSION = 1;
    boolean isDebugModeEnabled();

    static FormBuilder getInstance() {
        return FormBuilderClass.Builder();
    }

    FormBuilder setDebugModeEnabled(boolean debugModeEnabled);

    String getAppVersion();
    FormBuilder setAppVersion(String appVersion);

    boolean isFormSubmitted(Context context, int formId);

    void openDynamicFormActivity(Context context, int formId, String json, FormResponse.FormSubmitListener formSubmitListener);

    void openDynamicFormActivity(Context context, int formId, FormBuilderModel property, FormResponse.FormSubmitListener formSubmitListener);

    Fragment getFragment(FormBuilderModel property, FormResponse.FormSubmitListener formSubmitListener);

    void setFormSubmitListener(FormResponse.FormSubmitListener mFormSubmitListener);

    void dispatchOnFormSubmit(String data);

    void syncSignupForm();

    void setSyncSignupFormListener(FormResponse.SyncSignupForm syncSignupFormListener);

    FormBuilder setEnableJsonEncode(boolean isEnableJsonEncode);

    boolean isEnableJsonEncode();
    FormBuilder setLocationProperty(FormLocationProperties properties);
}
