package cn.ml_tech.mx.mlservice.Util;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 *
 */
public class AlertDialog {
    static {
        System.loadLibrary("JniTest");
    }

    Context context;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public void callback(String dialoginfo, String extra) {
        Intent intent = new Intent();
        intent.setAction("com.alert");
        intent.putExtra("info", dialoginfo);
        if (!extra.trim().equals("")) {
            intent.putExtra("extra", extra);
        }
        if (context != null)
            context.sendBroadcast(intent);
        Log.d("zw", "sssssss");
    }

    public static native String getStringFromNative();

    public native void doCallBack(); //Native层会调用callback()方法
}
