package com.formbuilder.interfaces;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface FormResponse {

    @IntDef({VISIBLE, INVISIBLE, GONE})
    @Retention(RetentionPolicy.SOURCE)
    @interface Visibility {
    }

    interface Callback<T> {
        void onSuccess(T response);

        default void onFailure(Exception e){}
    }

    interface FormSubmitListener {
        void onFormSubmitted(String data);
    }

    interface SyncSignupForm {
        void onSyncSignupForm();
    }
}