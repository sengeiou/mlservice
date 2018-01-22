package cn.ml_tech.mx.mlservice.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import cn.ml_tech.mx.mlservice.DAO.MobileUser;
import cn.ml_tech.mx.mlservice.Util.LogUtil;
import cn.ml_tech.mx.mlservice.Util.WifiConnectUtil;
import cn.ml_tech.mx.mlservice.base.SocketModule;

/**
 * Created by zhongwang on 2018/1/22.
 */

public class MobileConnectService extends Service {
    private WifiConnectUtil wifiConnectUtil;
     @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.out(LogUtil.Debug,"mobile service oncreate");
        wifiConnectUtil = WifiConnectUtil.getWifiConnectUtil(this);
        wifiConnectUtil.startObserver(new WifiConnectUtil.Operate() {
            @Override
            public SocketModule login(SocketModule socketModule) {
                MobileUser mobileUser = (MobileUser) socketModule.getBaseModule();
                LogUtil.out(LogUtil.Debug,mobileUser.getUserName()+"  "+mobileUser.getUserPassword());
                return null;
            }
        });
    }
}
