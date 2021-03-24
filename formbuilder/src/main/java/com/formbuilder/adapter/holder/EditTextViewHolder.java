package com.formbuilder.adapter.holder;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.formbuilder.R;
import com.formbuilder.adapter.DynamicInputAdapter;
import com.formbuilder.adapter.EmailSuggestionAdapter;
import com.formbuilder.interfaces.FieldInputType;
import com.formbuilder.model.DynamicInputModel;
import com.formbuilder.util.GsonParser;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Resource layout
 * R.layout.pre_slot_edit_text
 */
public class EditTextViewHolder extends RecyclerView.ViewHolder {
    public final DynamicInputAdapter mAdapter;
    public final AutoCompleteTextView etInputText;
    public final TextInputLayout etInputLayout;

    public EditTextViewHolder(DynamicInputAdapter mAdapter, View view) {
        super(view);
        this.mAdapter = mAdapter;
        etInputLayout = view.findViewById(R.id.et_input_layout);
        etInputText = view.findViewById(R.id.et_input_text);
        etInputText.setThreshold(1);
        etInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        if (mAdapter.mRecyclerView != null && mAdapter.mRecyclerView.getLayoutManager() != null && getAdapterPosition() >= 0) {
                            View child = mAdapter.mRecyclerView.getLayoutManager().getChildAt(getAdapterPosition());
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
        if (TextUtils.isEmpty(inputType) && TextUtils.isEmpty(fieldName)) {
            return InputType.TYPE_CLASS_TEXT;
        }
        if (!TextUtils.isEmpty(inputType)) {
            switch (inputType) {
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
        } else {
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

    public void setData(DynamicInputModel item) throws Exception{
        etInputLayout.setHint(item.getFieldName());
        etInputText.setText(item.getInputData());
        if (item.getMaxLength() > 0) {
            etInputText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(item.getMaxLength())});
        }
        etInputText.setInputType(getInputType(item.getInputType(), item.getFieldName()));
        etInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setInputData(s.toString());
            }
        });
        if (item.getFieldSuggestions() != null) {
            if (item.getInputType() != null && item.getInputType().contains(FieldInputType.textEmailAddress)) {
                List<String> suggestions = GsonParser.fromJson(item.getFieldSuggestions(), new TypeToken<List<String>>() {
                });
                if (suggestions != null) {
                    EmailSuggestionAdapter adapter = new EmailSuggestionAdapter(mAdapter.context, android.R.layout.simple_list_item_1, suggestions);
                    etInputText.setAdapter(adapter);
                }
            } else {
                String[] suggestions = GsonParser.fromJson(item.getFieldSuggestions(), new TypeToken<String[]>() {
                });
                if (suggestions != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(mAdapter.context, android.R.layout.simple_list_item_1, suggestions);
                    etInputText.setAdapter(adapter);
                }
            }
        }
    }
}
