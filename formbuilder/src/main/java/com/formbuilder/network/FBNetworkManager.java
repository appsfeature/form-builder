package com.formbuilder.network;

import android.content.Context;

import com.formbuilder.FormBuilder;
import com.formbuilder.R;
import com.formbuilder.interfaces.FormResponse;
import com.formbuilder.model.DynamicInputModel;
import com.formbuilder.model.FBNetworkModel;
import com.formbuilder.model.PRSubmitModel;
import com.formbuilder.util.FBConstant;
import com.formbuilder.util.FBUtility;
import com.formbuilder.util.TaskRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

import retrofit2.Response;

public class FBNetworkManager {

    private FBApiConfig mRetrofit;
    private final String host;
    private final Context context;


    public FBNetworkManager(Context context, String host) {
        this.context = context;
        this.host = host;
        if (FBRetrofit.getClient(host) != null) {
            this.mRetrofit = FBRetrofit.getClient(host).create(FBApiConfig.class);
        }
    }

    public void submitRegistration(int formId, List<DynamicInputModel> mList, FormResponse.Callback<FBNetworkModel> callback) {
        if(mList != null && mList.size() > 0){
            TaskRunner.getInstance().executeAsync(new Callable<FBNetworkModel>() {
                @Override
                public FBNetworkModel call() throws Exception {
                    try {
                        String userData = getSubmitModel(formId, mList).toJson();
                        FBUtility.log("JSON : " + userData);
                        if(mRetrofit != null) {
                            Map<String, String> params = new HashMap<>();
                            params.put("userData", FBUtility.encode(userData));
                            Response<FBNetworkModel> response = mRetrofit.postDataRequest(host, params).execute();
                            if (response.body() != null) {
                                response.body().setData(userData);
                                return response.body();
                            }
                        }else {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return new FBNetworkModel(FBConstant.SUCCESS, userData);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }, new TaskRunner.Callback<FBNetworkModel>() {
                @Override
                public void onComplete(FBNetworkModel finalData) {
                    callback.onSuccess(finalData);
                }
            });
        }
    }

    private PRSubmitModel getSubmitModel(int formId, List<DynamicInputModel> mList){
        PRSubmitModel model = new PRSubmitModel();
        model.setFormId(formId);
        model.setVersion(FormBuilder.LIBRARY_VERSION);
        model.setApplicationId(context.getPackageName());
        model.setAppVersion(FormBuilder.getInstance().getAppVersion());
        model.setTimestamp(getServerTimeStamp());
        model.setAppName(context.getString(R.string.app_name));
        model.setBoardResultField(mList);
        return model;
    }

    private String getServerTimeStamp() {
        SimpleDateFormat outFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date = new Date(System.currentTimeMillis());
        return outFmt.format(date);
    }
}