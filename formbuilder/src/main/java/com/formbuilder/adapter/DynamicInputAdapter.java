package com.formbuilder.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.formbuilder.R;
import com.formbuilder.adapter.holder.EditTextViewHolder;
import com.formbuilder.adapter.holder.SpinnerViewHolder;
import com.formbuilder.interfaces.FieldInputType;
import com.formbuilder.interfaces.FieldType;
import com.formbuilder.model.DynamicInputModel;
import com.formbuilder.model.entity.MasterEntity;
import com.formbuilder.util.GsonParser;
import com.google.gson.reflect.TypeToken;

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
        if (i == FieldType.EDIT_TEXT) {
            return new EditTextViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pre_slot_edit_text, viewGroup, false));
        } else {
            return new SpinnerViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pre_slot_spinner, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        DynamicInputModel item = mList.get(position);
        if (viewHolder instanceof EditTextViewHolder) {
            EditTextViewHolder holder = (EditTextViewHolder) viewHolder;
            holder.etInputLayout.setHint(item.getFieldName());
            holder.etInputText.setText(item.getInputData());
            holder.etInputText.setInputType(holder.getInputType(item.getInputType(), item.getFieldName()));
            holder.etInputText.addTextChangedListener(new TextWatcher() {
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
            if(item.getFieldSuggestions() != null) {
                if(item.getInputType() != null && item.getInputType().contains(FieldInputType.textEmailAddress)){
                    List<String> suggestions = GsonParser.fromJson(item.getFieldSuggestions(), new TypeToken<List<String>>() {
                    });
                    if(suggestions != null) {
                        EmailSuggestionAdapter adapter = new EmailSuggestionAdapter(context, android.R.layout.simple_list_item_1, suggestions);
                        holder.etInputText.setAdapter(adapter);
                    }
                }else {
                    String[] suggestions = GsonParser.fromJson(item.getFieldSuggestions(), new TypeToken<String[]>() {
                    });
                    if(suggestions != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, suggestions);
                        holder.etInputText.setAdapter(adapter);
                    }
                }
            }
        }else if (viewHolder instanceof SpinnerViewHolder) {
            SpinnerViewHolder holder = (SpinnerViewHolder) viewHolder;
            SpinnerAdapter adapter = new SpinnerAdapter(context, holder.getSpinnerList(item.getFieldName(), item.getFieldData()));
            holder.spinnerInput.setAdapter(adapter);
            holder.spinnerInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    MasterEntity mSelectedItem = (MasterEntity) parent.getItemAtPosition(position);
                    if (mSelectedItem != null) {
                        item.setInputData(mSelectedItem.getId() + "");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

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