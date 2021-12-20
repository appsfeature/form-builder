package com.formbuilder.interfaces;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        RequestType.GET,
        RequestType.POST,
        RequestType.POST_FORM,
})
@Retention(RetentionPolicy.SOURCE)
public @interface RequestType {
    int GET = 0;
    int POST = 1;
    int POST_FORM = 3;
}
