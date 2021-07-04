package com.master.design.blackeye.Helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.master.design.blackeye.R;


/**
 * Created by Dakshay Sanghvi on 6/6/2016.
 */
public class DialogUtil {

    public static void showDialogTwoButton(final Context context, int icon, String title, String message, String positiveButton, String negativeButton, final CallBack callBack) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout_for_alert_message);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        //layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        final Button btn_ok = dialog.findViewById(R.id.btn_ok);
        final Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        ImageView iv_icon = dialog.findViewById(R.id.iv_icon);

        final RelativeLayout layout_main = dialog.findViewById(R.id.layout_main);
        TextView txt_title = dialog.findViewById(R.id.txt_title);
        TextView txt_message = dialog.findViewById(R.id.txt_message);

        if (icon != 0) {
            iv_icon.setImageResource(icon);
        } else {
            iv_icon.setImageResource(R.drawable.app_icon);
        }
        txt_message.setText(message);
        txt_title.setText(title);
        if (positiveButton == null) {
            btn_ok.setText(context.getString(R.string.ok));
        } else {
            btn_ok.setText(positiveButton);
        }
        if (negativeButton == null) {
            btn_cancel.setText(context.getString(R.string.cancel));
        } else {
            btn_cancel.setText(negativeButton);
        }
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.onDismiss(true);
                }
                dialog.dismiss();
            }
        });
        layout_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null) {
                    callBack.onDismiss(false);
                }
                dialog.dismiss();
            }
        });
        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            dialog.show();
        } else {
            dialog.show();
        }
    }

    public static void showDialogSingleButton(Context context, int icon, String title, String message, String positiveButton, final CallBack callBack) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout_for_alert_message);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        //layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        final Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        final Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setVisibility(View.GONE);
        ImageView iv_icon = (ImageView) dialog.findViewById(R.id.iv_icon);

        final RelativeLayout layout_main = (RelativeLayout) dialog.findViewById(R.id.layout_main);
        TextView txt_title = (TextView) dialog.findViewById(R.id.txt_title);
        TextView txt_message = (TextView) dialog.findViewById(R.id.txt_message);

        if (icon != 0) {
            iv_icon.setImageResource(icon);
        } else {
            iv_icon.setImageResource(R.drawable.app_icon);
        }

        txt_message.setText(message);
        txt_title.setText(title);
        if (positiveButton == null) {
            btn_ok.setText(context.getString(R.string.ok));
        } else {
            btn_ok.setText(positiveButton);
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.onDismiss(true);
                }
                dialog.dismiss();
            }
        });
        layout_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dialog.dismiss();
            }
        });
        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            dialog.show();
        } else {
            dialog.show();
        }
    }

    public interface CallBack {
        void onDismiss(boolean isPressedOK);
    }

    public Dialog showProgressDialog(Context context, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_progress);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView txt_message = (TextView) dialog.findViewById(R.id.txt_message);
        txt_message.setText(message);

        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            dialog.show();
        } else {
            dialog.show();
        }
        return dialog;
    }
}



