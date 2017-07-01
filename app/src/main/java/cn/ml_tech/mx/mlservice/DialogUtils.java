package cn.ml_tech.mx.mlservice;


public class DialogUtils {

    static {
        System.loadLibrary("JniTest");
    }

    public void callback(String fromNative) {
        System.out.println(" I was invoked by native method  ############# " + fromNative);
    }
    public native void doCallBack(); //Native层会调用callback()方法

}
