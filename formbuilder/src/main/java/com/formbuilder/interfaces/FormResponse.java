package com.formbuilder.interfaces;

import android.view.View;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public interface FormResponse {

    @IntDef({VISIBLE, INVISIBLE, GONE})
    @Retention(RetentionPolicy.SOURCE)
    @interface Visibility {
    }

    interface Callback<T> {
        void onSuccess(T response);

        default void onFailure(Exception e){}
    }

    interface OnClickListener<T> {
        void onItemClicked(View view, T item);
    }

    interface FormSubmitListener {
        void onFormSubmitted(String data);
    }

    interface Progress {
        void onStartProgressBar();
        void onStopProgressBar();
    }
}