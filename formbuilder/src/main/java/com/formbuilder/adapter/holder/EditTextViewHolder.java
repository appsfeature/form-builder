package com.formbuilder.adapter.holder;

import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.formbuilder.R;
import com.formbuilder.adapter.DynamicInputAdapter;
import com.formbuilder.interfaces.FieldInputType;
import com.google.android.material.textfield.TextInputLayout;

public class EditTextViewHolder extends RecyclerView.ViewHolder {
    public final DynamicInputAdapter dynamicInputAdapter;
    public final AutoCompleteTextView etInputText;
    public final TextInputLayout etInputLayout;

    public EditTextViewHolder(DynamicInputAdapter dynamicInputAdapter, View view) {
        super(view);
        this.dynamicInputAdapter = dynamicInputAdapter;
        etInputLayout = view.findViewById(R.id.et_input_layout);
        etInputText = view.findViewById(R.id.et_input_text);
        etInputText.setThreshold(1);
        etInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        if(dynamicInputAdapter.mRecyclerView != null && dynamicInputAdapter.mRecyclerView.getLayoutManager() != null && getAdapterPosition() >= 0) {
                            View child = dynamicInputAdapter.mRecyclerView.getLayoutManager().getChildAt(getAdapterPosition());
                            if (child != null) {
                                child.setFocusable(true);
                                child.setFocusableInTouchMode(true);
                                child.requestFocus();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public int getInputType(@FieldInputType String inputType, String fieldName) {
        if(TextUtils.isEmpty(inputType) && TextUtils.isEmpty(fieldName)) {
            return InputType.TYPE_CLASS_TEXT;
        }
        if(!TextUtils.isEmpty(inputType)){
            switch (inputType){
                case FieldInputType.textPersonName:
                    return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME;
                case FieldInputType.textEmailAddress:
                    return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
                case FieldInputType.numberSigned:
                case FieldInputType.number:
                case FieldInputType.phone:
                    return InputType.TYPE_CLASS_NUMBER;
                case FieldInputType.textMultiLine:
                    return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE;
                case FieldInputType.textCapWords:
                    return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS;
                case FieldInputType.textCapCharacters:
                    return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS;
                case FieldInputType.text:
                default:
                    return InputType.TYPE_CLASS_TEXT;

            }
        }else {
            if (fieldName.toLowerCase().contains("email")) {
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            } else if (fieldName.toLowerCase().contains("mobile") || fieldName.contains("phone") || fieldName.contains("roll no")) {
                return InputType.TYPE_CLASS_NUMBER;
            } else if (fieldName.toLowerCase().contains("name")) {
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME;
            } else if (fieldName.toLowerCase().contains("address")) {
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE;
            } else {
                return InputType.TYPE_CLASS_TEXT;
            }
        }
    }
}
