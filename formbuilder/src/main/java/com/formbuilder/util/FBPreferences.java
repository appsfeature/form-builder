package com.formbuilder.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


public class FBPreferences {

    public static final String IS_REGISTRATION_COMPLETED = "is_registration_completed";

    private static SharedPreferences sharedPreferences;

    public static SharedPreferences getSharedPreferenceObj(Context context){
        if (context != null && sharedPreferences == null )
            sharedPreferences = context.getSharedPreferences(context.getPackageName() , Context.MODE_PRIVATE);

        return sharedPreferences ;
    }
    public static String getString(Context context, String key) {
        if (getSharedPreferenceObj(context) != null) {
            return decrypt(getSharedPreferenceObj(context).getString(encrypt(key), ""));
        } else {
            return decrypt("");
        }
    }
    public static int getInt(Context context, String key) {
        if (getSharedPreferenceObj(context) != null) {
            return getSharedPreferenceObj(context).getInt(encrypt(key), 0);
        } else {
            return (0);
        }
    }
    public static int getIntDef(Context context, String key, int def) {
        return getSharedPreferenceObj(context).getInt(encrypt(key), def);
    }
    public static float getFloat(Context context, String key) {
        return getSharedPreferenceObj(context).getFloat(encrypt(key), 0);

    }
    public static long getLong(Context context, String key) {
        return getSharedPreferenceObj(context).getLong(encrypt(key), 0);
    }
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        if (getSharedPreferenceObj(context) != null) {
            return getSharedPreferenceObj(context).getBoolean(key, defaultValue);
        } else {
            return false;
        }
    }
    public static void setString(Context context, String key, String values) {
        if (getSharedPreferenceObj(context) != null && !TextUtils.isEmpty(key)) {
            final SharedPreferences.Editor editor = getSharedPreferenceObj(context).edit();
            if (editor != null) {
                editor.putString(encrypt(key), encrypt(values));
                editor.apply();
            }
        }
    }
    public static void setInt(Context context, String key, int value) {
        if (getSharedPreferenceObj(context) != null && !TextUtils.isEmpty(key)) {
            final SharedPreferences.Editor editor = getSharedPreferenceObj(context).edit();
            if (editor != null) {
                editor.putInt(encrypt(key), value);
                editor.apply();
            }
        }
    }
    public static void setFloat(Context context, String key, float value) {
        final SharedPreferences.Editor editor = getSharedPreferenceObj(context).edit();
        editor.putFloat(encrypt(key), value);
        editor.apply();
    }
    public static void setLong(Context context, String key, long value) {
        final SharedPreferences.Editor editor = getSharedPreferenceObj(context).edit();
        editor.putLong(encrypt(key), value);
        editor.apply();
    }
    public static void setBoolean(Context context, String key, boolean value) {
        if (getSharedPreferenceObj(context) != null && !TextUtils.isEmpty(key)) {
            final SharedPreferences.Editor editor = getSharedPreferenceObj(context).edit();
            if (editor != null) {
                editor.putBoolean(encrypt(key), value);
                editor.apply();
            }
        }
    }

    /**
     * Clear all preferences.
     */
    public static void clearPreferences(Context context) {
        final SharedPreferences.Editor editor = getSharedPreferenceObj(context).edit();
        editor.clear();
        editor.apply();
    }

    private static String encrypt(String input) {
        return input;
    }

    private static String decrypt(String input) {
        return input;
    }

    public static void setRegistrationCompleted(Context context, int formId, boolean value) {
        setBoolean(context, IS_REGISTRATION_COMPLETED + formId, value);
    }

    public static boolean isRegistrationCompleted(Context context, int formId) {
        return getBoolean(context, IS_REGISTRATION_COMPLETED + formId, false);
    }
}