package com.sample.preregistration;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import com.formbuilder.FormBuilder;
import com.formbuilder.interfaces.FieldInputType;
import com.formbuilder.interfaces.FieldType;
import com.formbuilder.interfaces.FormResponse;
import com.formbuilder.interfaces.RequestType;
import com.formbuilder.interfaces.SubmissionType;
import com.formbuilder.interfaces.ValidationCheck;
import com.formbuilder.model.DynamicInputModel;
import com.formbuilder.model.FormBuilderModel;
import com.formbuilder.model.entity.PopupEntity;
import com.formbuilder.util.FBPreferences;
import com.formbuilder.util.GsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int SAMPLE_FORM_ID = 1;
    private static final String SAMPLE_FORM_NAME = "Dynamic Form Builder";
    private static final String sampleJson = "{\"formId\":1,\"title\":\"Dynamic Form Builder\",\"subTitle\":\"Create all fields dynamic by json or Model structure.\",\"submissionType\":1,\"isShowActionbar\":false,\"buttonText\":\"Submit\",\"baseUrl\":\"https://www.yourwebsite.in/api/v3/\",\"requestApi\":\"submit-form\",\"requestType\":3,\"popup\":{\"buttonText\":\"Continue\",\"description\":\"You will get your updates soon\",\"title\":\"Thank You!\"},\"fieldList\":[{\"fieldName\":\"Name\",\"fieldType\":1,\"inputType\":\"textPersonName\",\"isSpinnerSelectTitle\":false,\"maxLength\":0,\"paramKey\":\"name\",\"validation\":\"\"},{\"fieldData\":\"[{\\\"id\\\":1,\\\"title\\\":\\\"PCM\\\"},{\\\"id\\\":2,\\\"title\\\":\\\"PCMB\\\"},{\\\"id\\\":3,\\\"title\\\":\\\"Arts\\\"},{\\\"id\\\":4,\\\"title\\\":\\\"Commerce\\\"}]\",\"fieldName\":\"Select Steam\",\"fieldType\":2,\"inputType\":\"text\",\"isSpinnerSelectTitle\":true,\"maxLength\":0,\"paramKey\":\"steam\",\"validation\":\"\"},{\"fieldData\":\"[\\\"Male\\\",\\\"Female\\\"]\",\"fieldName\":\"Select Gender\",\"fieldType\":3,\"inputType\":\"text\",\"isSpinnerSelectTitle\":false,\"maxLength\":0,\"paramKey\":\"gender\",\"validation\":\"\"},{\"fieldData\":\"Select Date\",\"fieldName\":\"Date of Birth\",\"fieldType\":5,\"inputType\":\"text\",\"isSpinnerSelectTitle\":false,\"maxLength\":0,\"paramKey\":\"dob\",\"validation\":\"\"},{\"fieldName\":\"Personal Detail\",\"fieldType\":0,\"inputType\":\"text\",\"isSpinnerSelectTitle\":false,\"maxLength\":0,\"paramKey\":\"personal_detail\",\"validation\":\"\"},{\"fieldName\":\"Mobile No\",\"fieldSuggestions\":\"[\\\"9891983694\\\"]\",\"fieldType\":1,\"inputType\":\"phone\",\"isSpinnerSelectTitle\":false,\"maxLength\":10,\"paramKey\":\"mobile\",\"validation\":\"\"},{\"fieldName\":\"Email Id\",\"fieldSuggestions\":\"[\\\"@gmail.com\\\", \\\"@yahoo.com\\\", \\\"@hotmail.com\\\", \\\"@outlook.com\\\"]\",\"fieldType\":1,\"inputType\":\"textEmailAddress\",\"isSpinnerSelectTitle\":false,\"maxLength\":0,\"paramKey\":\"email_id\",\"validation\":\"\"},{\"fieldName\":\"Address\",\"fieldType\":6,\"inputType\":\"textMultiLine\",\"isSpinnerSelectTitle\":false,\"maxLength\":0,\"paramKey\":\"address\",\"validation\":\"\"},{\"fieldName\":\"Subscribe for news updates\",\"fieldType\":6,\"inputType\":\"text\",\"isSpinnerSelectTitle\":false,\"maxLength\":0,\"paramKey\":\"agree_check_box\",\"validation\":\"\"}]}";
    private static final boolean isOpenActivityByJson = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FormBuilder.getInstance()
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setEnableJsonEncode(true)
                .setDebugModeEnabled(BuildConfig.DEBUG);
    }

    @MainThread
    public void onOpenDynamicForm(View view) {
        if(isOpenActivityByJson) {
            FormBuilder.getInstance().openDynamicFormActivity(this, SAMPLE_FORM_ID, sampleJson, null);
        }else {
            String property = GsonParser.toJsonAll(getCategoryProperty(), new TypeToken<FormBuilderModel>() {});
            FormBuilder.getInstance().openDynamicFormActivity(this, SAMPLE_FORM_ID, getCategoryProperty(), new FormResponse.FormSubmitListener() {
                @Override
                public void onFormSubmitted(String data) {

                }
            });
        }
    }

    @MainThread
    public void onClearPreferences(View view) {
        FBPreferences.setFormSubmitted(this, SAMPLE_FORM_ID, false);
    }

    private FormBuilderModel getCategoryProperty() {
        FormBuilderModel item = new FormBuilderModel();
        item.setFormId(SAMPLE_FORM_ID);
        item.setTitle(SAMPLE_FORM_NAME);
        item.setSubTitle("Create all fields dynamic by json or Model structure.");
        item.setBaseUrl("https://www.yourwebsite.in/api/v3/");
        item.setRequestApi("submit-form");
        item.setRequestType(RequestType.POST_FORM);
        item.setSubmissionType(SubmissionType.KEY_VALUE_PAIR);
        item.setPopup(getPopup());
        item.setInputList(getInputFieldList());
        item.setExtraParams(getExtraParams());
        return item;
    }

    private List<DynamicInputModel> getInputFieldList() {
        List<DynamicInputModel> fieldList = new ArrayList<>();
        DynamicInputModel item;

        item = new DynamicInputModel();
        item.setFieldName("Name");
        item.setParamKey("name");
        item.setInputType(FieldInputType.textPersonName);
        item.setFieldType(FieldType.EDIT_TEXT);
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Select Steam");
        item.setParamKey("steam");
        item.setFieldType(FieldType.SPINNER);
        item.setSpinnerSelectTitle(true);
//        item.setValidation(ValidationCheck.SPINNER);
        item.setFieldData("[{\"id\":1,\"title\":\"PCM\"},{\"id\":2,\"title\":\"PCMB\"},{\"id\":3,\"title\":\"Arts\"},{\"id\":4,\"title\":\"Commerce\"}]");
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Select Gender");
        item.setParamKey("gender");
        item.setFieldType(FieldType.RADIO_BUTTON);
        item.setFieldData("[\"Male\",\"Female\"]");
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Date of Birth");
        item.setParamKey("dob");
        item.setFieldType(FieldType.DATE_PICKER);
        item.setFieldData("Select Date");
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Personal Detail");
        item.setParamKey("personal_detail");
        item.setFieldType(FieldType.TEXT_VIEW);
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Mobile No");
        item.setParamKey("mobile");
        item.setInputType(FieldInputType.phone);
        item.setFieldType(FieldType.EDIT_TEXT);
        item.setMaxLength(10);
        item.setFieldSuggestions("[\"9891983694\"]");
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Email Id");
        item.setParamKey("email_id");
        item.setInputType(FieldInputType.textEmailAddress);
        item.setFieldType(FieldType.EDIT_TEXT);
        item.setFieldSuggestions("[\"@gmail.com\", \"@yahoo.com\", \"@hotmail.com\", \"@outlook.com\"]");
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Address");
        item.setParamKey("address");
        item.setInputType(FieldInputType.textMultiLine);
        item.setFieldType(FieldType.EDIT_TEXT);
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Select Location");
        item.setParamKey("location");
        item.setValidation(ValidationCheck.EMPTY);
        item.setInputType(FieldInputType.locationLatLng);
        item.setFieldType(FieldType.LOCATION);
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Subscribe for news updates");
        item.setParamKey("agree_check_box");
        item.setFieldType(FieldType.CHECK_BOX);
        item.setValidation(ValidationCheck.CHECK_BOX);
        fieldList.add(item);

        return fieldList;
    }

    private PopupEntity getPopup() {
        PopupEntity popupEntity = new PopupEntity();
        popupEntity.setTitle("Thank You!");
        popupEntity.setDescription("You will get your updates soon");
        popupEntity.setButtonText("Continue");
        return popupEntity;
    }

    private Map<String, String> getExtraParams() {
        Map<String, String> params = new HashMap<>();
        params.put("app_name", getString(R.string.app_name));
        return params;
    }
}