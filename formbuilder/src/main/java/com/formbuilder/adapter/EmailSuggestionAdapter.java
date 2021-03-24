package com.formbuilder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EmailSuggestionAdapter extends ArrayAdapter<String> {
    private final int viewResourceId;

    private final List<String> mList;
    public final ArrayList<String> tempList;

    public EmailSuggestionAdapter(Context context, int viewResourceId, List<String> items) {
        super(context, viewResourceId, items);
        this.viewResourceId = viewResourceId;
        this.mList = items;
        this.tempList = new ArrayList<>(mList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        String customer = mList.get(position);
        if (customer != null) {
            TextView customerNameLabel = (TextView) v;
            if (customerNameLabel != null) {
                customerNameLabel.setText(customer);
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    private final Filter nameFilter = new Filter() {
        public String convertResultToString(Object resultValue) {
            return (String) resultValue;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();
            if (constraint != null) {
                String inputText = constraint.toString();
                if (inputText.contains("@")) {
                    String postAtValue = inputText.substring(inputText.indexOf("@"));
                    String preAtValue = "";
                    try {
                        preAtValue = inputText.substring(0, inputText.indexOf("@"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    for (String customer : tempList) {
                        if (customer.toLowerCase().startsWith(postAtValue.toLowerCase())) {
                            filteredList.add(preAtValue + customer);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filteredList;
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {
                mList.clear();
                notifyDataSetChanged();
                if (results.values != null) {
                    mList.addAll((List<String>) results.values);
                    notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}