package com.wytiger.lib.utils.special;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;

public class ThemeResourceUtil {

    public static final String TAG = "ThemeResourceUtil";

    /**
     * @param context
     * @param attrId
     * @return 返回context下，attrId指向的资源id。返回0如果找不到。
     */
    public static int getAttributeResId(Context context, int attrId) {
        TypedValue attrValue = getStyleAttributeValue(context, attrId);
        if (attrValue == null)
            return 0;

        return attrValue.resourceId;
    }

    /**
     * @param context
     * @param attrId
     * @return 返回context下，attrId指向的实际Drawable对象。如果资源指向color，会new ColorDrawable返回。返回null如果找不到该资源或者该资源无法转化为Drawable对象。
     */
    public static Drawable getAttributeDrawable(Context context, int attrId) {
        TypedValue attrValue = getStyleAttributeValue(context, attrId);
        if (attrValue == null)
            return null;

        Drawable drawable = null;
        switch (attrValue.type) {
        case TypedValue.TYPE_STRING:
            drawable = context.getResources().getDrawable(attrValue.resourceId);
            break;
        case TypedValue.TYPE_INT_COLOR_ARGB4:
        case TypedValue.TYPE_INT_COLOR_ARGB8:
        case TypedValue.TYPE_INT_COLOR_RGB4:
        case TypedValue.TYPE_INT_COLOR_RGB8:
            drawable = new ColorDrawable(attrValue.data);
            break;
        default:
            Log.w(TAG, "getAttributeDrawable() type not process: " + attrValue.type);
            break;
        }
        return drawable;
    }
    
    /**
     * @param context
     * @param attrId
     * @return 返回context下，attrId指向的实际color值。返回-1如果找不到该资源或者该资源非color对象。
     */
    public static int getAttributeColor(Context context, int attrId) {
        TypedValue attrValue = getStyleAttributeValue(context, attrId);
        if (attrValue == null)
            return -1;

        switch (attrValue.type) {
        case TypedValue.TYPE_INT_COLOR_ARGB4:
        case TypedValue.TYPE_INT_COLOR_ARGB8:
        case TypedValue.TYPE_INT_COLOR_RGB4:
        case TypedValue.TYPE_INT_COLOR_RGB8:
            return attrValue.data;
        default:
            Log.w(TAG, "getAttributeColor() type not process: " + attrValue.type);
            break;
        }
        return -1;
    }
    
    /**
     * @param context
     * @param attrValueId
     * @return 参数指定的属性在context中的值。null如果找不到。
     */
    public static TypedValue getStyleAttributeValue(Context context, int attrValueId) {
        TypedValue attrValue = new TypedValue();
        boolean hasSkinValue = context.getTheme().resolveAttribute(attrValueId, attrValue, true);
        if (!hasSkinValue)
            return null;
        return attrValue;
    }
}
