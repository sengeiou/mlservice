package cn.ml_tech.mx.mlservice.base;

import android.app.Application;
import android.util.Log;

import org.litepal.LitePalApplication;

import cn.ml_tech.mx.mlservice.Util.LogUtil;
import cn.ml_tech.mx.mlservice.Util.WifiConnectUtil;

/**
 * Created by zhongwang on 2018/1/10.
 */

public class MlServerApplication extends LitePalApplication {
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
        LogUtil.out(LogUtil.Debug,"oncreate");
        wifiConnectUtil = WifiConnectUtil.getWifiConnectUtil(this);

    }
}
