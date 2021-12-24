package com.formbuilder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.formbuilder.R;
import com.formbuilder.adapter.holder.CheckBoxViewHolder;
import com.formbuilder.adapter.holder.DatePickerViewHolder;
import com.formbuilder.adapter.holder.EditTextViewHolder;
import com.formbuilder.adapter.holder.EmptyViewHolder;
import com.formbuilder.adapter.holder.RadioViewHolder;
import com.formbuilder.adapter.holder.SpinnerViewHolder;
import com.formbuilder.adapter.holder.TextViewHolder;
import com.formbuilder.interfaces.FieldType;
import com.formbuilder.model.DynamicInputModel;

import java.util.List;

public class DynamicInputAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final List<DynamicInputModel> mList;
    public final Context context;
    public RecyclerView mRecyclerView;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    public DynamicInputAdapter(Context context, List<DynamicInputModel> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == FieldType.TEXT_VIEW) {
            return new TextViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pre_slot_text_view, viewGroup, false));
        } else if (i == FieldType.SPINNER) {
            return new SpinnerViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pre_slot_spinner, viewGroup, false));
        } else if (i == FieldType.RADIO_BUTTON) {
            return new RadioViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pre_slot_radio_button, viewGroup, false));
        } else if (i == FieldType.CHECK_BOX) {
            return new CheckBoxViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pre_slot_check_box, viewGroup, false));
        } else if (i == FieldType.DATE_PICKER) {
            return new DatePickerViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pre_slot_date_picker, viewGroup, false));
        } else if (i == FieldType.EDIT_TEXT) {
            return new EditTextViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pre_slot_edit_text, viewGroup, false));
        } else {
            return new EmptyViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pre_slot_empty_view, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        DynamicInputModel item = mList.get(position);
        try {
            if (viewHolder instanceof EditTextViewHolder) {
                EditTextViewHolder holder = (EditTextViewHolder) viewHolder;
                holder.setData(item);
            } else if (viewHolder instanceof SpinnerViewHolder) {
                SpinnerViewHolder holder = (SpinnerViewHolder) viewHolder;
                holder.setData(item);
            } else if (viewHolder instanceof TextViewHolder) {
                TextViewHolder holder = (TextViewHolder) viewHolder;
                holder.setData(item);
            } else if (viewHolder instanceof RadioViewHolder) {
                RadioViewHolder holder = (RadioViewHolder) viewHolder;
                holder.setData(item);
            } else if (viewHolder instanceof CheckBoxViewHolder) {
                CheckBoxViewHolder holder = (CheckBoxViewHolder) viewHolder;
                holder.setData(item);
            } else if (viewHolder instanceof DatePickerViewHolder) {
                DatePickerViewHolder holder = (DatePickerViewHolder) viewHolder;
                holder.setData(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList == null ? 0 : mList.get(position).getFieldType();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}