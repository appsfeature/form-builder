package com.formbuilder.adapter.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.formbuilder.R;
import com.formbuilder.adapter.DynamicInputAdapter;
import com.formbuilder.model.DynamicInputModel;
import com.formbuilder.util.GsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Resource layout
 * R.layout.pre_slot_check_box
 */
public class CheckBoxViewHolder extends RecyclerView.ViewHolder {
    private final DynamicInputAdapter mAdapter;
    private final CheckBox cbCheckBox;

    public CheckBoxViewHolder(DynamicInputAdapter mAdapter, View view) {
        super(view);
        this.mAdapter = mAdapter;
        cbCheckBox = view.findViewById(R.id.cb_check_box);
    }

    public void setData(DynamicInputModel item) throws Exception {
        cbCheckBox.setText(item.getFieldName());
        cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setInputData(isChecked ? "1" : "0");
            }
        });
    }
}
