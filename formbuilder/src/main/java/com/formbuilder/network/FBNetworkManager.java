package com.formbuilder.network;

import android.content.Context;

import com.formbuilder.FormBuilder;
import com.formbuilder.R;
import com.formbuilder.interfaces.FieldType;
import com.formbuilder.interfaces.FormResponse;
import com.formbuilder.interfaces.RequestType;
import com.formbuilder.interfaces.SubmissionType;
import com.formbuilder.model.DynamicInputModel;
import com.formbuilder.model.FBNetworkModel;
import com.formbuilder.model.FormBuilderModel;
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

    private final FBApiConfig mRetrofit;
    private final Context context;


    public FBNetworkManager(Context context, String baseUrl) {
        this.context = context;
        this.mRetrofit = FBRetrofit.getClient(baseUrl).create(FBApiConfig.class);
    }

    public void submitRegistration(FormBuilderModel property, List<DynamicInputModel> mList, FormResponse.Callback<FBNetworkModel> callback) {
        if (property != null && mList != null && mList.size() > 0) {
            if (property.getSubmissionType() == SubmissionType.KEY_VALUE_PAIR) {
                sendRequestByKeyValuePair(property, mList, callback);
            } else {
                sendRequestByBulkJson(property, mList, callback);
            }
        }
    }

    private void sendRequestByBulkJson(FormBuilderModel property, List<DynamicInputModel> mList, FormResponse.Callback<FBNetworkModel> callback) {
        TaskRunner.getInstance().executeAsync(new Callable<FBNetworkModel>() {
            @Override
            public FBNetworkModel call() throws Exception {
                try {
                    String userData = getSubmitModel(property.getFormId(), mList).toJson();
                    FBUtility.log("JSON : " + userData);

                    Map<String, String> params = new HashMap<>();
                    if (property.getExtraParams() != null) {
                        for (Map.Entry<String, String> entry : property.getExtraParams().entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            params.put(key, value);
                        }
                    }
                    params.put("data", FBUtility.encode(userData));
                    Response<FBNetworkModel> response;
                    if (property.getRequestType() == RequestType.GET) {
                        response = mRetrofit.requestGet(property.getRequestApi(), params).execute();
                    } else if (property.getRequestType() == RequestType.POST_FORM) {
                        response = mRetrofit.requestPostDataForm(property.getRequestApi(), params).execute();
                    } else {
                        response = mRetrofit.requestPost(property.getRequestApi(), params).execute();
                    }
                    if (response.body() != null) {
                        response.body().setData(userData);
                        return response.body();
                    } else {
                        return new FBNetworkModel(FBConstant.FAILURE, response.message());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return new FBNetworkModel(FBConstant.FAILURE, e.getMessage());
                }
            }
        }, new TaskRunner.Callback<FBNetworkModel>() {
            @Override
            public void onComplete(FBNetworkModel finalData) {
                callback.onSuccess(finalData);
            }
        });
    }

    private void sendRequestByKeyValuePair(FormBuilderModel property, List<DynamicInputModel> mList, FormResponse.Callback<FBNetworkModel> callback) {
        TaskRunner.getInstance().executeAsync(new Callable<FBNetworkModel>() {
            @Override
            public FBNetworkModel call() throws Exception {
                try {
                    Map<String, String> params = new HashMap<>();
                    params.put("form_id", "" + property.getFormId());
                    params.put("application_id", "" + context.getPackageName());
                    params.put("timestamp", "" + getServerTimeStamp());
                    if (property.getExtraParams() != null) {
                        for (Map.Entry<String, String> entry : property.getExtraParams().entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            params.put(key, value);
                        }
                    }
                    for (DynamicInputModel field : mList) {
                        if (field.getFieldType() != FieldType.TEXT_VIEW) {
                            params.put(field.getParamKey(), field.getInputData() != null ? field.getInputData() : "");
                        }
                    }

                    Response<FBNetworkModel> response;
                    if (property.getRequestType() == RequestType.GET) {
                        response = mRetrofit.requestGet(property.getRequestApi(), params).execute();
                    } else if (property.getRequestType() == RequestType.POST_FORM) {
                        response = mRetrofit.requestPostDataForm(property.getRequestApi(), params).execute();
                    } else {
                        response = mRetrofit.requestPost(property.getRequestApi(), params).execute();
                    }
                    if (response.body() != null) {
                        return response.body();
                    } else {
                        return new FBNetworkModel(FBConstant.FAILURE, response.message());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return new FBNetworkModel(FBConstant.FAILURE, e.getMessage());
                }
            }
        }, new TaskRunner.Callback<FBNetworkModel>() {
            @Override
            public void onComplete(FBNetworkModel finalData) {
                callback.onSuccess(finalData);
            }
        });
    }

    private PRSubmitModel getSubmitModel(int formId, List<DynamicInputModel> mList) {
        PRSubmitModel model = new PRSubmitModel();
        model.setFormId(formId);
        model.setVersion(FormBuilder.LIBRARY_VERSION);
        model.setApplicationId(context.getPackageName());
        model.setAppVersion(FormBuilder.getInstance().getAppVersion());
        model.setTimestamp(getServerTimeStamp());
        model.setAppName(context.getString(R.string.app_name));
        model.setInputFieldData(mList);
        return model;
    }

    private String getServerTimeStamp() {
        SimpleDateFormat outFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date = new Date(System.currentTimeMillis());
        return outFmt.format(date);
    }
}