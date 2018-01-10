package cn.ml_tech.mx.mlservice.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * 创建时间: 2017/10/17
 * 创建人: Administrator
 * 功能描述:
 */

public class ToastUtil {
    private Context context;
    private Toast toast;
    private static ToastUtil toastUtil;

    private ToastUtil(Context context) {
        this.context = context;
    }

    public static ToastUtil getInstance(Context context) {
        if (toastUtil == null)
            toastUtil = new ToastUtil(context);
        return toastUtil;
    }

    public void showToast(String content) {
        if (null != toast) {
            toast.setText(content);
        } else {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        }

        toast.show();
    }

    public void showToast(int resourceId) {
        if (null != toast) {
            toast.setText(context.getString(resourceId));
        } else {
            toast = Toast.makeText(context, context.getString(resourceId), Toast.LENGTH_SHORT);
        }

        toast.show();
    }
}