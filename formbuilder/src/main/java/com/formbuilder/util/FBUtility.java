package com.formbuilder.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.formbuilder.FormBuilder;
import com.formbuilder.R;
import com.formbuilder.interfaces.FormResponse;
import com.formbuilder.model.FormLocationProperties;
import com.location.picker.interfaces.LocationProperties;

import java.io.UnsupportedEncodingException;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FBUtility {

    public static String getColorValue(Context context, int colorResource) {
        return getColorValue(context, "", colorResource);
    }
    public static String getColorValue(Context context, String transparentLevel, int colorResource) {
        try {
            String colorValue = Integer.toHexString(ContextCompat.getColor(context, colorResource) & 0x00ffffff);
            if(colorValue.length() < 6){
                switch (colorValue.length()){
                    case 5:
                        colorValue = "0" + colorValue;
                        break;
                    case 4:
                        colorValue = "00" + colorValue;
                        break;
                    case 3:
                        colorValue = "000" + colorValue;
                        break;
                }
            }
            return "#" + transparentLevel + colorValue;
        } catch (Exception e) {
            e.printStackTrace();
            return "#ffffff";
        }
    }

    public static boolean isConnected(Context context) {
        boolean isConnected = false;
        try {
            if (context != null && context.getSystemService(Context.CONNECTIVITY_SERVICE) != null && context.getSystemService(Context.CONNECTIVITY_SERVICE) instanceof ConnectivityManager) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Network network = connectivityManager.getActiveNetwork();
                    if (network != null) {
                        NetworkCapabilities nc = connectivityManager.getNetworkCapabilities(network);
                        isConnected = nc != null && (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || nc.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
                    }
                } else {
                    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                    isConnected = activeNetwork != null && activeNetwork.isConnected();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }



    public static void showNoData(View view, @FormResponse.Visibility int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
            if(visibility == VISIBLE) {
                TextView tvNoData = view.findViewById(R.id.tv_no_data);
                if (view.findViewById(R.id.player_progressbar) != null) {
                    view.findViewById(R.id.player_progressbar).setVisibility(GONE);
                }
                if (tvNoData != null) {
                    tvNoData.setVisibility(VISIBLE);
                    if (!isConnected(view.getContext())) {
                        tvNoData.setText(FBConstant.NO_INTERNET_CONNECTION);
                    } else {
                        tvNoData.setText(FBConstant.NO_DATA);
                    }
                }
            }
        }
    }

    public static void showNoDataProgress(View view) {
        if (view != null) {
            view.setVisibility(VISIBLE);
            if (view.findViewById(R.id.player_progressbar) != null) {
                view.findViewById(R.id.player_progressbar).setVisibility(VISIBLE);
            }
            TextView tvNoData = view.findViewById(R.id.tv_no_data);
            if (tvNoData != null) {
                tvNoData.setVisibility(GONE);
            }
        }
    }

    public static void showPropertyError(Activity activity) {
        if (activity != null) {
            showToastCentre(activity, FBConstant.Error.MSG_ERROR);
            activity.finish();
        }
    }

    public static void showToastCentre(Context context, String msg) {
        if(context != null) {
            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public static void log(String message) {
        if(FormBuilder.getInstance().isDebugModeEnabled()){
            Log.d(FormBuilder.class.getSimpleName(), message == null ? "null" : message);
        }
    }

    public static String encode(String value) {
        try {
            if(value == null){
                return value;
            }
            byte[] data = value.getBytes("UTF-8");
            return Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return value;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }
    }

    public static void hideKeyboard(Activity activity) {
        try {
            if(activity != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                View f = activity.getCurrentFocus();
                if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
                    imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
                else
                    activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LocationProperties getLocationProperty(FormLocationProperties properties) {
        LocationProperties lp = new LocationProperties();
        lp.setApiKey(properties.getApiKey());
        lp.setEnableAddressLine1(properties.isEnableAddressLine1);
        lp.setEnableAddressLine2(properties.isEnableAddressLine2);
        lp.setEnableButtonDirection(properties.isEnableButtonDirection);
        lp.setEnableButtonMap(properties.isEnableButtonMap);
        lp.setEnableCityDetails(properties.isEnableCityDetails);
        lp.setEnableSearchBar(properties.isEnableSearchBar);
        lp.setEnableTranslucentStatus(properties.isEnableTranslucentStatus);
        lp.setHintAddressLine1(properties.hintAddressLine1);
        return lp;
    }
}
