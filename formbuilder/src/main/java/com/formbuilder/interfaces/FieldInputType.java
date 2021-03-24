package com.formbuilder.interfaces;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        FieldInputType.text,
        FieldInputType.number,
        FieldInputType.textPersonName,
        FieldInputType.textEmailAddress,
        FieldInputType.numberSigned,
        FieldInputType.phone,
        FieldInputType.textMultiLine,
        FieldInputType.textCapWords,
        FieldInputType.textCapCharacters
})
@Retention(RetentionPolicy.SOURCE)
public @interface FieldInputType {
    String text = "text";
    String number = "number";
    String textPersonName = "textPersonName";
    String textEmailAddress = "textEmailAddress";
    String numberSigned = "numberSigned";
    String phone = "phone";
    String textMultiLine = "textMultiLine";
    /**
     * For example : Hello World
     */
    String textCapWords = "textCapWords";
    /**
     * For example : HELLO WORLD
     */
    String textCapCharacters = "textCapCharacters";
}
