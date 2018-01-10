package cn.ml_tech.mx.mlservice.base;

import android.app.Application;

import cn.ml_tech.mx.mlservice.Util.WifiConnectUtil;

/**
 * Created by zhongwang on 2018/1/10.
 */

public class MlServerApplication extends Application {
    private WifiConnectUtil wifiConnectUtil;

    public WifiConnectUtil getWifiConnectUtil() {
        return wifiConnectUtil;
    }

    public void setWifiConnectUtil(WifiConnectUtil wifiConnectUtil) {
        this.wifiConnectUtil = wifiConnectUtil;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wifiConnectUtil = WifiConnectUtil.getWifiConnectUtil(this);

    }
}
