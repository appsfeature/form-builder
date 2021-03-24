package com.formbuilder.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.formbuilder.R;
import com.formbuilder.activity.FormBuilderActivity;
import com.formbuilder.adapter.DynamicInputAdapter;
import com.formbuilder.adapter.SpinnerAdapter;
import com.formbuilder.model.DynamicInputModel;
import com.formbuilder.model.entity.MasterEntity;
import com.formbuilder.util.DatePickerDialog;
import com.formbuilder.util.FBUtility;
import com.formbuilder.util.GsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Resource layout
 * R.layout.pre_slot_date_picker
 */
public class DatePickerViewHolder extends RecyclerView.ViewHolder {
    private final DynamicInputAdapter mAdapter;
    private final TextView tvDatePicker;
    private final View datePicker;
    private int sDay, sMonth, sYear;

    public DatePickerViewHolder(DynamicInputAdapter mAdapter, View view) {
        super(view);
        this.mAdapter = mAdapter;
        tvDatePicker = view.findViewById(R.id.tv_date_picker);
        datePicker = view.findViewById(R.id.date_input_layout);
        int[] date = DatePickerDialog.initDate(null);
        sDay=date[0];
        sMonth=date[1];
        sYear=date[2];
    }

    public void setData(DynamicInputModel item) throws Exception{
        tvDatePicker.setText(item.getFieldName());
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(mAdapter.context, new DatePickerDialog.DateSelectListener() {
                    @Override
                    public void onSelectDateClick(int day, int month, int year, String ddMMMyy) {
                        sDay = day;
                        sMonth = month;
                        sYear = year;
                        tvDatePicker.setText(ddMMMyy);
                        item.setInputData(DatePickerDialog.getFormattedDate(sDay,sMonth,sYear));
                    }
                }, sDay, sMonth, sYear).show();
            }
        });
    }
}
