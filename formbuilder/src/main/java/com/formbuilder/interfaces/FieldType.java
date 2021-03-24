package com.formbuilder.interfaces;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({FieldType.TEXT_VIEW, FieldType.EDIT_TEXT, FieldType.SPINNER})
@Retention(RetentionPolicy.SOURCE)
public @interface FieldType {
    int TEXT_VIEW = 0;
    int EDIT_TEXT = 1;
    int SPINNER = 2;
}
