package com.formbuilder.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.formbuilder.R;
import com.formbuilder.adapter.DynamicInputAdapter;
import com.formbuilder.adapter.holder.SpinnerViewHolder;
import com.formbuilder.interfaces.FormResponse;
import com.formbuilder.interfaces.ValidationCheck;
import com.formbuilder.model.DynamicInputModel;
import com.formbuilder.model.FBNetworkModel;
import com.formbuilder.model.FormBuilderModel;
import com.formbuilder.network.FBNetworkManager;
import com.formbuilder.util.FBAlertUtil;
import com.formbuilder.util.FBConstant;
import com.formbuilder.util.FBPreferences;
import com.formbuilder.util.FBProgressButton;
import com.formbuilder.util.FBUtility;
import com.formbuilder.util.FieldValidation;

import java.util.ArrayList;
import java.util.List;


public class FormBuilderFragment extends Fragment {

    private View layoutNoData;
    private DynamicInputAdapter adapter;
    private final List<DynamicInputModel> mList = new ArrayList<>();
    private FormBuilderModel property;
    private TextView tvSubTitle;
    private String title;
    private FBNetworkManager networkManager;
    private FBProgressButton btnAction;
    private RecyclerView mRecyclerView;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pre_registration_list, container, false);
        activity = getActivity();
        initUI(v);
        return v;
    }

    private void initUI(View v) {
        getIntentData();
        initView(v);

        loadData();
    }

    private void loadData() {
        if (!TextUtils.isEmpty(property.getSubTitle())) {
            tvSubTitle.setText(property.getSubTitle());
            tvSubTitle.setVisibility(View.VISIBLE);
        } else {
            tvSubTitle.setVisibility(View.GONE);
        }
        btnAction.setText(property.getButtonText());

        loadList(property.getInputList());
    }


    private void getIntentData() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getSerializable(FBConstant.CATEGORY_PROPERTY) instanceof FormBuilderModel) {
            property = (FormBuilderModel) bundle.getSerializable(FBConstant.CATEGORY_PROPERTY);
            title = property.getTitle();
            networkManager = new FBNetworkManager(activity, property.getBaseUrl());
        } else {
            FBUtility.showPropertyError(activity);
        }
    }


    private void initView(View v) {
        layoutNoData = v.findViewById(R.id.ll_no_data);
        tvSubTitle = v.findViewById(R.id.tv_sub_title);
        mRecyclerView = v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        adapter = new DynamicInputAdapter(activity, mList);
        mRecyclerView.setAdapter(adapter);

        btnAction = FBProgressButton.newInstance(activity, v)
                .setText(FBConstant.DEFAULT_BUTTON_TEXT)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!isValidationCheck()){
                            return;
                        }
                        FBUtility.hideKeyboard(activity);
                        submitRequest();
                    }
                });
    }

    private boolean isValidationCheck() {
        boolean isValidAllFields = true;
        if(mRecyclerView != null && mRecyclerView.getLayoutManager() != null) {
            for (int i = 0; i < mList.size(); i++){
                if(!mList.get(i).getValidation().equals(ValidationCheck.NOT_REQUIRED)) {
                    if(mList.get(i).getValidation().equals(ValidationCheck.SPINNER)) {
                        if(TextUtils.isEmpty(mList.get(i).getInputData()) || mList.get(i).getInputData().equals("0")){
                            SpinnerViewHolder.showValidationError(activity, mList.get(i).getFieldName());
                            isValidAllFields = false;
                        }
                    }else{
                        View mRecycleView = mRecyclerView.getLayoutManager().getChildAt(i);
                        if (mRecycleView != null && mRecycleView.findViewById(R.id.et_input_text) instanceof EditText) {
                            EditText editText = mRecycleView.findViewById(R.id.et_input_text);
                            if (editText != null) {
                                boolean status = FieldValidation.check(activity, editText, mList.get(i).getValidation());
                                if (!status) {
                                    isValidAllFields = status;
                                }
                            }
                        }
                    }
                }
            }
        }
        return isValidAllFields;
    }

    private void loadList(List<DynamicInputModel> list) {
        FBUtility.showNoData(layoutNoData, View.GONE);
        mList.clear();
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        if (list == null || list.size() <= 0) {
            FBUtility.showNoData(layoutNoData, View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    private void submitRequest() {
        if (btnAction != null) {
            btnAction.startProgress(new FBProgressButton.Listener() {
                @Override
                public void onAnimationCompleted() {
                    if (networkManager != null && property != null) {
                        networkManager.submitRegistration(property, mList, new FormResponse.Callback<FBNetworkModel>() {
                            @Override
                            public void onSuccess(FBNetworkModel response) {
                                if (response != null && response.getStatus().equalsIgnoreCase(FBConstant.SUCCESS)) {
                                    FBPreferences.setRegistrationCompleted(activity, property.getFormId(), true);
                                    btnAction.revertSuccessProgress(new FBProgressButton.Listener() {
                                        @Override
                                        public void onAnimationCompleted() {
                                            FBAlertUtil.showSuccessDialog(activity, property.getPopup(), response.getData());
                                        }
                                    });
                                } else {
                                    btnAction.revertProgress();
                                    FBUtility.showToastCentre(activity, FBConstant.Error.MSG_ERROR);
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}