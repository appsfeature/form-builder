package com.formbuilder.adapter.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.formbuilder.R;
import com.formbuilder.adapter.DynamicInputAdapter;
import com.formbuilder.interfaces.FieldInputType;
import com.formbuilder.model.DynamicInputModel;
import com.formbuilder.util.DatePickerDialog;
import com.formbuilder.util.GsonParser;
import com.location.picker.LocationPicker;
import com.location.picker.interfaces.LocationPickerCallback;
import com.location.picker.model.LocationPickerDetail;
import com.location.picker.util.CountryCode;

import java.util.Objects;

/**
 * Resource layout
 * R.layout.pre_slot_location
 */
public class LocationViewHolder extends RecyclerView.ViewHolder {
    private final DynamicInputAdapter mAdapter;
    private final TextView tvTitle;
    private final View mainView;
    private final TextView tvInputHint;

    public LocationViewHolder(DynamicInputAdapter mAdapter, View view) {
        super(view);
        this.mAdapter = mAdapter;
        tvTitle = view.findViewById(R.id.tv_title);
        tvInputHint = view.findViewById(R.id.tv_input_hint);
        mainView = view.findViewById(R.id.ll_main_view);
    }

    public void setData(DynamicInputModel item) throws Exception{
        mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationPicker.getInstance()
                        .setLocationCallback(new LocationPickerCallback() {
                            @Override
                            public void onLocationSelected(LocationPickerDetail detail) {
                                tvTitle.setText(detail.getAddressLine1());
                                if(Objects.equals(item.getInputType(), FieldInputType.locationLatLng)){
                                    item.setInputData(detail.getLatLong());
                                }else {
                                    item.setInputData(toJson(detail));
                                }
                            }

                            @Override
                            public void onCanceled(Exception e) {

                            }
                        })
                        .open(mAdapter.context, CountryCode.India);
            }
        });
        if (tvInputHint != null) {
            tvInputHint.setText(item.getFieldName());
        }
    }

    public String toJson(LocationPickerDetail detail) {
        return GsonParser.getGson().toJson(detail, LocationPickerDetail.class);
    }

}
