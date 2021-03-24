package com.formbuilder.adapter.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.formbuilder.R;
import com.formbuilder.adapter.DynamicInputAdapter;
import com.formbuilder.model.DynamicInputModel;

/**
 * Resource layout
 * R.layout.pre_slot_text_view
 */
public class TextViewHolder extends RecyclerView.ViewHolder {
    private final DynamicInputAdapter mAdapter;
    public final TextView textView;

    public TextViewHolder(DynamicInputAdapter mAdapter, View view) {
        super(view);
        this.mAdapter = mAdapter;
        textView = view.findViewById(R.id.tv_slot_text_view);
    }

    public void setData(DynamicInputModel item) throws Exception {
        textView.setText(item.getFieldName());
    }
}
