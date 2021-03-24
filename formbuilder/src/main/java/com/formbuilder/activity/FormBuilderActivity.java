package com.formbuilder.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.formbuilder.R;
import com.formbuilder.adapter.DynamicInputAdapter;
import com.formbuilder.interfaces.FormResponse;
import com.formbuilder.model.DynamicInputModel;
import com.formbuilder.model.FBNetworkModel;
import com.formbuilder.model.FormBuilderModel;
import com.formbuilder.network.FBNetworkManager;
import com.formbuilder.util.FBAlertUtil;
import com.formbuilder.util.FBConstant;
import com.formbuilder.util.FBPreferences;
import com.formbuilder.util.FBUtility;
import com.formbuilder.util.FBProgressButton;

import java.util.ArrayList;
import java.util.List;


public class FormBuilderActivity extends AppCompatActivity {

    private View layoutNoData;
    private DynamicInputAdapter adapter;
    private final List<DynamicInputModel> mList = new ArrayList<>();
    private FormBuilderModel property;
    private TextView tvSubTitle;
    private String title;
    private FBNetworkManager networkManager;
    private FBProgressButton btnAction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_registration_list);
        getIntentData();
        initView();

        loadData();
    }

    private void loadData() {
        if(!TextUtils.isEmpty(property.getSubTitle())) {
            tvSubTitle.setText(property.getSubTitle());
            tvSubTitle.setVisibility(View.VISIBLE);
        }else {
            tvSubTitle.setVisibility(View.GONE);
        }
        btnAction.setText(property.getButtonText());

        loadList(property.getInputList());
    }


    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getSerializable(FBConstant.CATEGORY_PROPERTY) instanceof FormBuilderModel) {
            property = (FormBuilderModel) bundle.getSerializable(FBConstant.CATEGORY_PROPERTY);
            title = property.getTitle();
            networkManager = new FBNetworkManager(this, property.getRequestApi());
            setUpToolBar();
        }else {
            FBUtility.showPropertyError(this);
        }
    }

    private void initView() {
        layoutNoData = findViewById(R.id.ll_no_data);
        tvSubTitle = findViewById(R.id.tv_sub_title);
        RecyclerView rvList = findViewById(R.id.recycler_view);
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new DynamicInputAdapter(mList);
        rvList.setAdapter(adapter);

        btnAction = FBProgressButton.newInstance(this)
                .setText(FBConstant.DEFAULT_BUTTON_TEXT)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitRequest();
                    }
                });
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

    private void setUpToolBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (!TextUtils.isEmpty(title)) {
                actionBar.setTitle(title);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if( id == android.R.id.home ){
            onBackPressed();
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submitRequest() {
        if (btnAction != null) {
            btnAction.startProgress(new FBProgressButton.Listener() {
                @Override
                public void onAnimationCompleted() {
                    if (networkManager != null && property != null) {
                        networkManager.submitRegistration(property.getFormId(), mList, new FormResponse.Callback<FBNetworkModel>() {
                            @Override
                            public void onSuccess(FBNetworkModel response) {
                                if (response != null && response.getStatus().equalsIgnoreCase(FBConstant.SUCCESS)) {
                                    FBPreferences.setRegistrationCompleted(FormBuilderActivity.this, property.getFormId(), true);
                                    btnAction.revertSuccessProgress(new FBProgressButton.Listener() {
                                        @Override
                                        public void onAnimationCompleted() {
                                            FBAlertUtil.showSuccessDialog(FormBuilderActivity.this, property.getPopup(), response.getData());
                                        }
                                    });
                                } else {
                                    btnAction.revertProgress();
                                    FBUtility.showToastCentre(FormBuilderActivity.this, FBConstant.Error.MSG_ERROR);
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}