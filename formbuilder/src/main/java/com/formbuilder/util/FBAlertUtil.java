package com.formbuilder.util;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.formbuilder.FormBuilder;
import com.formbuilder.R;
import com.formbuilder.model.entity.PopupEntity;

public class FBAlertUtil {

    public static void showSuccessDialog(Activity activity, PopupEntity popup, String data) {
        if(activity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.pre_alert_success, null);
            builder.setView(dialogView);
            View alertBackground = dialogView.findViewById(R.id.alert_background);
            TextView tvTitle = dialogView.findViewById(R.id.tv_alert_title);
            TextView tvDescription = dialogView.findViewById(R.id.tv_alert_description);
            Button btnAlert = dialogView.findViewById(R.id.btn_alert);
            if (popup != null) {
                tvTitle.setText(popup.getTitle());
                tvDescription.setText(popup.getDescription());
                btnAlert.setText(popup.getButtonText());
            }
            alertBackground.setBackgroundColor(Color.parseColor(FBUtility.getColorValue(activity, "20", R.color.colorPrimary)));
            final AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            btnAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        FormBuilder.getInstance().dispatchOnFormSubmit(data);
                        activity.finish();
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            dialog.show();
        }
    }
}
