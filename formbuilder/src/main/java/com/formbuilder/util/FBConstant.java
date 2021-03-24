package com.formbuilder.util;

public interface FBConstant {
    String TITLE = "title";

    String NO_DATA = "No Data";
    String EMPTY_OR_NULL_DATA = "Empty or Null Data";

    String SUCCESS = "success";
    String FAILURE = "failure";
    String NO_INTERNET_CONNECTION = "No Internet Connection";

    String CATEGORY_PROPERTY = "category_property";
    String USER_DATA = "user_data";
    String DEFAULT_BUTTON_TEXT = "Submit";

    interface Error {
        String MSG_ERROR = "Error, please try later.";
        String DATA_NOT_FOUND = "Error, Data not found";
        String CATEGORY_NOT_FOUND = "Error, This is not supported. Please update";
        String INVALID_FORM_DATA = "Invalid Form Data";
    }
}
