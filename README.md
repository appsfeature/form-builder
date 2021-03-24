# form-builder

#### Library size : 48Kb(lib) + 362.5Kb(retrofit2:converter-gson, okhttp3:logging-interceptor) = 410.5KB(Total)


<p align="left">
  <img src="https://raw.githubusercontent.com/appsfeature/form-builder/master/screenshots/sample1.png" alt="Preview 1" width="250" /> 
  <img src="https://raw.githubusercontent.com/appsfeature/form-builder/master/screenshots/sample2.png" alt="Preview 2" width="250" /> 
  <img src="https://raw.githubusercontent.com/appsfeature/form-builder/master/screenshots/sample3.png" alt="Preview 3" width="250" />
</p>

  
## Setup Project

Add this to your project build.gradle
``` gradle
allprojects {
    repositories {
        maven {
            url "https://jitpack.io"
        }
    }

    ext {
        appcompat = '1.2.0'
        material = '1.3.0'
        retrofit_version = '2.3.0'
        retrofit_okhttp_version = '3.10.0'
    }
}
```

Add this to your project build.gradle

#### Dependency
[![](https://jitpack.io/v/appsfeature/form-builder.svg)](https://jitpack.io/#appsfeature/form-builder)
```gradle
dependencies {
    implementation 'com.github.appsfeature:form-builder:1.0'
}
```
Needed support libs
```gradle
dependencies {
    implementation "androidx.appcompat:appcompat:$rootProject.ext.appcompat"
    implementation "com.google.android.material:material:$rootProject.ext.material"
}
```


### Usage methods
```java
public class MainActivity extends AppCompatActivity {

    private static final int SAMPLE_FORM_ID = 1;
    private static final String SAMPLE_FORM_NAME = "UP Board Result";
    private String sampleJson = "{\"formId\":52,\"title\":\"UP Board Result\",\"sub_title\":\"Note: To get UP Board Result update on WhatsApp and Gmail\",\"requestApi\":\"https://www.yourwebsite.in/api/v3/submit-form\",\"methodType\":\"POST\",\"buttonText\":\"Register\",\"popup\":{\"title\":\"Thank You!\",\"description\":\"You will get your result soon\",\"submit_btn_text\":\"Continue\"},\"fieldList\":[{\"fieldData\":null,\"fieldName\":\"Name\",\"fieldSuggestions\":null,\"fieldType\":1,\"inputType\":\"textPersonName\"},{\"fieldData\":null,\"fieldName\":\"Roll Number\",\"fieldSuggestions\":null,\"fieldType\":1,\"inputType\":\"number\"},{\"fieldData\":\"[{\\\"id\\\":1,\\\"title\\\":\\\"PCM\\\"},{\\\"id\\\":2,\\\"title\\\":\\\"PCMB\\\"},{\\\"id\\\":3,\\\"title\\\":\\\"Arts\\\"},{\\\"id\\\":4,\\\"title\\\":\\\"Commerce\\\"}]\",\"fieldName\":\"Select Steam\",\"fieldSuggestions\":null,\"fieldType\":2,\"inputType\":null},{\"fieldData\":null,\"fieldName\":\"Mobile No\",\"fieldSuggestions\":\"[\\\"9891983694\\\"]\",\"fieldType\":1,\"inputType\":\"phone\"},{\"fieldData\":null,\"fieldName\":\"Email Id\",\"fieldSuggestions\":\"[\\\"@gmail.com\\\", \\\"@yahoo.com\\\", \\\"@hotmail.com\\\", \\\"@outlook.com\\\"]\",\"fieldType\":1,\"inputType\":\"textEmailAddress\"},{\"fieldData\":null,\"fieldName\":\"Address\",\"fieldSuggestions\":null,\"fieldType\":1,\"inputType\":\"textMultiLine\"}]}";
    private boolean isOpenActivityByJson = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FormBuilder.getInstance()
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setDebugModeEnabled(BuildConfig.DEBUG);
    }

    @MainThread
    public void onOpenPreRegistration(View view) {
        if(isOpenActivityByJson) {
            FormBuilder.getInstance().openRegistrationActivity(this, SAMPLE_FORM_ID, sampleJson);
        }else {
            FormBuilder.getInstance().openRegistrationActivity(this, SAMPLE_FORM_ID, getCategoryProperty());
        }
    }

    @MainThread
    public void onClearPreferences(View view) {
        FBPreferences.setRegistrationCompleted(this, SAMPLE_FORM_ID, false);
    }

    private FormBuilderModel getCategoryProperty() {
        FormBuilderModel item = new FormBuilderModel();
        item.setFormId(SAMPLE_FORM_ID);
        item.setTitle(SAMPLE_FORM_NAME);
        item.setSubTitle("Note: To get UP Board Result update on WhatsApp and Gmail");
//        item.setRequestApi("https://www.yourwebsite.in/api/v3/submit-form");
        item.setMethodType("POST");
        item.setPopup(getPopup());
        item.setInputList(getInputFieldList());
        return item;
    }

    private List<DynamicInputModel> getInputFieldList() {
        List<DynamicInputModel> fieldList = new ArrayList<>();
        DynamicInputModel item;

        item = new DynamicInputModel();
        item.setFieldName("Name");
        item.setInputType(FieldInputType.textPersonName);
        item.setFieldType(FieldType.EDIT_TEXT);
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Select Steam");
        item.setFieldType(FieldType.SPINNER);
        item.setFieldData("[{\"id\":1,\"title\":\"PCM\"},{\"id\":2,\"title\":\"PCMB\"},{\"id\":3,\"title\":\"Arts\"},{\"id\":4,\"title\":\"Commerce\"}]");
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Select Gender");
        item.setFieldType(FieldType.RADIO_BUTTON);
        item.setFieldData("[\"Male\",\"Female\"]");
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Date of Birth");
        item.setFieldType(FieldType.DATE_PICKER);
        item.setFieldData("Select Date");
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Personal Detail");
        item.setFieldType(FieldType.TEXT_VIEW);
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Mobile No");
        item.setInputType(FieldInputType.phone);
        item.setFieldType(FieldType.EDIT_TEXT);
        item.setMaxLength(10);
        item.setFieldSuggestions("[\"9891983694\"]");
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Email Id");
        item.setInputType(FieldInputType.textEmailAddress);
        item.setFieldType(FieldType.EDIT_TEXT);
        item.setFieldSuggestions("[\"@gmail.com\", \"@yahoo.com\", \"@hotmail.com\", \"@outlook.com\"]");
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Address");
        item.setInputType(FieldInputType.number);
        item.setFieldType(FieldType.EDIT_TEXT);
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Subscribe for news updates");
        item.setFieldType(FieldType.CHECK_BOX);
        fieldList.add(item);

        return fieldList;
    }

    private PopupEntity getPopup() {
        PopupEntity popupEntity = new PopupEntity();
        popupEntity.setTitle("Thank You!");
        popupEntity.setDescription("You will get your result soon");
        popupEntity.setButtonText("Continue");
        return popupEntity;
    }
}
```
