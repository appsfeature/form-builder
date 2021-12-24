package com.formbuilder.fragment;

import android.view.View;

import com.formbuilder.R;

public class FormBuilderFragment extends BaseFormBuilderFragment {

    @Override
    public int getLayoutContentView() {
        return R.layout.pre_registration_list;
    }

    @Override
    public void onInitViews(View view) {

    }

    @Override
    public void onLoadData() {

    }

    @Override
    public boolean onValidationCheck() {
        return true;
    }
}
