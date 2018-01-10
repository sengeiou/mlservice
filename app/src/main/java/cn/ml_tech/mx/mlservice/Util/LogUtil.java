package cn.ml_tech.mx.mlservice.Util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;


public class LogUtil {
    public final static String VERBOSE = "Verbose";
    public final static String Debug = "Debug";
    public final static String Info = "Info";
    public final static String Warn = "Warn";
    public final static String Error = "Error";
    public final static String Assert = "Assert";
    private static String tag = "Log";
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    public static void out(String level, String content){
     if(level.equals(VERBOSE)){
         Log.v(tag,content);
     }else if(level.equals(Info)){
         Log.i(tag,content);
     }else if(level.equals(Debug)){
         Log.d(tag,content);
     }else if(level.equals(Warn)){
         Log.w(tag,content);
     }else if(level.equals(Error)){
         Log.e(tag,content);
     }else {
         Log.d(tag,content);
     }
    }
    /**
     * 判断当前应用是否是debug状态
     */

    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
