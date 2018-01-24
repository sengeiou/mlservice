package cn.ml_tech.mx.mlservice.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import cn.ml_tech.mx.mlservice.DAO.MobileUser;
import cn.ml_tech.mx.mlservice.Util.DataUtil;
import cn.ml_tech.mx.mlservice.Util.LogUtil;
import cn.ml_tech.mx.mlservice.Util.WifiConnectUtil;
import cn.ml_tech.mx.mlservice.base.MlServerApplication;
import cn.ml_tech.mx.mlservice.base.SocketInfo;

/**
 * Created by zhongwang on 2018/1/22.
 */

public class MobileConnectService extends Service {
    private WifiConnectUtil wifiConnectUtil;
    private MlServerApplication mlServerApplication;
    private DataUtil dataUtil;
    private Gson gson;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        wifiConnectUtil.setConnectted(new WifiConnectUtil.Operate() {
            @Override
            public SocketInfo login(SocketInfo socketInfo) {
                MobileUser mobileUser = gson.fromJson(socketInfo.getBaseModule(), MobileUser.class);
                socketInfo.setBaseModule(dataUtil.checkAuthority(mobileUser.getUserName(), mobileUser.getUserPassword())+"");
            return socketInfo;

            }
        });
    }

    private void init() {
        LogUtil.out(LogUtil.Debug, "mobile service oncreate");
        wifiConnectUtil = WifiConnectUtil.getWifiConnectUtil(this);
        mlServerApplication = (MlServerApplication) getApplication();
        gson = mlServerApplication.getGson();
        dataUtil = DataUtil.getInstance();
    }

}
