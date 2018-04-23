package com.honyum.elevatorMan.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;

import com.honyum.elevatorMan.R;

import java.lang.ref.WeakReference;

/**
 * Created by star on 2018/4/9.
 */

public class DialogUtil {


    public Dialog CustomDialog(Context context,View view)
    {
        Dialog dialog = new Dialog(context,R.style.dialogStyle);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.show();
        return dialog;
    }
    public void showMessageDialog(Context context,String title, String message, String yesText, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(yesText, listener).show();
    }

    public void showButtonMessageDialog(Context context,String title, String message, String yesText,String noText, DialogInterface.OnClickListener yesListener,DialogInterface.OnClickListener noListener)
    {
        if (TextUtils.isEmpty(yesText))
        {
            yesText = "确定";
        }
        if (TextUtils.isEmpty(noText))
        {
            yesText = "取消";
        }
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(yesText, yesListener).setNegativeButton(noText,noListener).show();
    }
    public void showSingleSelectDialog(Context context,String title,String[] items,DialogInterface.OnClickListener selectListener)
    {

        new AlertDialog.Builder(context, R.style.dialogStyle)
                .setTitle(title)
                .setSingleChoiceItems(items, -1,selectListener)
                .show().setCanceledOnTouchOutside(true);
    }
    public void showMutiSelectDialog(Context context,String title, String[] items, String yesText, String noText, DialogInterface.OnMultiChoiceClickListener mutiSelectLinster, DialogInterface.OnClickListener yesListener, DialogInterface.OnClickListener noListener, boolean[] checks)
    {
        if (TextUtils.isEmpty(yesText))
        {
            yesText = "确定";
        }
        if (TextUtils.isEmpty(noText))
        {
            noText = "取消";
        }
        new AlertDialog.Builder(context, R.style.dialogStyle)
                .setTitle(title)
                .setPositiveButton(yesText, yesListener)
                .setMultiChoiceItems(items,checks,mutiSelectLinster)
                .setNegativeButton(noText,noListener).show().setCanceledOnTouchOutside(true);;
    }
    public void showListSingleSelectDialog(Context context,String title, String message,String[] items, String yesText,String noText,DialogInterface.OnClickListener selectListener, DialogInterface.OnClickListener yesListener,DialogInterface.OnClickListener noListener)
    {
        if (TextUtils.isEmpty(yesText))
        {
            yesText = "确定";
        }
        if (TextUtils.isEmpty(noText))
        {
            yesText = "取消";
        }
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(yesText, yesListener)
                .setItems(items, selectListener)
                .setNegativeButton(noText,noListener).show();
    }

}
