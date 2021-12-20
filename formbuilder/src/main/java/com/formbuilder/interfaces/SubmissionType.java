package com.formbuilder.interfaces;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        SubmissionType.BULK_JSON,
        SubmissionType.KEY_VALUE_PAIR
})
@Retention(RetentionPolicy.SOURCE)
public @interface SubmissionType {
    int BULK_JSON = 0;
    int KEY_VALUE_PAIR = 1;
}
