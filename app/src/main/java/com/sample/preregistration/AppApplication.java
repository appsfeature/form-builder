package com.sample.preregistration;

import android.app.Application;

import com.formbuilder.FormBuilder;
import com.formbuilder.model.FormLocationProperties;

public class AppApplication extends Application {
    private static AppApplication instance;

    public static AppApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FormBuilder.getInstance()
                .setLocationProperty(FormLocationProperties.Builder()
                        .setEnableSearchBar(true)
                        .setEnableAddressLine1(true)
                        .setEnableAddressLine2(true)
                        .setEnableCityDetails(true)
                        .setHintAddressLine1("Enter Detail")
                        .setApiKey(getString(R.string.google_api_key)))
                .setDebugModeEnabled(BuildConfig.DEBUG);
    }
}