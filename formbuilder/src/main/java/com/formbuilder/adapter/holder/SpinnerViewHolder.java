package com.formbuilder.adapter.holder;

import android.view.View;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.formbuilder.R;
import com.formbuilder.adapter.DynamicInputAdapter;
import com.formbuilder.model.entity.MasterEntity;
import com.formbuilder.util.GsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class SpinnerViewHolder extends RecyclerView.ViewHolder {
    public final Spinner spinnerInput;
    private final DynamicInputAdapter dynamicInputAdapter;

    public SpinnerViewHolder(DynamicInputAdapter dynamicInputAdapter, View view) {
        super(view);
        this.dynamicInputAdapter = dynamicInputAdapter;
        spinnerInput = view.findViewById(R.id.spinner_input);
        (view.findViewById(R.id.spinner_input_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerInput.performClick();
            }
        });
    }

    public List<MasterEntity> getSpinnerList(String fieldName, String fieldData) {
        List<MasterEntity> spinnerList = null;
        if(fieldData != null) {
            spinnerList = GsonParser.fromJson(fieldData, new TypeToken<List<MasterEntity>>() {
            });
            if (spinnerList != null) {
                spinnerList.add(0, new MasterEntity(0, fieldName));
            }
        }
        if(spinnerList == null){
            spinnerList = new ArrayList<>();
            spinnerList.add(new MasterEntity(0, "No Data"));
        }
        return spinnerList;
    }
}
