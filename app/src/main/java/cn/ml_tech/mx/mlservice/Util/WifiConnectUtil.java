package cn.ml_tech.mx.mlservice.Util;

/**
 * 创建时间: 2017/12/26
 * 创建人: Administrator
 * 功能描述:
 */

public class WifiConnectUtil {
    private static WifiConnectUtil wifiConnectUtil;
    private String ipAddress = "";
    private WifiConnectUtil() {
        ipAddress = "192.168.3.12";
    }

    public static WifiConnectUtil getWifiConnectUtil() {
        if (wifiConnectUtil == null)
            wifiConnectUtil = new WifiConnectUtil();
        return wifiConnectUtil;
    }
}
