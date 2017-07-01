package cn.ml_tech.mx.mlservice;


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

    /**
     * @param fromNative
     */
    public void callback(String fromNative) {
        Intent intent = new Intent();
        intent.setAction("com.alert");
        intent.putExtra("info", fromNative);
        Log.d("zw", fromNative);
        if (context != null)
            context.sendBroadcast(intent);
        Log.d("zw", "sssssss");
    }

    public static native String getStringFromNative();

    public native void doCallBack(); //Native层会调用callback()方法
}
