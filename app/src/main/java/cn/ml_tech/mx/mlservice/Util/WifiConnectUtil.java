package cn.ml_tech.mx.mlservice.Util;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建时间: 2017/12/26
 * 创建人: Administrator
 * 功能描述:
 */

public class WifiConnectUtil {
    private static WifiConnectUtil wifiConnectUtil;
    private String ipAddress = "";
    private boolean isEnable;
    private  WebConfig webConfig;//配置信息类
    private  ExecutorService threadPool;//线程池
    private ServerSocket serverSocket;
    private ToastUtil toastUtil;
    private Context context;
    private WifiConnectUtil(Context context) {
        this.context = context;
        toastUtil =ToastUtil.getInstance(context);
        ipAddress = "192.168.3.135";
        threadPool = Executors.newCachedThreadPool();
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(3456);
                    serverSocket.accept();
                    LogUtil.out(LogUtil.Debug,"链接成功");
                } catch (IOException e) {
                    e.printStackTrace();
                    toastUtil.showToast("无线服务开启失败");
                }
            }
        });

    }
    public static WifiConnectUtil getWifiConnectUtil(Context context) {
            wifiConnectUtil = new WifiConnectUtil(context);
        return wifiConnectUtil;
    }

    public class WebConfig {

        private int port;//端口
        private int maxParallels;//最大监听数

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getMaxParallels() {
            return maxParallels;
        }

        public void setMaxParallels(int maxParallels) {
            this.maxParallels = maxParallels;
        }
    }
}
