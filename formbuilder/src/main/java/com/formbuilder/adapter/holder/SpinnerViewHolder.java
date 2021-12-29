package com.formbuilder.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.formbuilder.R;
import com.formbuilder.activity.FormBuilderActivity;
import com.formbuilder.adapter.DynamicInputAdapter;
import com.formbuilder.adapter.SpinnerAdapter;
import com.formbuilder.model.DynamicInputModel;
import com.formbuilder.model.entity.MasterEntity;
import com.formbuilder.util.FBUtility;
import com.formbuilder.util.GsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Resource layout
 * R.layout.pre_slot_spinner
 */
public class SpinnerViewHolder extends RecyclerView.ViewHolder {
    public final Spinner spinnerInput;
    private final DynamicInputAdapter mAdapter;

    public SpinnerViewHolder(DynamicInputAdapter mAdapter, View view) {
        super(view);
        this.mAdapter = mAdapter;
        spinnerInput = view.findViewById(R.id.spinner_input);
        (view.findViewById(R.id.spinner_input_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerInput.performClick();
            }
        });
    }

    public List<MasterEntity> getSpinnerList(String fieldName, String fieldData) {
        List<MasterEntity> fieldList = null;
        if(fieldData != null) {
            fieldList = GsonParser.fromJson(fieldData, new TypeToken<List<MasterEntity>>() {
            });
            if (fieldList != null) {
                fieldList.add(0, new MasterEntity(0, fieldName));
            }
        }
        if(fieldList == null){
            fieldList = new ArrayList<>();
            fieldList.add(new MasterEntity(0, "No Data"));
        }
        return fieldList;
    }

    public static void showValidationError(Context context, String spinnerTitle) {
        FBUtility.showToastCentre(context, "Please Select " + spinnerTitle);
    }

    public void setData(DynamicInputModel item) throws Exception{
        SpinnerAdapter adapter = new SpinnerAdapter(mAdapter.context, getSpinnerList(item.getFieldName(), item.getFieldData()));
        spinnerInput.setAdapter(adapter);
        spinnerInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MasterEntity mSelectedItem = (MasterEntity) parent.getItemAtPosition(position);
                if (mSelectedItem != null) {
                    item.setInputData(item.isSpinnerSelectTitle() ? mSelectedItem.getTitle() : mSelectedItem.getId() + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
