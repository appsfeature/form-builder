package com.sample.preregistration;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.formbuilder.fragment.BaseFormBuilderFragment;
import com.formbuilder.util.FieldValidation;

public class AppFormBuilderFragment extends BaseFormBuilderFragment {

    private static final String FIELD_ADDRESS = "address";
    private EditText etAddress;
    private Spinner spState;

    @Override
    public int getLayoutContentView() {
        return R.layout.pre_registration_list;
    }


    @Override
    public void onInitViews(View view) {
        etAddress = view.findViewById(R.id.et_input_text);
        spState = view.findViewById(R.id.spinner_input);

        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateInputFieldList(FIELD_ADDRESS, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onLoadData() {
        etAddress.setText(getInputField(FIELD_ADDRESS).getFieldSuggestions());
    }

    @Override
    public boolean onValidationCheck() {
        boolean isValidAllFields = true;
        if(!TextUtils.isEmpty(getInputField(FIELD_ADDRESS).getValidation())) {
            isValidAllFields = FieldValidation.check(activity, etAddress, getInputField(FIELD_ADDRESS).getValidation());
        }
        return isValidAllFields;
    }
}
