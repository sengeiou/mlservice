package cn.ml_tech.mx.mlservice.base;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.litepal.LitePalApplication;

import cn.ml_tech.mx.mlservice.Service.MobileConnectService;
import cn.ml_tech.mx.mlservice.Util.LogUtil;
import cn.ml_tech.mx.mlservice.Util.WifiConnectUtil;

/**
 * Created by zhongwang on 2018/1/10.
 */

public class MlServerApplication extends LitePalApplication {
private Gson gson;
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.out(LogUtil.Debug,"application oncreate");
        initMobileService();
        initLib();
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    private void initLib() {
        gson = new Gson();
    }

    private void initMobileService() {
        Intent intent = new Intent(this, MobileConnectService.class);
        startService(intent);
    }
}
