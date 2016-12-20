package com.wytiger.lib.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.EditText;

/**
 * description:复制粘贴工具类
 * Created by wytiger on 2016-12-9.
 */

public class ClipboardUtil {
    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy(Context context, String content) {
//        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//        cm.setText(content);//过时

        ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", content);
        myClipboard.setPrimaryClip(myClip);
    }

    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    public static void paste(Context context, EditText pasteField) {
//        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//        return cmb.getText().toString().trim();//过时

        ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = myClipboard.getPrimaryClip();
        ClipData.Item item = data.getItemAt(0);
        String text = item.getText().toString();
        pasteField.setText(text);
    }

    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
//        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//        return cmb.getText().toString().trim();//过时

        ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = myClipboard.getPrimaryClip();
        ClipData.Item item = data.getItemAt(0);
        String text = item.getText().toString();

        return text;
    }
}
