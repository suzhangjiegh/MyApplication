package com.szj.demo.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/1.
 */
public class MyUtil {
    private static Toast toast;

    /**
     *
     * @param c
     * @param text
     * 显示Toast
     */
    public static void showToast(Context c, String text) {
        if (toast == null) {
            toast=Toast.makeText(c.getApplicationContext(), text, Toast.LENGTH_SHORT);
        }else {
            toast.setText(text);
        }
        toast.show();
    }

    public interface OnDialogClickListener{
        void dialogButton(boolean ispositve);

    }
    public static void dialog(String title,String message,final Context context,final OnDialogClickListener listener) {
        Dialog alertDialog = new AlertDialog.Builder(context).setMessage(message)
                .setTitle(title).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.dialogButton(true);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.dialogButton(false);
                    }
                }).create();
        alertDialog.show();


    }
}
